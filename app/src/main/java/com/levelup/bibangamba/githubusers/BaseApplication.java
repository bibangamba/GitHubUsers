package com.levelup.bibangamba.githubusers;

import android.app.Activity;
import android.app.Application;

import com.levelup.bibangamba.githubusers.di.component.AppComponent;
import com.levelup.bibangamba.githubusers.di.component.DaggerAppComponent;
import com.levelup.bibangamba.githubusers.di.module.PresenterModule;
import com.levelup.bibangamba.githubusers.di.module.RetrofitModule;

import static com.levelup.bibangamba.githubusers.util.Constants.BASE_URL;

public class BaseApplication extends Application {
    AppComponent mAppComponent;

    public static BaseApplication getApplicationInstance(Activity activity) {
        return (BaseApplication) activity.getApplication();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerAppComponent.builder()
                .retrofitModule(new RetrofitModule(BASE_URL))
                .presenterModule(new PresenterModule())
                .build();
        mAppComponent.inject(this);
    }

    public AppComponent getApplicationComponent() {
        return mAppComponent;
    }
}
