package com.levelup.bibangamba.githubusers.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.Nullable;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.levelup.bibangamba.githubusers.BR;

public class GithubUserViewModel extends BaseObservable {
    private GithubUser githubUser;
    private boolean imageVisibility;

    @Bindable
    public GithubUser getGithubUser() {
        return githubUser;
    }

    public void setGithubUser(GithubUser githubUser) {
        this.githubUser = githubUser;
        notifyPropertyChanged(BR.githubUser);
    }

    @Bindable
    public boolean isImageVisibility() {
        return imageVisibility;
    }

    public void setImageVisibility(boolean imageVisibility) {
        this.imageVisibility = imageVisibility;
        notifyPropertyChanged(BR.imageVisibility);
    }

    public RequestListener getCustomRequestListener() {
        return new RequestListener() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Object resource, Object model, Target target, DataSource dataSource, boolean isFirstResource) {
                setImageVisibility(true);
                return false;
            }
        };
    }
}
