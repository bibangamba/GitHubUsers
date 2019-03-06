package view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.levelup.bibangamba.githubusers.R;

import model.GithubUsers;
import presenter.GithubUserDetailsPresenter;

public class DetailActivity extends AppCompatActivity implements GithubUserDetailsView {
    ImageView profilePictureImageView;
    TextView usernameTextView;
    TextView profileUrlTextView;
    TextView organisationTextView;
    TextView followingTextView;
    TextView followersTextView;
    TextView reposTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_details_view);
        Intent getIntentThatLaunchDetailActivity = getIntent();
        GithubUsers userDetails = getIntentThatLaunchDetailActivity.getParcelableExtra(getString(R.string.github_user_details));

        if (savedInstanceState == null) {
            GithubUserDetailsPresenter githubUserDetailsPresenter= new GithubUserDetailsPresenter(this, this);
            githubUserDetailsPresenter.getGithubUserInfo(userDetails.getUsername());
        }

        usernameTextView = findViewById(R.id.usernameTextView);
        profileUrlTextView = findViewById(R.id.profileUrlTextView);
        organisationTextView = findViewById(R.id.organizationNameTextView);
        followingTextView = findViewById(R.id.followsValueTextView);
        followersTextView = findViewById(R.id.followersValueTextView);
        reposTextView = findViewById(R.id.reposValueTextView);
        profilePictureImageView = findViewById(R.id.profilePictureImageView);
        Glide
                .with(this)
                .load(userDetails.getProfilePicture())
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .into(profilePictureImageView);
        usernameTextView.setText(userDetails.getUsername());
        profileUrlTextView.setText(userDetails.getProfileUrl());
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
        followingTextView.setText(githubUser.getFollowing());
        followersTextView.setText(githubUser.getFollowers());
        reposTextView.setText(githubUser.getRepos());
        organisationTextView.setText(githubUser.getCompany());
    }
}