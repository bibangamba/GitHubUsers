package com.levelup.bibangamba.githubusers.view;

import com.levelup.bibangamba.githubusers.BaseContract;
import com.levelup.bibangamba.githubusers.model.GithubUser;

import java.util.List;

public class GithubUsersListContract {
    public interface GithubUsersView extends BaseContract.View {
        void handleRetrievedUsers(List<GithubUser> githubUsers);
    }

    public interface GithubUsersPresenter extends BaseContract.Presenter<GithubUsersView> {
        void getGithubUsers();
    }
}
