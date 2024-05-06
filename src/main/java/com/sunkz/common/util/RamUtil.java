package com.sunkz.common.util;

import org.apache.lucene.util.RamUsageEstimator;

public class RamUtil {

    public static void size(Object o) {
        String size = RamUsageEstimator.humanSizeOf(o);
        System.out.println("size:\t" + size);
    }

}
