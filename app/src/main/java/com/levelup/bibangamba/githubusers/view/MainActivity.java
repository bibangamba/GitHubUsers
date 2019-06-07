package com.levelup.bibangamba.githubusers.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.Snackbar;
import android.support.test.espresso.IdlingResource;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.levelup.bibangamba.githubusers.R;
import com.levelup.bibangamba.githubusers.databinding.ActivityMainBinding;
import com.levelup.bibangamba.githubusers.model.GithubUser;
import com.levelup.bibangamba.githubusers.presenter.GithubUsersPresenter;
import com.levelup.bibangamba.githubusers.util.CheckNetworkConnection;
import com.levelup.bibangamba.githubusers.util.EspressoIdlingResource;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GithubUsersView, IMainActivity,
        SwipeRefreshLayout.OnRefreshListener {
    public static final String LIST_STATE_KEY = "recycler_list_state";
    public static final String GITHUB_USERS = "retrieved_github_users";
    public static final String GITHUB_USER_DETAILS = "github_user_details";

    Parcelable listState;
    RecyclerView.LayoutManager mLayoutManager;
    ArrayList<GithubUser> retrievedGithubUsers;
    GithubUsersPresenter githubUsersPresenter;

    ActivityMainBinding mActivityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityMainBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_main);
        mActivityMainBinding.githubUsersSwipeRefreshLayout.setOnRefreshListener(this);

        githubUsersPresenter = new GithubUsersPresenter(this, this);
        if (savedInstanceState == null) {
            loadGithubUsers();
        }
    }

    public void loadGithubUsers() {
        if (new CheckNetworkConnection(MainActivity.this).isNetworkAvailable()) {
            githubUsersPresenter.getGithubUsers();
            EspressoIdlingResource.increment();
        } else {
            mActivityMainBinding.githubUsersProgressBar.setVisibility(View.GONE);
            Snackbar.make(mActivityMainBinding.mainActivityConstraintLayout,
                    R.string.no_internet,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.retry, v -> loadGithubUsers())
                    .show();
        }
    }

    @Override
    public void githubUsersHaveBeenFetchedAndAreReadyForUse(List<GithubUser> githubUsers) {
        retrievedGithubUsers = (ArrayList<GithubUser>) githubUsers;
        mActivityMainBinding.recyclerViewLayout.recyclerView.setVisibility(View.VISIBLE);
        mActivityMainBinding.githubUsersProgressBar.setVisibility(View.GONE);

        mActivityMainBinding.setGithubUsers(retrievedGithubUsers);

        EspressoIdlingResource.decrement();
    }


    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putParcelableArrayList(GITHUB_USERS, retrievedGithubUsers);
        if (mActivityMainBinding.recyclerViewLayout.recyclerView.getLayoutManager() != null) {
            listState = mActivityMainBinding.recyclerViewLayout.recyclerView.getLayoutManager()
                    .onSaveInstanceState();
            state.putParcelable(LIST_STATE_KEY, listState);
        }
    }

    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        if (state != null) {
            retrievedGithubUsers = state.getParcelableArrayList(GITHUB_USERS);
            listState = state.getParcelable(LIST_STATE_KEY);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (listState != null) {
            mActivityMainBinding.setGithubUsers(retrievedGithubUsers);
            mActivityMainBinding.setListState(listState);
            mActivityMainBinding.recyclerViewLayout.recyclerView.setVisibility(View.VISIBLE);
            mActivityMainBinding.githubUsersProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onRefresh() {
        refreshGithubUsers();
    }

    private void refreshGithubUsers() {
        loadGithubUsers();
        mActivityMainBinding.githubUsersSwipeRefreshLayout.setRefreshing(false);
    }

    @VisibleForTesting
    public IdlingResource getCountingIdlingResource() {
        return EspressoIdlingResource.getIdlingResource();
    }

    @Override
    public void startDetailActivity(GithubUser githubUser) {
        Intent startDetailActivityIntent = new Intent(this, DetailActivity.class);
        startDetailActivityIntent.putExtra(GITHUB_USER_DETAILS, githubUser);
        startActivity(startDetailActivityIntent);
    }
}
