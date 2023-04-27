package com.sunkz.common.util;

import cn.hutool.core.collection.CollectionUtil;

import java.util.List;

public class PageUtil {

    /**
     * pageIndex from 1
     */
    public static <T> List<T> getPage(List<T> source, int pageIndex, int pageSize) {
        if (CollectionUtil.isEmpty(source) || pageIndex <= 0 || pageSize <= 0) {
            return null;
        }
        int start = (pageIndex - 1) * pageSize;
        if (start >= source.size()) {
            return null;
        }
        int end = (source.size() - start) > pageSize ? start + pageSize : source.size();
        return source.subList(start, end);
    }

}
