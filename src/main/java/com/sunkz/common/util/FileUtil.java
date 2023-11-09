package com.sunkz.common.util;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
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

    public static void delete(String name) {
        File file = new File(name);
        if (file.exists()) {
            file.delete();
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
        byte[] buffer = new byte[1024 * 1024 * 1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            fileOutputStream.write(buffer, 0, length);
        }
        inputStream.close();
        fileOutputStream.close();
    }

    public static String write(String content) {
        String file = System.getProperty("user.dir") + "/" + System.currentTimeMillis() / 1000 + ".txt";
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            log.error("write error", e);
        }
        return file;
    }

}
