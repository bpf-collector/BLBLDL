package com.bpf.utils;

public class FileUtil {

    public static String replaceSign(String str) {
        return str.replaceAll("[:?*\"\\/|]", "-")
                .replaceAll("[<>]", "")
                .replace(" ", "_");
    }
}
