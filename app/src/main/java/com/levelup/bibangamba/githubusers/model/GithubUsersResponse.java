package com.levelup.bibangamba.githubusers.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GithubUsersResponse {

    @SerializedName("items")
    private List<GithubUser> users;

    public GithubUsersResponse() {
        this.users = new ArrayList<>();
    }

    public List<GithubUser> getUsers() {
        return users;
    }

}
