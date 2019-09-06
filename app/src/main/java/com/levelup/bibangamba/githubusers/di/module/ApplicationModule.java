package com.levelup.bibangamba.githubusers.di.module;

import android.content.Context;

import com.levelup.bibangamba.githubusers.di.qualifier.ApplicationContext;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private Context mContext;

    public ApplicationModule(Context applicationContext) {
        this.mContext = applicationContext;
    }

    @Provides
    @ApplicationContext
    Context providesApplicationContext() {
        return mContext;
    }
}
