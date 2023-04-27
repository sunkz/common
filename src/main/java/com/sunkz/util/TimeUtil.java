package com.sunkz.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtil {

    public static final String PATTERN_1 = "HH:mm:ss";
    public static final String PATTERN_2 = "mm:ss";

    public static String now() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "\t";
    }

    public static String now(String pattern) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern)) + "\t";
    }

}
