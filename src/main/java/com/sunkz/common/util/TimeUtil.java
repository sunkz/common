package com.sunkz.common.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class TimeUtil {

    public static final String PATTERN_1 = "HH:mm:ss";
    public static final String PATTERN_2 = "mm:ss";
    public static final String PATTERN_3 = "yyyyMMddHHmmss";
    public static final String PATTERN_4 = "yyyy-MM-dd";
    public static final String PATTERN_5 = "yyyy-MM-dd HH:mm:ss";

    public static String now() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public static String now(String pattern) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));
    }

}
