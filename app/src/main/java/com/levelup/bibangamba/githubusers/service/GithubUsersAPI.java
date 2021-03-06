package com.levelup.bibangamba.githubusers.service;

import com.levelup.bibangamba.githubusers.BuildConfig;
import com.levelup.bibangamba.githubusers.model.GithubUser;
import com.levelup.bibangamba.githubusers.model.GithubUsersResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface GithubUsersAPI {
    @GET("search/users?q=type:User+location:Nairobi+language:JAVA")
    Call<GithubUsersResponse> getAllUsers();

    @GET("users/{username}")
    Call<GithubUser> getUserInformation(@Path("username") String username);
}
