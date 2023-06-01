package com.sunkz.common.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;

@Slf4j
public class FileUtil {

    public static void delete(File file) {
        if (file != null && file.exists() && !file.isDirectory()) {
            boolean delete = file.delete();
            log.info("FileUtil delete {},{}", file.getName(), delete);
        }
    }

}
