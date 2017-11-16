package com.hiray.typeadapter.utility;

import java.util.Calendar;



public class FormatUtil {

    static StringBuilder stringBuilder = new StringBuilder(10);

    public static String formatBetNumber(int number) {
        stringBuilder.delete(0, stringBuilder.length());
        if (number <= 0)
            return stringBuilder.toString();

        if (number < 10000)
            return stringBuilder.append(number).toString();
        else
            return stringBuilder.append(number / 10000).append('万').toString();
    }


    public static int getRestSecInMinute() {
        return 60 - Calendar.getInstance().get(Calendar.SECOND);
    }

}
