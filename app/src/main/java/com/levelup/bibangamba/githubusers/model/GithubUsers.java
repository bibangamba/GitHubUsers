package com.levelup.bibangamba.githubusers.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class GithubUsers implements Parcelable {
    public static final Creator<GithubUsers> CREATOR = new Creator<GithubUsers>() {
        @Override
        public GithubUsers createFromParcel(Parcel in) {
            return new GithubUsers(in);
        }

        @Override
        public GithubUsers[] newArray(int size) {
            return new GithubUsers[size];
        }
    };
    @SerializedName("avatar_url")
    private String profilePicture;
    @SerializedName("login")
    private String username;
    @SerializedName("html_url")
    private String profileUrl;
    @SerializedName("followers")
    private String followers;
    @SerializedName("following")
    private String following;
    @SerializedName("public_repos")
    private String repos;
    @SerializedName("company")
    private String company;

    public GithubUsers() {
    }

    protected GithubUsers(Parcel in) {
        profilePicture = in.readString();
        username = in.readString();
        profileUrl = in.readString();
        followers = in.readString();
        following = in.readString();
        repos = in.readString();
        company = in.readString();
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getFollowers() {
        return followers;
    }

    public void setFollowers(String followers) {
        this.followers = followers;
    }

    public String getFollowing() {
        return following;
    }

    public void setFollowing(String following) {
        this.following = following;
    }

    public String getRepos() {
        return repos;
    }

    public void setRepos(String repos) {
        this.repos = repos;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(profilePicture);
        dest.writeString(username);
        dest.writeString(profileUrl);
        dest.writeString(followers);
        dest.writeString(following);
        dest.writeString(repos);
        dest.writeString(company);
    }
}
