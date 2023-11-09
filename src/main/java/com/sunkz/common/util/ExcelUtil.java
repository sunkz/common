package com.sunkz.common.util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ExcelUtil {

    public static <T> List<T> parseFromURL(String url, Map<String, String> headerAlias, Class<T> clazz) {
        try {
            File file = new File(UUID.randomUUID() + ".xlsx");
            FileUtils.copyURLToFile(new URL(url), file);
            return cn.hutool.poi.excel.ExcelUtil.getReader(file).setHeaderAlias(headerAlias).readAll(clazz);
        } catch (Exception e) {
            System.out.println("解析失败");
        }
        return null;
    }

    public static <T> void write(List<T> dataList, String fileName) {
        cn.hutool.poi.excel.ExcelUtil.getWriter("./" + fileName + ".xlsx").write(dataList).close();
    }

}

