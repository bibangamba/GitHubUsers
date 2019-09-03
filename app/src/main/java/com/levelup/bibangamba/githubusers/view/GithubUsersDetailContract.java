package com.levelup.bibangamba.githubusers.view;

import com.levelup.bibangamba.githubusers.BaseContract;
import com.levelup.bibangamba.githubusers.model.GithubUser;

public class GithubUsersDetailContract {
    public interface GithubUserDetailView extends BaseContract.View {
        void handleRetrievedUserInfo(GithubUser githubUser);
    }

    public interface GithubUserDetailPresenter extends BaseContract.Presenter<GithubUserDetailView> {
        void getGithubUserInfo(String username);
    }
}
