package com.example.jason.helloworld.common;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static String dateToStr(Date date) {
        return dateToStr("yyyy-MM-dd HH:mm:ss", date);
    }

    private static String dateToStr(String format, Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }
}
