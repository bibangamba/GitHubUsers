package com.levelup.bibangamba.githubusers.githubuserdetails;

import com.levelup.bibangamba.githubusers.base.BasePresenter;
import com.levelup.bibangamba.githubusers.base.BaseView;
import com.levelup.bibangamba.githubusers.model.GithubUser;

public interface GithubUserDetailsContract {
    interface View extends BaseView<Presenter> {
        void showGithubUserDetails();

        void loadMoreGithubUserDetails(GithubUser user);

        void requestGithubUserDetailsLoading();

        boolean isActive();

//        void showNoInternetSnackbar();

//        void showErrorOnFailingToLoadGithubUserDetails();
    }

    interface Presenter extends BasePresenter {
        void triggerShareAction(GithubUser user);

        void expandGithubUserProfileImage();

        void loadGithubUserDetails(String username);
    }
}
