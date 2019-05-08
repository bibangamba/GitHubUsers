package com.levelup.bibangamba.githubusers.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.levelup.bibangamba.githubusers.R;

import java.util.List;

import com.levelup.bibangamba.githubusers.model.GithubUsers;
import com.levelup.bibangamba.githubusers.view.DetailActivity;

public class GithubUsersAdapter extends
        RecyclerView.Adapter<GithubUsersAdapter.GithubUserViewHolder> {
    private Context context;
    private List<GithubUsers> githubUsers;
    private String GITHUB_USER_DETAILS;

    public GithubUsersAdapter(Context context, List<GithubUsers> listOfGithubUsers) {
        this.context = context;
        this.githubUsers = listOfGithubUsers;
        GITHUB_USER_DETAILS = context.getString(R.string.github_user_details);
    }


    @NonNull
    @Override
    public GithubUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View githubUserListItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.github_user_grid_item, parent, false);
        return new GithubUserViewHolder(githubUserListItem);
    }

    @Override
    public void onBindViewHolder(@NonNull GithubUserViewHolder githubUserViewHolder, int position) {
        githubUserViewHolder.githubUsernameTextView
                .setText(githubUsers.get(position).getUsername());
        Glide
                .with(context)
                .load(githubUsers.get(position).getProfilePicture())
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .into(githubUserViewHolder.profilePictureImageView);

        githubUserViewHolder.itemView.setOnClickListener(v -> {
            Intent startDetailActivityIntent = new Intent(context, DetailActivity.class);
            startDetailActivityIntent.putExtra(GITHUB_USER_DETAILS, githubUsers.get(position));
            context.startActivity(startDetailActivityIntent);
        });
    }

    @Override
    public int getItemCount() {
        return githubUsers.size();
    }

    static class GithubUserViewHolder extends RecyclerView.ViewHolder {
        TextView githubUsernameTextView;
        ImageView profilePictureImageView;

        GithubUserViewHolder(View itemView) {
            super(itemView);
            this.githubUsernameTextView = itemView.findViewById(R.id.githubUsernameTextView);
            this.profilePictureImageView = itemView.findViewById(R.id.profilePictureImageView);
        }
    }
}
