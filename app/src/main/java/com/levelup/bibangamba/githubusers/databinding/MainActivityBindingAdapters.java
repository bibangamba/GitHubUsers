package com.levelup.bibangamba.githubusers.databinding;

import android.content.Context;
import android.content.res.Configuration;
import android.databinding.BindingAdapter;
import android.os.Parcelable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.levelup.bibangamba.githubusers.adapters.GithubUsersAdapter;
import com.levelup.bibangamba.githubusers.model.GithubUser;

import java.util.List;

public class MainActivityBindingAdapters {
    private static final int NUMBER_OF_COLUMNS_2 = 2;
    private static final int NUMBER_OF_COLUMNS_3 = 3;

    private MainActivityBindingAdapters() {
    }

    @BindingAdapter("githubUsersAdapter")
    public static void setGithubUsers(RecyclerView recyclerView, List<GithubUser> githubUsers) {
        if (githubUsers == null) {
            return;
        }
        recyclerView.setHasFixedSize(true);
        if (recyclerView.getLayoutManager() == null) {
            RecyclerView.LayoutManager mLayoutManager = setNumberOfGridColumnsDependingOnScreenOrientation(
                    recyclerView.getContext());
            recyclerView.setLayoutManager(mLayoutManager);
        }
        GithubUsersAdapter githubUsersAdapter = (GithubUsersAdapter) recyclerView.getAdapter();
        if (githubUsersAdapter == null) {
            githubUsersAdapter = new GithubUsersAdapter(recyclerView.getContext(), githubUsers);
            recyclerView.setAdapter(githubUsersAdapter);
        }

    }

    @BindingAdapter("restoreListStateAdapter")
    public static void restoreListState(RecyclerView recyclerView, Parcelable listState) {
        if (listState == null) {
            return;
        }
        if (recyclerView.getLayoutManager() != null) {
            recyclerView.getLayoutManager().onRestoreInstanceState(listState);
        }
    }

    private static RecyclerView.LayoutManager setNumberOfGridColumnsDependingOnScreenOrientation(
            Context context) {
        if (context.getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_PORTRAIT) {
            return new GridLayoutManager(context, NUMBER_OF_COLUMNS_2);
        } else {
            return new GridLayoutManager(context, NUMBER_OF_COLUMNS_3);
        }
    }
}

