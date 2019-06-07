package com.levelup.bibangamba.githubusers.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.levelup.bibangamba.githubusers.R;
import com.levelup.bibangamba.githubusers.databinding.UserDetailsViewBinding;
import com.levelup.bibangamba.githubusers.model.GithubUser;
import com.levelup.bibangamba.githubusers.model.GithubUserViewModel;
import com.levelup.bibangamba.githubusers.presenter.GithubUserDetailsPresenter;
import com.levelup.bibangamba.githubusers.service.GithubService;
import com.levelup.bibangamba.githubusers.util.EspressoIdlingResource;

public class DetailActivity extends AppCompatActivity implements GithubUserDetailsView {
    GithubUser userDetails;

    UserDetailsViewBinding mUserDetailsViewBinding;
    GithubUserViewModel mGithubUserViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserDetailsViewBinding = DataBindingUtil.setContentView(this,
                R.layout.user_details_view);


        Intent intentThatLaunchDetailActivity = getIntent();
        userDetails = intentThatLaunchDetailActivity.getParcelableExtra(getString(R.string.github_user_details));
        mGithubUserViewModel = new GithubUserViewModel();
        mGithubUserViewModel.setGithubUser(userDetails);
        mUserDetailsViewBinding.setUserViewModel(mGithubUserViewModel);


        if (savedInstanceState == null) {
            new GithubUserDetailsPresenter(this, this, new GithubService())
                    .getGithubUserInfo(userDetails.getUsername());
            EspressoIdlingResource.increment();
        }

        mUserDetailsViewBinding.shareButton
                .setOnClickListener(viewClick -> handleShareButtonClick());
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void githubUserInformationFetchComplete(GithubUser githubUser) {
        mGithubUserViewModel.setGithubUser(githubUser);
        EspressoIdlingResource.decrement();
    }

    private void handleShareButtonClick() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND)
                .setType("text/plain")
                .putExtra(Intent.EXTRA_TEXT,
                        String.format("Check out this awesome developer @%s, %s.",
                                userDetails.getUsername(), userDetails.getProfileUrl()));
        startActivity(Intent.createChooser(shareIntent,
                String.format("Share @%s's profile using:", userDetails.getUsername())));
    }

    @VisibleForTesting
    public IdlingResource getCountingIdlingResource() {
        return EspressoIdlingResource.getIdlingResource();
    }
}
