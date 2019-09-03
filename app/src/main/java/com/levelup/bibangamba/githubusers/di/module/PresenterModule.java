package com.levelup.bibangamba.githubusers.di.module;

import com.levelup.bibangamba.githubusers.presenter.GithubUserDetailsPresenter;
import com.levelup.bibangamba.githubusers.presenter.GithubUserListPresenter;
import com.levelup.bibangamba.githubusers.service.GithubUsersAPI;
import com.levelup.bibangamba.githubusers.view.GithubUsersDetailContract;
import com.levelup.bibangamba.githubusers.view.GithubUsersListContract;

import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {

    @Provides
    GithubUsersListContract.GithubUsersPresenter providesGithubUserListPresenter(GithubUsersAPI githubUsersAPI) {
        return new GithubUserListPresenter(githubUsersAPI);
    }

    @Provides
    GithubUsersDetailContract.GithubUserDetailPresenter providesGithubUserDetailsPresenter(GithubUsersAPI githubUsersAPI) {
        return new GithubUserDetailsPresenter(githubUsersAPI);
    }


// TODO: 2019-08-28 figure out how to pass application context to the constructor
//    @Provides
//    @ApplicationContext
//    @ActivityScope
//    GithubUserListPresenter providesGithubUserListPresenter(
//            Context context,
//            GithubUsersView githubUsersView,
//            GithubUsersAPI githubUsersAPI
//    ) {
//        return new GithubUserListPresenter(context, githubUsersView, githubUsersAPI);
//    }

}
