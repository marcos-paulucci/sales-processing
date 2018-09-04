package com.JPMorgan.salesProccessing.utils;

public class StringUtils {
    public static String padRight(String s) {
        int n = 14;
        return String.format("%1$-" + n + "s", s);
    }
}
