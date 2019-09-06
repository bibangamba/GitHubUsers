package com.levelup.bibangamba.githubusers;

import com.levelup.bibangamba.githubusers.model.GithubUser;

import java.util.List;

public interface BaseContract {
    interface Presenter<T>{
        void attach(T view);
        void detach();
    }

    interface View {
    }
}
