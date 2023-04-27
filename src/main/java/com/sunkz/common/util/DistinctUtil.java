package com.sunkz.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;

public class DistinctUtil {

    public static <T> List<T> distinct(List<T> source, BiPredicate<T, T> predicate) {
        List<T> distinct = new ArrayList<>();
        for (T t1 : source) {
            boolean contain = false;
            for (T t2 : distinct) {
                if (predicate.test(t1, t2)) {
                    contain = true;
                    break;
                }
            }
            if (!contain) {
                distinct.add(t1);
            }
        }
        return distinct;
    }

}
