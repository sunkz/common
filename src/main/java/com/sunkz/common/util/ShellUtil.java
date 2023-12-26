package com.sunkz.common.util;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ShellUtil {

    /**
     * @param name app.jar
     */
    @SneakyThrows
    public static void runJar(String name) {
        Runtime.getRuntime().exec(new String[]{"java", "-jar", name});
    }

    @SneakyThrows
    public static void stopJar(String name) {
        String javaHome = System.getProperty("java.home").replace("/jre", "") + "/bin";
        String jps = javaHome + "/jps";
        String command = jps + " -l|grep " + name + "|awk '{print $1}'";
        Process process = Runtime.getRuntime().exec(new String[]{"/bin/bash", "-c", command});
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String pid = reader.readLine();
        if (pid != null) {
            command = "kill -9 " + pid;
            Runtime.getRuntime().exec(new String[]{"/bin/bash", "-c", command});
        }
    }

    @SneakyThrows
    public static List<String> runCommand(String exec) {
        Process process = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", exec});
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        List<String> result = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            result.add(line);
        }
        return result;
    }

}
