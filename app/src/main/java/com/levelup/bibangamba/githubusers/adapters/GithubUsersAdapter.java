package com.levelup.bibangamba.githubusers.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.levelup.bibangamba.githubusers.R;
import com.levelup.bibangamba.githubusers.model.GithubUser;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class GithubUsersAdapter extends RecyclerView.Adapter<GithubUsersAdapter.GithubUserViewHolder> {
    private ClickListener mClickListener;
    private List<GithubUser> mGithubUsers;

    @Inject
    public GithubUsersAdapter(ClickListener listener) {
        this.mClickListener = listener;
        this.mGithubUsers = new ArrayList<>();
    }


    @NonNull
    @Override
    public GithubUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View githubUserListItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.github_user_grid_item, parent, false);
        return new GithubUserViewHolder(githubUserListItem);
    }

    @Override
    public void onBindViewHolder(@NonNull GithubUserViewHolder githubUserViewHolder, int position) {
        githubUserViewHolder.githubUsernameTextView.setText(mGithubUsers.get(position).getUsername());
        Glide
                .with(githubUserViewHolder.itemView.getContext())
                .load(mGithubUsers.get(position).getProfilePicture())
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .into(githubUserViewHolder.profilePictureImageView);

        githubUserViewHolder.itemView.setOnClickListener(v -> {
            mClickListener.onListItemClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return mGithubUsers.size();
    }

    public void setData(List<GithubUser> listOfGithubUsers) {
        this.mGithubUsers = listOfGithubUsers;
        notifyDataSetChanged();
    }

    public interface ClickListener {
        void onListItemClick(int position);
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
