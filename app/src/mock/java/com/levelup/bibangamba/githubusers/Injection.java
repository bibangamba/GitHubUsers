package com.levelup.bibangamba.githubusers;

public class Injection {
    private static String BASE_URL = "";

    public static void setBaseUrl(String baseUrl) {
        BASE_URL = baseUrl;
    }

    public static String getBaseUrl(){
        return BASE_URL;
    }
}
