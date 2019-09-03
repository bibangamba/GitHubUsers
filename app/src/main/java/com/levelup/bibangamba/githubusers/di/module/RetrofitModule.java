package com.levelup.bibangamba.githubusers.di.module;

import com.levelup.bibangamba.githubusers.service.GithubUsersAPI;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = {PresenterModule.class})
public class RetrofitModule {
    private String mBaseUrl;

    public RetrofitModule(String baseUrl) {
        this.mBaseUrl = baseUrl;
    }

    @Provides
    OkHttpClient providesOkHttpClient() {
        return new OkHttpClient.Builder()
                .build();
    }

    @Provides
    Retrofit providesRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(mBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    GithubUsersAPI providesGithubUsersAPI(Retrofit retrofit) {
        return retrofit.create(GithubUsersAPI.class);
    }
}
