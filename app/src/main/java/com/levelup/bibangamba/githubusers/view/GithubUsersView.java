package com.levelup.bibangamba.githubusers.view;

import java.util.List;

import com.levelup.bibangamba.githubusers.model.GithubUser;

public interface GithubUsersView {
    void githubUsersHaveBeenFetchedAndAreReadyForUse(List<GithubUser> githubUsers);
}
