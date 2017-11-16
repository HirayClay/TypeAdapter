package com.hiray.typeadapter;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hiray.adapter.TypeAdapter;
import com.hiray.typeadapter.event.BetNumberEvent;
import com.hiray.typeadapter.event.Bus;
import com.hiray.typeadapter.event.ItemNotifyEvent;
import com.hiray.typeadapter.model.ColumnItem;
import com.hiray.typeadapter.model.EditionHeader;
import com.hiray.typeadapter.model.HistoryNumber;
import com.hiray.typeadapter.model.IrregularItem;
import com.hiray.typeadapter.model.OddEvenGrid;
import com.hiray.typeadapter.utility.FormatUtil;
import com.hiray.typeadapter.widget.BackgroundRadioButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import rx.Subscription;
import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    BetHolder betHolder = new BetHolder();
    @Bind(R.id.hundred)
    BackgroundRadioButton hundred;
    @Bind(R.id.thousand)
    BackgroundRadioButton thousand;
    @Bind(R.id.ten_thousand)
    BackgroundRadioButton tenThousand;
    @Bind(R.id.hundred_thousand)
    BackgroundRadioButton hundredThousand;
    @Bind(R.id.million)
    BackgroundRadioButton million;
    @Bind(R.id.radio_group)
    RadioGroup radioGroup;
    @Bind(R.id.recycler_view_history)
    RecyclerView recyclerViewHistory;
    @Bind(R.id.clock)
    TextView clock;
    private Subscription subscription;
    int bet = 100;
    private TypeAdapter typeAdapter;
    private TypeAdapter historyAdapter;
    private Timer timer;
    private ColumnGridBinder columnGridViewBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    public void init() {

        typeAdapter = new TypeAdapter();

        List<Object> data = new ArrayList<>();
        data.add(new EditionHeader(354789));

        data.add(new OddEvenGrid()); //type = 1,2
        List<String> _1_6 = Arrays.asList("1", "2", "3", "4", "5", "6");

        data.add(new ColumnItem("点数", "出现一个所选号码得2倍，2个得3倍，3个得4倍", _1_6, null, 6, 3)); //type = 3

        /*三同号, 通选*/
        data.add(new IrregularItem()); //type = 4,5

        /*和值*/
        List<String> _3_18 = Arrays.asList("3", "4", "5", "6", "7", "8",
                "9", "10", "11", "12", "13", "14",
                "15", "16", "17", "18");
        List<String> _3_18_multipliers = Arrays.asList("150倍", "50倍", "25倍", "15倍", "12倍", "8倍",
                "6倍", "6倍", "6倍", "6倍", "8倍", "12倍",
                "15倍", "25倍", "50倍", "150倍");

        data.add(new ColumnItem("和值", "", _3_18, _3_18_multipliers, 4, 6)); //type = 6

        /*两不同号*/
        List<String> _12_56 = Arrays.asList(
                "12", "13", "14", "15", "16",
                "23", "24", "25", "26", "34",
                "35", "36", "45", "46", "56");

        data.add(new ColumnItem("两不同号", "5倍", _12_56, null, 5, 7)); //type = 7

        /*两同号*/
        List<String> _11_66 = Arrays.asList("11", "22", "33", "44", "55", "66");
        data.add(new ColumnItem("两同号", "10倍", _11_66, null, 6, 8)); //type = 8

        /*直选*/
        List<String> _123_456 = Arrays.asList(
                "123", "124", "125", "126", "134",
                "135", "136", "145", "146", "156",
                "234", "235", "236", "245", "246",
                "256", "345", "346", "356", "456");

        data.add(new ColumnItem("直选", "25倍", _123_456, null, 5, 9)); //type = 9


        /*对子直选*/
        List<String> _112_665 = Arrays.asList(
                "112", "113", "114", "115", "116",
                "221", "223", "224", "225", "226",
                "331", "332", "334", "335", "336",
                "441", "442", "443", "445", "446",
                "551", "552", "553", "554", "556",
                "661", "662", "663", "664", "665");

        data.add(new ColumnItem("对子直选", "50倍", _112_665, null, 5, 10)); //type = 10


        typeAdapter.addBinder(new EditionHeaderBinder());
        typeAdapter.addBinder(new OddEvenBinder());
        columnGridViewBinder = new ColumnGridBinder();
        typeAdapter.addBinder(columnGridViewBinder);
        typeAdapter.addBinder(new IrregularGridViewBinder());

        typeAdapter.setData(data);
//        typeAdapter.setStrategy(TypeAdapter.STRATEGY_CLASS);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(typeAdapter);


        historyAdapter = new TypeAdapter();
        historyAdapter.addBinder(new HistoryBinder());
        List<HistoryNumber> historyNumbers = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            historyNumbers.add(new HistoryNumber(false, "12", "2", "3", "4"));
        }
        historyAdapter.setData(historyNumbers);
//        historyAdapter.setStrategy(TypeAdapter.STRATEGY_CLASS);
        recyclerViewHistory.setLayoutManager(new GridLayoutManager(this, 14));
        recyclerViewHistory.setAdapter(historyAdapter);

        subscription = Bus.getInstance().register(new Action1() {
            @Override
            public void call(Object o) {
                if (o instanceof BetNumberEvent) {
                    BetNumberEvent e = (BetNumberEvent) o;
                    gambleAndNotify(e.type, e.position, e.number);
                }
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.hundred:
                        betStepSize = 100;
                        break;
                    case R.id.thousand:
                        betStepSize = 1000;
                        break;
                    case R.id.ten_thousand:
                        betStepSize = 10000;
                        break;
                    case R.id.hundred_thousand:
                        betStepSize = 100000;
                        break;
                    case R.id.million:
                        betStepSize = 1000000;
                        break;
                }
            }
        });

        startTimer(FormatUtil.getRestSecInMinute());
    }

    private void startTimer(int sec) {
        if (timer != null)
            timer.cancel();
        timer = new Timer(sec * 1000, 1000);
        timer.start();
    }

    int betStepSize = 100;

    private void gambleAndNotify(int type, int position, String number) {
        showMessage("you click number:" + number);
        BetHolder.addTo(type, position, betStepSize);
        Bus.getInstance().post(new ItemNotifyEvent(type, position));
        columnGridViewBinder.notifyItem(type, position);
    }


    /*请求后台*/
    private void request(int type, String number, int bet) {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        BetHolder.clear();
        subscription.unsubscribe();
        timer.cancel();
        timer = null;
    }

    class Timer extends CountDownTimer {

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public Timer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            clock.setText(millisUntilFinished / 1000 + "''");
        }

        @Override
        public void onFinish() {
            showMessage("开奖中.....");
            getWindow().getDecorView().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startTimer(FormatUtil.getRestSecInMinute());
                }
            }, 1000);
        }
    }


    public void showMessage(String msg) {
        Toasty.success(this, msg, Toast.LENGTH_SHORT, true).show();
    }
}
