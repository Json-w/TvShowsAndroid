package com.example.jason.helloworld.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    private static String defaultPattern = "yyyy-MM-dd HH:mm:ss";

    public static String dateToStr(Date date) {
        return dateToStr(defaultPattern, date);
    }

    public static String dateToStr(String format, Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static String TimeMillisToStr(long timeMillis, String pattern) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeMillis);
        return dateToStr(pattern, calendar.getTime());
    }

    public static String TimeMillisToStr(long timeMillis) {
        return TimeMillisToStr(timeMillis, defaultPattern);
    }
}
