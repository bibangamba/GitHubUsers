package com.levelup.bibangamba.githubusers.di.component;

import android.app.Application;

import com.levelup.bibangamba.githubusers.di.module.ApplicationModule;
import com.levelup.bibangamba.githubusers.di.module.RetrofitModule;
import com.levelup.bibangamba.githubusers.view.UserDetailActivity;
import com.levelup.bibangamba.githubusers.view.UserListActivity;

import dagger.Component;

@Component(modules = {ApplicationModule.class, RetrofitModule.class})
public interface AppComponent {

    void inject(Application application);

    void inject(UserListActivity userListActivity);

    void inject(UserDetailActivity userDetailActivity);


}
