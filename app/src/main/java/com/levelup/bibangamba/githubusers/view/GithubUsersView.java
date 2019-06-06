package com.levelup.bibangamba.githubusers.view;

import java.util.List;

import com.levelup.bibangamba.githubusers.model.GithubUsers;

public interface GithubUsersView {
    void githubUsersHaveBeenFetchedAndAreReadyForUse(List<GithubUsers> githubUsers);
}
