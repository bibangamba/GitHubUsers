package com.levelup.bibangamba.githubusers.view;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.Snackbar;
import android.support.test.espresso.IdlingResource;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.levelup.bibangamba.githubusers.BaseApplication;
import com.levelup.bibangamba.githubusers.R;
import com.levelup.bibangamba.githubusers.adapters.GithubUsersAdapter;
import com.levelup.bibangamba.githubusers.model.GithubUser;
import com.levelup.bibangamba.githubusers.presenter.GithubUserListPresenter;
import com.levelup.bibangamba.githubusers.util.CheckNetworkConnection;
import com.levelup.bibangamba.githubusers.util.EspressoIdlingResource;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class UserListActivity extends AppCompatActivity implements
        GithubUsersListContract.GithubUsersView {

    public static final String LIST_STATE_KEY = "recycler_list_state";
    public static final String GITHUB_USERS = "retrieved_github_users";
    private static final String TAG = "UserListActivity";
    public GithubUsersAdapter.ClickListener onItemClickListener;
    ArrayList<GithubUser> mRetrievedGithubUsers;
    ProgressBar githubUsersProgressBar;

    @Inject
    GithubUserListPresenter mGithubUserListPresenter;

    GithubUsersAdapter mGithubUsersAdapter;

    private RecyclerView githubUsersRecyclerView;
    private Parcelable listState;
    private RecyclerView.LayoutManager layoutManager;
    private SwipeRefreshLayout githubUsersSwipeToRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((BaseApplication) getApplication())
                .getApplicationComponent()
                .inject(this);
        setUpUI();

        if (savedInstanceState == null) {
            loadGithubUsers();
        }

        onItemClickListener = position -> {
            Intent startDetailActivityIntent = new Intent(this, UserDetailActivity.class);
            startDetailActivityIntent.putExtra(getString(R.string.github_user_details),
                    mRetrievedGithubUsers.get(position));
            startActivity(startDetailActivityIntent);
        };

        mGithubUsersAdapter = new GithubUsersAdapter(onItemClickListener);

        mGithubUserListPresenter.attach(this);

    }

    private void setUpUI() {
        githubUsersRecyclerView = findViewById(R.id.recycler_view);
        githubUsersSwipeToRefreshLayout = findViewById(R.id.github_users_swipe_refresh_layout);
        githubUsersProgressBar = findViewById(R.id.github_users_progress_bar);
        githubUsersRecyclerView.setHasFixedSize(true);
        githubUsersSwipeToRefreshLayout.setOnRefreshListener(this::refreshGithubUsers);
        if (this.getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_PORTRAIT) {
            layoutManager = new GridLayoutManager(UserListActivity.this, 2);
        } else {
            layoutManager = new GridLayoutManager(UserListActivity.this, 3);
        }

        githubUsersRecyclerView.setLayoutManager(layoutManager);
    }

    public void loadGithubUsers() {
        if (new CheckNetworkConnection(UserListActivity.this).isNetworkAvailable()) {
            mGithubUserListPresenter.getGithubUsers();
            EspressoIdlingResource.increment();
        } else {
            githubUsersProgressBar.setVisibility(View.GONE);
            Snackbar.make(findViewById(R.id.main_activity_constraint_layout),
                    R.string.no_internet,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.retry, new NoInternetSnackBarListener())
                    .show();
        }
    }

    @Override
    public void handleRetrievedUsers(List<GithubUser> githubUsers) {
        mRetrievedGithubUsers = (ArrayList<GithubUser>) githubUsers;
        githubUsersRecyclerView.setAdapter(mGithubUsersAdapter);

        setListDataAndHideLoader();

        EspressoIdlingResource.decrement();
    }

    private void setListDataAndHideLoader() {
        mGithubUsersAdapter.setData(mRetrievedGithubUsers);
        githubUsersRecyclerView.setVisibility(View.VISIBLE);
        githubUsersProgressBar.setVisibility(View.GONE);
    }


    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putParcelableArrayList(GITHUB_USERS, mRetrievedGithubUsers);
        listState = layoutManager.onSaveInstanceState();
        state.putParcelable(LIST_STATE_KEY, listState);
    }

    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        if (state != null) {
            mRetrievedGithubUsers = state.getParcelableArrayList(GITHUB_USERS);
            listState = state.getParcelable(LIST_STATE_KEY);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (listState != null) {
            githubUsersRecyclerView.setAdapter(mGithubUsersAdapter);
            layoutManager.onRestoreInstanceState(listState);
            setListDataAndHideLoader();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGithubUserListPresenter.detach();
    }

    private void refreshGithubUsers() {
        loadGithubUsers();
        githubUsersSwipeToRefreshLayout.setRefreshing(false);
    }

    @VisibleForTesting
    public IdlingResource getCountingIdlingResource() {
        return EspressoIdlingResource.getIdlingResource();
    }

    private class NoInternetSnackBarListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            loadGithubUsers();
        }
    }
}
