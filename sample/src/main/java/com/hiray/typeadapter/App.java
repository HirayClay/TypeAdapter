package com.hiray.typeadapter;

import android.app.Application;
import android.content.res.Resources;

import es.dmoral.toasty.Toasty;

/**
 * Created by CJJ on 2017/11/16.
 *
 * @author CJJ
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Resources rs = getResources();
        Toasty.Config.getInstance().setSuccessColor(rs.getColor(R.color.colorDarkGreen))
                .setTextColor(rs.getColor(R.color.white))
                .apply();
    }
}
