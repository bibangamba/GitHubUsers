package com.levelup.bibangamba.githubusers.service;

import com.levelup.bibangamba.githubusers.BuildConfig;
import com.levelup.bibangamba.githubusers.Injection;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GithubService {
    private static final String BASE_URL = Injection.getBaseUrl();

    private static final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .addInterceptor(chain ->
                    chain.proceed(chain.request().newBuilder().addHeader("Authorization",
                            "bearer " + BuildConfig.GITHUB_ACCESS_TOKEN).build()))
            .addInterceptor(new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY))
            .build();

    private static final Retrofit retrofit2 = new Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private static final GithubUsersAPI GITHUB_USERS_API = retrofit2.create(GithubUsersAPI.class);

    public static GithubUsersAPI getServiceInstance() {
        return GITHUB_USERS_API;
    }

}
