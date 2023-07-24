package com.sunkz.common.util;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class PageUtil {

    /**
     * pageIndex from 1
     */
    public static <T> List<T> getPage(List<T> source, int pageIndex, int pageSize) {
        if (CollectionUtils.isEmpty(source) || pageIndex <= 0 || pageSize <= 0) {
            return new ArrayList<>(1);
        }
        int start = (pageIndex - 1) * pageSize;
        if (start >= source.size()) {
            return new ArrayList<>(1);
        }
        int end = (source.size() - start) > pageSize ? start + pageSize : source.size();
        return source.subList(start, end);
    }

}
