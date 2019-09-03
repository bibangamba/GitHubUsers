package com.levelup.bibangamba.githubusers.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.levelup.bibangamba.githubusers.model.GithubUser;
import com.levelup.bibangamba.githubusers.model.GithubUsersResponse;
import com.levelup.bibangamba.githubusers.service.GithubUsersAPI;
import com.levelup.bibangamba.githubusers.view.GithubUsersListContract;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GithubUserListPresenter implements GithubUsersListContract.GithubUsersPresenter {
    private static final String TAG = "GithubUserListPresenter";
    private GithubUsersListContract.GithubUsersView githubUsersView;
    private GithubUsersAPI mGithubUsersAPI;

    @Inject
    public GithubUserListPresenter(GithubUsersAPI githubUsersAPI) {
        this.mGithubUsersAPI = githubUsersAPI;
    }

    @Override
    public void getGithubUsers() {
        mGithubUsersAPI.getAllUsers()
                .enqueue(new Callback<GithubUsersResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<GithubUsersResponse> call,
                                           @NonNull Response<GithubUsersResponse> response) {
                        GithubUsersResponse githubUsersResponse = response.body();
                        assert githubUsersResponse != null;
                        List<GithubUser> users = githubUsersResponse.getUsers();
                        githubUsersView.handleRetrievedUsers(users);
                    }

                    @Override
                    public void onFailure(@NonNull Call<GithubUsersResponse> call,
                                          @NonNull Throwable t) {
                        try {
                            throw new InterruptedException("Failed to retrieve Github Users");
//                            throw new InterruptedException(context.getString(
//                            R.string.error_message_when_retrieving_data_from_api));
                        } catch (InterruptedException e) {
                            Log.e(TAG, e.toString());
                        }
                    }
                });
    }

    @Override
    public void attach(GithubUsersListContract.GithubUsersView view) {
        this.githubUsersView = view;
    }

    @Override
    public void detach() {
        githubUsersView = null;
    }


}
