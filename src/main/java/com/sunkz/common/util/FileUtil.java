package com.sunkz.common.util;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

@Slf4j
public class FileUtil {

    @SneakyThrows
    public static BufferedImage captureScreen() {
        return new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
    }

    @SneakyThrows
    public static void downloadUrl(String fileURL, String saveDir) {
        URL url = new URL(fileURL);
        String fileName = fileURL.substring(fileURL.lastIndexOf('/') + 1);
        FileUtils.copyURLToFile(url, new File(saveDir, fileName));
    }

}
