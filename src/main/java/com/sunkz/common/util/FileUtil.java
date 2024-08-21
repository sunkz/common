package com.sunkz.common.util;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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

    public static void downloadUrl(String url, String name) {
        try (InputStream inputStream = new URL(url).openStream();
             FileOutputStream fileOutputStream = new FileOutputStream(name)) {

            byte[] buffer = new byte[8192];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String write(String content) {
        String file = System.getProperty("user.dir") + "/" + System.currentTimeMillis() + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(content);
            return file;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<String> readLines(String filePath) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

}
