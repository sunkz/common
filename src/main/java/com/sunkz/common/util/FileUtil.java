package com.sunkz.common.util;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

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

    @SneakyThrows
    public static void downloadUrl(String url, String name) {
        InputStream inputStream = new URL(url).openStream();
        FileOutputStream fileOutputStream = new FileOutputStream(name);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            fileOutputStream.write(buffer, 0, length);
        }
        inputStream.close();
        fileOutputStream.close();
    }

}
