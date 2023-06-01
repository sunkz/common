package com.sunkz.common.util;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

@Slf4j
public class FileUtil {

    public static void delete(File file) {
        if (file != null && file.exists() && !file.isDirectory()) {
            file.delete();
            log.info("FileUtil delete {}", file.getName());
        }
    }

    @SneakyThrows
    public static BufferedImage captureScreen() {
        return new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
    }

}
