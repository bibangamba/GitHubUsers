package com.levelup.bibangamba.githubusers.githubuserdetails;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.levelup.bibangamba.githubusers.R;
import com.levelup.bibangamba.githubusers.databinding.ActivityGithubUserDetailBinding;
import com.levelup.bibangamba.githubusers.util.ActivityUtils;
import com.levelup.bibangamba.githubusers.util.EspressoIdlingResource;

public class GithubUserDetailsActivity extends AppCompatActivity {
    ActivityGithubUserDetailBinding mActivityGithubUserDetailBinding;
    GithubDetailsPresenter mGithubDetailsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityGithubUserDetailBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_github_user_detail);

        GithubUserDetailsFragment githubUserDetailsFragment =
                (GithubUserDetailsFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.github_user_details_content_frame);
        if (githubUserDetailsFragment == null) {
            githubUserDetailsFragment = GithubUserDetailsFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    githubUserDetailsFragment, R.id.github_user_details_content_frame);
        }
        mGithubDetailsPresenter = new GithubDetailsPresenter(githubUserDetailsFragment);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @VisibleForTesting
    public IdlingResource getCountingIdlingResource() {
        return EspressoIdlingResource.getIdlingResource();
    }
}
