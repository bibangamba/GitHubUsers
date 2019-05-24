package com.levelup.bibangamba.githubusers.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.levelup.bibangamba.githubusers.R;
import com.levelup.bibangamba.githubusers.model.GithubUser;
import com.levelup.bibangamba.githubusers.service.GithubService;
import com.levelup.bibangamba.githubusers.view.GithubUserDetailsView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GithubUserDetailsPresenter {
    private Context context;
    private String TAG;
    private GithubUserDetailsView githubUserDetailsView;
    private GithubService githubService;

    public GithubUserDetailsPresenter(Context context, GithubUserDetailsView view, GithubService githubService) {
        this.context = context;
        TAG = context.getString(R.string.GithubUserDetailsPresenterTag);
        this.githubUserDetailsView = view;
        this.githubService = githubService;
    }

    public void getGithubUserInfo(String username) {
        githubService
                .getApi()
                .getUserInformation(username)
                .enqueue((new Callback<GithubUser>() {
                    @Override
                    public void onResponse(@NonNull Call<GithubUser> call, @NonNull Response<GithubUser> response) {
                        GithubUser githubUser = response.body();
                            githubUserDetailsView.githubUserInformationFetchComplete(githubUser);
                    }

                    @Override
                    public void onFailure(@NonNull Call<GithubUser> call, @NonNull Throwable t) {
                        try {
                            throw new InterruptedException(context.getString(R.string.error_message_when_retrieving_data_from_api));
                        } catch (InterruptedException e) {
                            Log.e(TAG, e.toString());
                        }
                    }
                }));
    }

}
