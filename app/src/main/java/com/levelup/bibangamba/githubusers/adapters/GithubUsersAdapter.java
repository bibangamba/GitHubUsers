package com.levelup.bibangamba.githubusers.adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.levelup.bibangamba.githubusers.R;
import com.levelup.bibangamba.githubusers.databinding.GithubUserGridItemBinding;
import com.levelup.bibangamba.githubusers.listgithubusers.GithubUsersContract;
import com.levelup.bibangamba.githubusers.listgithubusers.GithubUsersFragment;
import com.levelup.bibangamba.githubusers.model.GithubUser;

import java.util.List;

public class GithubUsersAdapter extends
        RecyclerView.Adapter<GithubUsersAdapter.GithubUserViewHolder> {
    private GithubUsersContract.View mGithubUsersView;
    private List<GithubUser> mGithubUsers;

    public GithubUsersAdapter(GithubUsersContract.View githubUsersFragment,
                              List<GithubUser> listOfGithubUsers) {
        this.mGithubUsersView = githubUsersFragment;
        this.mGithubUsers = listOfGithubUsers;
    }


    @NonNull
    @Override
    public GithubUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        GithubUserGridItemBinding githubUserGridItemBinding = DataBindingUtil
                .inflate(LayoutInflater.from(((GithubUsersFragment) mGithubUsersView).getContext()),
                        R.layout.github_user_grid_item,
                        parent, false);
        return new GithubUserViewHolder(githubUserGridItemBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull GithubUserViewHolder githubUserViewHolder, int position) {
        githubUserViewHolder.githubUserGridItemBinding.setUser(mGithubUsers.get(position));
        githubUserViewHolder.githubUserGridItemBinding
                .setGithubUsersView(mGithubUsersView);
        githubUserViewHolder.githubUserGridItemBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mGithubUsers.size();
    }

    static class GithubUserViewHolder extends RecyclerView.ViewHolder {
        GithubUserGridItemBinding githubUserGridItemBinding;

        GithubUserViewHolder(View itemView) {
            super(itemView);
            githubUserGridItemBinding = DataBindingUtil.bind(itemView);
        }
    }
}
