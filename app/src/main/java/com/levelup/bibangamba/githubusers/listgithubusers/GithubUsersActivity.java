package com.levelup.bibangamba.githubusers.listgithubusers;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;

import com.levelup.bibangamba.githubusers.R;
import com.levelup.bibangamba.githubusers.databinding.ActivityGithubUsersBinding;
import com.levelup.bibangamba.githubusers.util.ActivityUtils;
import com.levelup.bibangamba.githubusers.util.EspressoIdlingResource;

public class GithubUsersActivity extends AppCompatActivity {
    ActivityGithubUsersBinding githubUsersActivityBinding;
    GithubUsersPresenter mGithubUsersPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        githubUsersActivityBinding = DataBindingUtil
                .setContentView(this, R.layout.activity_github_users);

        GithubUsersFragment githubUsersFragment = (GithubUsersFragment)
                getSupportFragmentManager().findFragmentById(R.id.github_users_content_frame);

        if (githubUsersFragment == null) {
            githubUsersFragment = GithubUsersFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), githubUsersFragment,
                    R.id.github_users_content_frame);
        }
        mGithubUsersPresenter = new GithubUsersPresenter(githubUsersFragment);

    }

    @VisibleForTesting
    public IdlingResource getCountingIdlingResource() {
        return EspressoIdlingResource.getIdlingResource();
    }
}
