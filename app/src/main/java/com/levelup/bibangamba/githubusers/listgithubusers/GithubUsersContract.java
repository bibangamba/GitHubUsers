package com.levelup.bibangamba.githubusers.listgithubusers;

import com.levelup.bibangamba.githubusers.base.BasePresenter;
import com.levelup.bibangamba.githubusers.base.BaseView;
import com.levelup.bibangamba.githubusers.model.GithubUser;

import java.util.List;

public interface GithubUsersContract {
    interface View extends BaseView<Presenter> {
        void showLoadingIndicator(boolean active);

//        void showLoadingGithubUsersError();

        void showGithubUsers(List<GithubUser> users);

        void showGithubUserDetailsUI(GithubUser username);

        boolean isActive();

        void showNoInternetSnackbar();
    }

    interface Presenter extends BasePresenter {
        void loadGithubUsers();

//        void openGithubUserDetails(@NonNull GithubUser githubUser);

    }
}
