package com.levelup.bibangamba.githubusers.listgithubusers;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.levelup.bibangamba.githubusers.R;
import com.levelup.bibangamba.githubusers.databinding.FragmentGithubUsersBinding;
import com.levelup.bibangamba.githubusers.githubuserdetails.GithubUserDetailsActivity;
import com.levelup.bibangamba.githubusers.model.GithubUser;
import com.levelup.bibangamba.githubusers.util.Constants;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link GithubUsersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GithubUsersFragment extends Fragment implements GithubUsersContract.View,
        SwipeRefreshLayout.OnRefreshListener {


    FragmentGithubUsersBinding mFragmentGithubUsersBinding;
    Parcelable mListState;
    private GithubUsersContract.Presenter mPresenter;
    private List<GithubUser> mGithubUsers;


    public GithubUsersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment.
     *
     * @return A new instance of fragment GithubUsersFragment.
     */
    public static GithubUsersFragment newInstance() {
        return new GithubUsersFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragmentGithubUsersBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_github_users, container, false);
        mFragmentGithubUsersBinding.githubUsersSwipeRefreshLayout.setOnRefreshListener(this);
        return mFragmentGithubUsersBinding.getRoot();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(Constants.GITHUB_USERS_KEY, (ArrayList<GithubUser>) mGithubUsers);
        RecyclerView.LayoutManager layoutManager =
                mFragmentGithubUsersBinding.recyclerViewLayout.recyclerView.getLayoutManager();
        if (layoutManager != null) {
            mListState = layoutManager.onSaveInstanceState();
            outState.putParcelable(Constants.LIST_STATE_KEY, mListState);
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            mGithubUsers = savedInstanceState.getParcelableArrayList(Constants.GITHUB_USERS_KEY);
            mListState = savedInstanceState.getParcelable(Constants.LIST_STATE_KEY);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
        if (mListState != null && mGithubUsers != null) {
            mFragmentGithubUsersBinding.setGithubUsers(mGithubUsers);
            mFragmentGithubUsersBinding.setGithubUsersView(this);
            new Handler().postDelayed(() ->
                    mFragmentGithubUsersBinding.setListState(mListState), 50);
            showLoadingIndicator(false);
        }
    }

    @Override
    public void showLoadingIndicator(boolean active) {
        mFragmentGithubUsersBinding
                .githubUsersProgressBar.setVisibility(active ? View.VISIBLE : View.GONE);
        mFragmentGithubUsersBinding
                .githubUsersSwipeRefreshLayout.setVisibility(active ? View.GONE : View.VISIBLE);
        mFragmentGithubUsersBinding.githubUsersSwipeRefreshLayout.setRefreshing(active);
    }


//    @Override
//    public void showLoadingGithubUsersError() {
//        //not currently needed
//    }

    @Override
    public void showGithubUsers(List<GithubUser> users) {
        mGithubUsers = users;
        mFragmentGithubUsersBinding.setGithubUsers(users);
        mFragmentGithubUsersBinding.setGithubUsersView(this);
    }

    @Override
    public void showGithubUserDetailsUI(GithubUser githubUser) {
        Intent startDetailActivityIntent = new Intent(getActivity(), GithubUserDetailsActivity.class);
        startDetailActivityIntent.putExtra(Constants.GITHUB_USER_DETAILS_KEY, githubUser);
        startActivity(startDetailActivityIntent);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setPresenter(GithubUsersContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onRefresh() {
        refreshGithubUsers();
    }


    @Override
    public void showNoInternetSnackbar() {
        Snackbar.make(mFragmentGithubUsersBinding.mainActivityConstraintLayout,
                R.string.no_internet,
                Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.retry, v -> mPresenter.loadGithubUsers())
                .show();
    }

    private void refreshGithubUsers() {
        mPresenter.loadGithubUsers();
    }
}
