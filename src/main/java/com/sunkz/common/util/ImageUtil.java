package com.sunkz.common.util;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Slf4j
public class ImageUtil {

    private static final double COMMON_THRESHOLD = 0.9999;

    /**
     * 仅限相同大小图片比较
     */
    @SneakyThrows
    private static double compare(File file1, File file2) {
        BufferedImage image1 = ImageIO.read(file1);
        BufferedImage image2 = ImageIO.read(file2);

        int[] pixels1 = image1.getRGB(0, 0, image1.getWidth(), image1.getHeight(), null, 0, image1.getWidth());
        int[] pixels2 = image2.getRGB(0, 0, image2.getWidth(), image2.getHeight(), null, 0, image2.getWidth());

        double dotProduct = 0.0;
        double magnitude1 = 0.0;
        double magnitude2 = 0.0;
        for (int i = 0; i < pixels1.length; i++) {
            int pixel1 = pixels1[i];
            int pixel2 = pixels2[i];
            dotProduct += ((pixel1 >> 16) & 0xff) * ((pixel2 >> 16) & 0xff) + ((pixel1 >> 8) & 0xff) * ((pixel2 >> 8) & 0xff) + (pixel1 & 0xff) * (pixel2 & 0xff);
            magnitude1 += Math.pow((pixel1 >> 16) & 0xff, 2) + Math.pow((pixel1 >> 8) & 0xff, 2) + Math.pow(pixel1 & 0xff, 2);
            magnitude2 += Math.pow((pixel2 >> 16) & 0xff, 2) + Math.pow((pixel2 >> 8) & 0xff, 2) + Math.pow(pixel2 & 0xff, 2);
        }
        return dotProduct / (Math.sqrt(magnitude1) * Math.sqrt(magnitude2));
    }

    public static boolean isSame(File file1, File file2) {
        if (file1 == null || file2 == null) {
            return false;
        }
        double d = compare(file1, file2);
        log.info("ImageUtil isSame {},{},{}", file1.getName(), file2.getName(), d);
        return d > COMMON_THRESHOLD;
    }

}
