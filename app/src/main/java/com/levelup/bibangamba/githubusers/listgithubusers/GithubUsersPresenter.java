package com.levelup.bibangamba.githubusers.listgithubusers;

import android.content.Context;
import android.support.annotation.NonNull;

import com.levelup.bibangamba.githubusers.model.GithubUser;
import com.levelup.bibangamba.githubusers.model.GithubUsersResponse;
import com.levelup.bibangamba.githubusers.service.GithubService;
import com.levelup.bibangamba.githubusers.util.CheckNetworkConnection;
import com.levelup.bibangamba.githubusers.util.EspressoIdlingResource;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.common.base.Preconditions.checkNotNull;

public class GithubUsersPresenter implements GithubUsersContract.Presenter {
    private final GithubUsersContract.View mGithubUsersView;

    public GithubUsersPresenter(@NonNull GithubUsersContract.View githubUsersView) {
        mGithubUsersView = checkNotNull(githubUsersView,
                "githubUsersView cannot be null!");
        mGithubUsersView.setPresenter(this);
    }

    @Override
    public void loadGithubUsers() {
        Context context = ((GithubUsersFragment) mGithubUsersView).getContext();
        if (new CheckNetworkConnection(context).isNetworkAvailable()) {
            makeNetworkRequest();
        } else {
            mGithubUsersView.showNoInternetSnackbar();
        }
    }

    private void makeNetworkRequest() {
        EspressoIdlingResource.increment();
        mGithubUsersView.showLoadingIndicator(true);
        GithubService.getServiceInstance()
                .getAllUsers()
                .enqueue(new Callback<GithubUsersResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<GithubUsersResponse> call,
                                           @NonNull Response<GithubUsersResponse> response) {
                        GithubUsersResponse githubUsersResponse = response.body();
                        List<GithubUser> users = githubUsersResponse != null ?
                                githubUsersResponse.getUsers() : new ArrayList<>();
                        if (!mGithubUsersView.isActive()) {
                            return;
                        }

                        mGithubUsersView.showLoadingIndicator(false);
                        mGithubUsersView.showGithubUsers(users);
                        EspressoIdlingResource.decrement();
                    }

                    @Override
                    public void onFailure(@NonNull Call<GithubUsersResponse> call,
                                          @NonNull Throwable t) {
//                        if (!mGithubUsersView.isActive()) {
//                            return;
//                        }
//
//                        mGithubUsersView.showLoadingIndicator(false);
//                        mGithubUsersView.showLoadingGithubUsersError();
//                        EspressoIdlingResource.decrement();
                    }

                });
    }

    @Override
    public void start() {

        loadGithubUsers();
    }
}
