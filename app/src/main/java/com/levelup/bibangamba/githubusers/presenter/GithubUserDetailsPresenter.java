package com.levelup.bibangamba.githubusers.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.levelup.bibangamba.githubusers.model.GithubUser;
import com.levelup.bibangamba.githubusers.service.GithubUsersAPI;
import com.levelup.bibangamba.githubusers.view.GithubUsersDetailContract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GithubUserDetailsPresenter implements
        GithubUsersDetailContract.GithubUserDetailPresenter {
    private static final String TAG = "GHDetailsPresenter";
    private GithubUsersDetailContract.GithubUserDetailView githubUserDetailsView;
    private GithubUsersAPI mGithubUsersAPI;

    public GithubUserDetailsPresenter(GithubUsersAPI githubUsersAPI) {
        this.mGithubUsersAPI = githubUsersAPI;
    }

    @Override
    public void getGithubUserInfo(String username) {
        mGithubUsersAPI
                .getUserInformation(username)
                .enqueue((new Callback<GithubUser>() {
                    @Override
                    public void onResponse(@NonNull Call<GithubUser> call,
                                           @NonNull Response<GithubUser> response) {
                        GithubUser githubUser = response.body();
                        githubUserDetailsView.handleRetrievedUserInfo(githubUser);
                    }

                    @Override
                    public void onFailure(@NonNull Call<GithubUser> call, @NonNull Throwable t) {
                        try {
                            throw new InterruptedException("Failed to retrieve Github user info");
                        } catch (InterruptedException e) {
                            Log.e(TAG, e.toString());
                        }
                    }
                }));
    }

    @Override
    public void attach(GithubUsersDetailContract.GithubUserDetailView view) {
        githubUserDetailsView = view;
    }

    @Override
    public void detach() {
        githubUserDetailsView = null;
    }
}
