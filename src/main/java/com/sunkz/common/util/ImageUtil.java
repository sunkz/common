package com.sunkz.common.util;

import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ImageUtil {

    private static final int compareLevel = 4;

    @SneakyThrows
    public static double compare(File fst, File scd) {
        List<Double> fstData = getPicArrayData(fst.getPath());
        List<Double> scdData = getPicArrayData(scd.getPath());
        return getPearsonByDIM(fstData, scdData);
    }

    private static List<Double> getPicArrayData(String path) throws IOException {
        BufferedImage image = ImageIO.read(new File(path));

        //初始化集合
        final List<Double> picFingerprint = new ArrayList<>(compareLevel * compareLevel * compareLevel);
        IntStream.range(0, compareLevel * compareLevel * compareLevel).forEach(i -> {
            picFingerprint.add(i, 0.0);
        });
        //遍历像素点
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                Color color = new Color(image.getRGB(i, j));
                //对像素点进行计算
                putIntoFingerprintList(picFingerprint, color.getRed(), color.getGreen(), color.getBlue());
            }
        }

        return picFingerprint;
    }

    private static void putIntoFingerprintList(List<Double> picFingerprintList, int r, int g, int b) {
        final int index = Integer.valueOf(getBlockLocation(r) + getBlockLocation(g) + getBlockLocation(b), compareLevel);
        final Double origin = picFingerprintList.get(index);
        picFingerprintList.set(index, origin + 1);
    }

    private static String getBlockLocation(int colorPoint) {
        return IntStream.range(0, compareLevel)
                .filter(i -> {
                    int areaStart = (256 / compareLevel) * i;
                    int areaEnd = (256 / compareLevel) * (i + 1) - 1;
                    return colorPoint >= areaStart && colorPoint <= areaEnd;
                })
                .mapToObj(location -> location + "")
                .findFirst()
                .orElseThrow();
    }

    private static double getPearsonByDIM(List<Double> ratingOne, List<Double> ratingTwo) {
        try {
            if (ratingOne.size() != ratingTwo.size()) {
                if (ratingOne.size() > ratingTwo.size()) {
                    List<Double> temp = ratingOne;
                    ratingOne = new ArrayList<>();
                    for (int i = 0; i < ratingTwo.size(); i++) {
                        ratingOne.add(temp.get(i));
                    }
                } else {
                    List<Double> temp = ratingTwo;
                    ratingTwo = new ArrayList<>();
                    for (int i = 0; i < ratingOne.size(); i++) {
                        ratingTwo.add(temp.get(i));
                    }
                }
            }
            double sim = 0D;//最后的皮尔逊相关度系数
            double commonItemsLen = ratingOne.size();//操作数的个数
            double oneSum = 0D;//第一个相关数的和
            double twoSum = 0D;//第二个相关数的和
            double oneSqSum = 0D;//第一个相关数的平方和
            double twoSqSum = 0D;//第二个相关数的平方和
            double oneTwoSum = 0D;//两个相关数的乘积和
            for (int i = 0; i < ratingOne.size(); i++) {//计算
                double oneTemp = ratingOne.get(i);
                double twoTemp = ratingTwo.get(i);
                //求和
                oneSum += oneTemp;
                twoSum += twoTemp;
                oneSqSum += Math.pow(oneTemp, 2);
                twoSqSum += Math.pow(twoTemp, 2);
                oneTwoSum += oneTemp * twoTemp;
            }
            double num = (commonItemsLen * oneTwoSum) - (oneSum * twoSum);
            double den = Math.sqrt((commonItemsLen * oneSqSum - Math.pow(oneSum, 2)) * (commonItemsLen * twoSqSum - Math.pow(twoSum, 2)));
            sim = (den == 0) ? 1 : num / den;
            return sim;
        } catch (Exception e) {
            return 0;
        }
    }

}
