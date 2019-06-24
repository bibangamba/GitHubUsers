package com.levelup.bibangamba.githubusers;

public class Injection {
    private static String BASE_URL = "https://api.github.com/";

    public static String getBaseUrl() {
        return BASE_URL;
    }
}
