package com.levelup.bibangamba.githubusers.githubuserdetails;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.levelup.bibangamba.githubusers.model.GithubUser;
import com.levelup.bibangamba.githubusers.service.GithubService;
import com.levelup.bibangamba.githubusers.util.EspressoIdlingResource;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GithubDetailsPresenter implements GithubUserDetailsContract.Presenter {
    private GithubUserDetailsContract.View mGithubUserDetailsView;

    GithubDetailsPresenter(GithubUserDetailsContract.View githubUserDetailsView) {
        mGithubUserDetailsView = githubUserDetailsView;
        mGithubUserDetailsView.setPresenter(this);
    }

    @Override
    public void triggerShareAction(GithubUser user) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND)
                .setType("text/plain")
                .putExtra(Intent.EXTRA_TEXT,
                        String.format("Check out this awesome developer @%s, %s.",
                                user.getUsername(), user.getProfileUrl()));
        ((GithubUserDetailsFragment) mGithubUserDetailsView).startActivity(
                Intent.createChooser(shareIntent,
                        String.format("Share @%s's profile using:", user.getUsername())));
    }

    @Override
    public void expandGithubUserProfileImage() {
        //not needed at the moment
    }

    @Override
    public void loadGithubUserDetails(String username) {
        EspressoIdlingResource.increment();
        GithubService.getServiceInstance()
                .getUserInformation(username)
                .enqueue((new Callback<GithubUser>() {
                    @Override
                    public void onResponse(@NonNull Call<GithubUser> call,
                                           @NonNull Response<GithubUser> response) {
                        GithubUser githubUser = response.body();
                        mGithubUserDetailsView.loadMoreGithubUserDetails(githubUser);
                        EspressoIdlingResource.decrement();
                    }

                    @Override
                    public void onFailure(@NonNull Call<GithubUser> call, @NonNull Throwable t) {
//                        mGithubUserDetailsView.showErrorOnFailingToLoadGithubUserDetails();
//                        EspressoIdlingResource.decrement();
                    }
                }));
    }

    @Override
    public void start() {
        mGithubUserDetailsView.requestGithubUserDetailsLoading();
    }
}
