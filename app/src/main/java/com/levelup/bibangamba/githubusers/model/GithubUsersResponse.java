package com.levelup.bibangamba.githubusers.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GithubUsersResponse {

    @SerializedName("items")
    private List<GithubUsers> users;

    public GithubUsersResponse() {
        this.users = new ArrayList<>();
    }

    public List<GithubUsers> getUsers() {
        return users;
    }

}
