package com.levelup.bibangamba.githubusers.service;

import com.levelup.bibangamba.githubusers.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GithubService {
    private final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(chain ->
                    chain.proceed(chain.request().newBuilder().addHeader("Authorization",
                            "bearer " + BuildConfig.GITHUB_ACCESS_TOKEN).build()))
            .addInterceptor(new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY))
            .build();

    private Retrofit retrofit = null;

    public GithubUsersAPI getApi() {
        final String BASE_URL = "https://api.github.com/";
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(GithubUsersAPI.class);
    }
}
