package com.levelup.bibangamba.githubusers.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.levelup.bibangamba.githubusers.R;
import com.levelup.bibangamba.githubusers.model.GithubUsers;
import com.levelup.bibangamba.githubusers.presenter.GithubUserDetailsPresenter;
import com.levelup.bibangamba.githubusers.service.GithubService;
import com.levelup.bibangamba.githubusers.util.EspressoIdlingResource;

public class DetailActivity extends AppCompatActivity implements GithubUserDetailsView, View.OnClickListener {
    ImageView profilePictureImageView;
    TextView usernameTextView;
    TextView profileUrlTextView;
    TextView organisationTextView;
    TextView followingTextView;
    TextView followersTextView;
    TextView reposTextView;
    ImageButton shareButton;
    GithubUsers userDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_details_view);
        Intent getIntentThatLaunchDetailActivity = getIntent();
        userDetails = getIntentThatLaunchDetailActivity.getParcelableExtra(getString(R.string.github_user_details));

        if (savedInstanceState == null) {
            GithubUserDetailsPresenter githubUserDetailsPresenter = new GithubUserDetailsPresenter(this, this, new GithubService());
            githubUserDetailsPresenter.getGithubUserInfo(userDetails.getUsername());
            EspressoIdlingResource.increment();
        }

        usernameTextView = findViewById(R.id.usernameTextView);
        profileUrlTextView = findViewById(R.id.profileUrlTextView);
        organisationTextView = findViewById(R.id.organizationNameTextView);
        followingTextView = findViewById(R.id.followsValueTextView);
        followersTextView = findViewById(R.id.followersValueTextView);
        reposTextView = findViewById(R.id.reposValueTextView);
        profilePictureImageView = findViewById(R.id.profilePictureImageView);
        shareButton = findViewById(R.id.shareButton);
        Glide
                .with(this)
                .load(userDetails.getProfilePicture())
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .into(profilePictureImageView);
        usernameTextView.setText(userDetails.getUsername());
        profileUrlTextView.setText(userDetails.getProfileUrl());
        shareButton.setOnClickListener(this);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void githubUserInformationFetchComplete(GithubUsers githubUser) {
        if (githubUser == null) {
            //create mock githubUser since github api request returned null -- TESTING ONLY --
            //mocked/faked user data when github api call get null response
            githubUser = new GithubUsers();
            final String username = "nellyk";
            final String profilePicture = "https://avatars3.githubusercontent.com/u/3062772?v=4";
            final String profileURL = "https://github.com/nellyk";
            final String followers = "50";
            final String following = "107";
            final String repos = "44";
            final String company = "Andela";

            githubUser.setUsername(username);
            githubUser.setProfilePicture(profilePicture);
            githubUser.setProfileUrl(profileURL);
            githubUser.setFollowers(followers);
            githubUser.setFollowing(following);
            githubUser.setRepos(repos);
            githubUser.setCompany(company);
        }

        followingTextView.setText(githubUser.getFollowing());
        followersTextView.setText(githubUser.getFollowers());
        reposTextView.setText(githubUser.getRepos());
        organisationTextView.setText(githubUser.getCompany());
        EspressoIdlingResource.decrement();
    }

    @Override
    public void onClick(View clickedView) {
        int viewId = clickedView.getId();
        if (viewId == R.id.shareButton) {
            handleShareButtonClick();
        }
    }

    private void handleShareButtonClick() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, String.format("Check out this awesome developer @%s, %s.", userDetails.getUsername(), userDetails.getProfileUrl()));
        startActivity(Intent.createChooser(shareIntent, String.format("Share @%s's profile using:", userDetails.getUsername())));
    }

    @VisibleForTesting
    public IdlingResource getCountingIdlingResource() {
        return EspressoIdlingResource.getIdlingResource();
    }
}
