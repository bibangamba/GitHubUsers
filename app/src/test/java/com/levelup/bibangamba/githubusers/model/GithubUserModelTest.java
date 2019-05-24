package com.levelup.bibangamba.githubusers.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GithubUserModelTest {

    @Test
    public void setAndGetFromUsersModel() {
        //call the presenter function we need (loadUsers)
        //verify that functions we expect to be called are indeed called (getApi or service methods, etc)
        GithubUser githubUser = new GithubUser();
        final String username = "nellyk";
        final String profilePicture = "https://avatars3.githubusercontent.com/u/3062772?v=4";
        final String profileURL = "https://github.com/nellyk";
        final String followers = "55";
        final String following = "157";
        final String repos = "44";
        final String company = "Andela";

        githubUser.setUsername(username);
        githubUser.setProfilePicture(profilePicture);
        githubUser.setProfileUrl(profileURL);
        githubUser.setFollowers(followers);
        githubUser.setFollowing(following);
        githubUser.setRepos(repos);
        githubUser.setCompany(company);

        assertEquals(username, githubUser.getUsername());
        assertEquals(profilePicture, githubUser.getProfilePicture());
        assertEquals(profileURL, githubUser.getProfileUrl());
        assertEquals(followers, githubUser.getFollowers());
        assertEquals(following, githubUser.getFollowing());
        assertEquals(repos, githubUser.getRepos());
        assertEquals(company, githubUser.getCompany());
    }
}
