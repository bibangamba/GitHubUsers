package com.levelup.bibangamba.githubusers.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.levelup.bibangamba.githubusers.BaseApplication;
import com.levelup.bibangamba.githubusers.R;
import com.levelup.bibangamba.githubusers.model.GithubUser;
import com.levelup.bibangamba.githubusers.util.EspressoIdlingResource;

import javax.inject.Inject;

public class UserDetailActivity extends AppCompatActivity implements
        GithubUsersDetailContract.GithubUserDetailView {
    ImageView profilePictureImageView;
    TextView usernameTextView;
    TextView profileUrlTextView;
    TextView organisationTextView;
    TextView followingTextView;
    TextView followersTextView;
    TextView reposTextView;
    ImageButton shareButton;
    GithubUser userDetails;

    @Inject
    GithubUsersDetailContract.GithubUserDetailPresenter githubUserDetailsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_details_view);
        ((BaseApplication) getApplication()).getApplicationComponent().inject(this);
        githubUserDetailsPresenter.attach(this);

        setupUI();
        userDetails = getIntent().getParcelableExtra(getString(R.string.github_user_details));

        if (savedInstanceState == null) {
            githubUserDetailsPresenter.getGithubUserInfo(userDetails.getUsername());
            EspressoIdlingResource.increment();
        }

        renderPartialUserInfo();
        shareButton.setOnClickListener(v -> handleShareButtonClick());
    }

    private void renderPartialUserInfo() {
        Glide
                .with(this)
                .load(userDetails.getProfilePicture())
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .into(profilePictureImageView);
        usernameTextView.setText(userDetails.getUsername());
        profileUrlTextView.setText(userDetails.getProfileUrl());
    }

    private void setupUI() {
        usernameTextView = findViewById(R.id.usernameTextView);
        profileUrlTextView = findViewById(R.id.profileUrlTextView);
        organisationTextView = findViewById(R.id.organizationNameTextView);
        followingTextView = findViewById(R.id.followsValueTextView);
        followersTextView = findViewById(R.id.followersValueTextView);
        reposTextView = findViewById(R.id.reposValueTextView);
        profilePictureImageView = findViewById(R.id.profilePictureImageView);
        shareButton = findViewById(R.id.shareButton);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void handleRetrievedUserInfo(GithubUser githubUser) {
        renderCompleteUserInfo(githubUser);
        EspressoIdlingResource.decrement();
    }

    private void renderCompleteUserInfo(GithubUser githubUser) {
        followingTextView.setText(githubUser.getFollowing());
        followersTextView.setText(githubUser.getFollowers());
        reposTextView.setText(githubUser.getRepos());
        organisationTextView.setText(githubUser.getCompany());
    }

    private void handleShareButtonClick() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT,
                String.format("Check out this awesome developer @%s, %s.",
                        userDetails.getUsername(),
                        userDetails.getProfileUrl()));
        startActivity(Intent.createChooser(shareIntent,
                String.format("Share @%s's profile using:", userDetails.getUsername())));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        githubUserDetailsPresenter.detach();
    }

    @VisibleForTesting
    public IdlingResource getCountingIdlingResource() {
        return EspressoIdlingResource.getIdlingResource();
    }
}
