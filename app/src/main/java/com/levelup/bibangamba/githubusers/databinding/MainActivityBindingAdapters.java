package com.levelup.bibangamba.githubusers.databinding;

import android.content.Context;
import android.content.res.Configuration;
import android.databinding.BindingAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.levelup.bibangamba.githubusers.adapters.GithubUsersAdapter;
import com.levelup.bibangamba.githubusers.model.GithubUser;

import java.util.List;

public class MainActivityBindingAdapters {
    private static final int NUMBER_OF_COLUMNS_2 = 2;
    private static final int NUMBER_OF_COLUMNS_3 = 3;
    private static RecyclerView.LayoutManager mLayoutManager;

    private MainActivityBindingAdapters() {
    }

    @BindingAdapter("githubUsersAdapter")
    public static void setGithubUsers(RecyclerView recyclerView, List<GithubUser> githubUsers) {
        if (githubUsers == null) {
            return;
        }
        recyclerView.setHasFixedSize(true);
        if (recyclerView.getLayoutManager() == null) {
            mLayoutManager = new GridLayoutManager(recyclerView.getContext(),
                    NUMBER_OF_COLUMNS_2);
            recyclerView.setLayoutManager(mLayoutManager);
        }
        setNumberOFGridColumnsDependingOnScreenOrientation(recyclerView.getContext());
        GithubUsersAdapter githubUsersAdapter = (GithubUsersAdapter) recyclerView.getAdapter();
        if (githubUsersAdapter == null) {
            githubUsersAdapter = new GithubUsersAdapter(recyclerView.getContext(), githubUsers);
            recyclerView.setAdapter(githubUsersAdapter);
        }

    }

    private static void setNumberOFGridColumnsDependingOnScreenOrientation(Context context) {
        if (context.getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_PORTRAIT) {
            mLayoutManager = new GridLayoutManager(context, NUMBER_OF_COLUMNS_2);
        } else {
            mLayoutManager = new GridLayoutManager(context, NUMBER_OF_COLUMNS_3);
        }
    }
}

