package com.sunkz.common.util;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ExcelUtil {

    private static final Logger log = LoggerFactory.getLogger(ExcelUtil.class);

    public static <T> List<T> parseFromURL(String url, Map<String, String> headerAlias, Class<T> clazz) {
        try {
            File file = new File(UUID.randomUUID() + ".xlsx");
            FileUtils.copyURLToFile(new URL(url), file);
            return cn.hutool.poi.excel.ExcelUtil.getReader(file).setHeaderAlias(headerAlias).readAll(clazz);
        } catch (Exception e) {
            log.error("parseFromURL error url={},headerAlias={}", url, headerAlias, e);
        }
        return null;
    }

    public static <T> String write(List<T> dataList) {
        String file = System.getProperty("user.dir") + "/" + System.currentTimeMillis() / 1000 + ".xlsx";
        cn.hutool.poi.excel.ExcelUtil.getWriter(file).write(dataList).close();
        return file;
    }

}

