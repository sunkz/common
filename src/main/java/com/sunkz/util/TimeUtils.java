package com.sunkz.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtils {

    public static String now() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "\t";
    }

}
