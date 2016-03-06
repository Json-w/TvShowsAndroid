package com.example.jason.helloworld.common;

public class StringUtil {
    public static boolean isNull(String validate) {
        if (null == validate || "".equals(validate)) {
            return true;
        }
        return false;
    }
}
