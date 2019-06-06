package com.levelup.bibangamba.githubusers.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.levelup.bibangamba.githubusers.R;
import com.levelup.bibangamba.githubusers.model.GithubUser;
import com.levelup.bibangamba.githubusers.model.GithubUsersResponse;
import com.levelup.bibangamba.githubusers.service.GithubService;
import com.levelup.bibangamba.githubusers.view.GithubUsersView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GithubUsersPresenter {
    private Context context;
    private String TAG;
    private GithubUsersView githubUsersView;
    private GithubService githubService;

    public GithubUsersPresenter(Context context, GithubUsersView view) {
        this.context = context;
        TAG = context.getString(R.string.GithubUsersPresenterTag);
        this.githubUsersView = view;
        this.githubService = new GithubService();
    }

    public void getGithubUsers() {
        githubService
                .getApi()
                .getAllUsers()
                .enqueue(new Callback<GithubUsersResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<GithubUsersResponse> call,
                                           @NonNull Response<GithubUsersResponse> response) {
                        GithubUsersResponse githubUsersResponse = response.body();
                        List<GithubUser> users = githubUsersResponse != null ?
                                githubUsersResponse.getUsers() : new ArrayList<>();
                        githubUsersView.githubUsersHaveBeenFetchedAndAreReadyForUse(users);
                    }

                    @Override
                    public void onFailure(@NonNull Call<GithubUsersResponse> call,
                                          @NonNull Throwable t) {
                        try {
                            throw new InterruptedException(
                                    context.getString(
                                            R.string.error_message_when_retrieving_data_from_api));
                        } catch (InterruptedException e) {
                            Log.e(TAG, e.toString());
                        }
                    }
                });
    }
}
