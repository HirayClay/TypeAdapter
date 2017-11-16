package com.hiray.typeadapter;



import java.util.Arrays;
import java.util.List;

/**
 * 保存猜数字投注的数据
 */
public class BetHolder {

    //单双
    private static List<Integer> item1 = Arrays.asList(0, 0);
    //点数
    private static List<Integer> item2 = Arrays.asList(
            0, 0, 0, 0, 0, 0);
    //三同号，通选
    private static List<Integer> item3 = Arrays.asList(
            0, 0, 0, 0, 0, 0, 0);
    //和值
    private static List<Integer> item4 = Arrays.asList(
            0, 0, 0, 0,
            0, 0, 0, 0,
            0, 0, 0, 0,
            0, 0, 0, 0);
    //两不同号
    private static List<Integer> item5 = Arrays.asList(
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0);
    //两同号
    private static List<Integer> item6 = Arrays.asList(
            0, 0, 0, 0, 0, 0);
    //直选
    private static List<Integer> item7 = Arrays.asList(
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0);
    //对子直选
    private static List<Integer> item8 = Arrays.asList(
            0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0);

    private static List<List<Integer>> collections = Arrays.asList(
            item1, item2, item3, item4, item5, item6, item7, item8
    );


    public static void clear() {
        for (List<Integer> item :
                collections) {
            for (int i = 0; i < item.size(); i++) {
                item.set(i, 0);
            }
        }
    }

    public static int query(int type, int position) {
        switch (type) {
            case 1:
                return item1.get(0);
            case 2:
                return item1.get(1);
            case 3:
                return item2.get(position);
            case 4:
                return item3.get(position);
            case 5://pos = 7 通选
                return item3.get(position);
            case 6:
                return item4.get(position);
            case 7:
                return item5.get(position);
            case 8:
                return item6.get(position);
            case 9:
                return item7.get(position);
            case 10:
                return item8.get(position);
            default:
                return 0;
        }

    }

    public static void addTo(int type, int position, int count) {
        switch (type) {
            case 1:
                item1.set(position, query(type, position) + count);
                break;
            case 2:
                item1.set(position, query(type, position) + count);
                break;
            case 3:
                item2.set(position, query(type, position) + count);
                break;
            case 4:
                item3.set(position, query(type, position) + count);
                break;
            case 5://pos = 7
                item3.set(position, query(type, position) + count);
                break;
            case 6:
                item4.set(position, query(type, position) + count);
                break;
            case 7:
                item5.set(position, query(type, position) + count);
                break;
            case 8:
                item6.set(position, query(type, position) + count);
                break;
            case 9:
                item7.set(position, query(type, position) + count);
                break;
            case 10:
                item8.set(position, query(type, position) + count);
                break;

        }
    }

}
