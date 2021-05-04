package com.banerjee.githublistings.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@SuppressWarnings("serial")
public class RepoItem implements Serializable {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("stargazers_count")
    @Expose
    private String stargazersCount;
    @SerializedName("watchers_count")
    @Expose
    private String watchersCount;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("contributors_url")
    @Expose
    private String contributorsUrl;

    public RepoItem(String name, String stargazersCount, String watchersCount, String language,
                    String createdAt, String contributorsUrl) {
        this.name = name;
        this.stargazersCount = stargazersCount;
        this.watchersCount = watchersCount;
        this.language = language;
        this.createdAt = createdAt;
        this.contributorsUrl = contributorsUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStargazersCount() {
        return stargazersCount;
    }

    public void setStargazersCount(String stargazersCount) {
        this.stargazersCount = stargazersCount;
    }

    public String getWatchersCount() {
        return watchersCount;
    }

    public void setWatchersCount(String watchersCount) {
        this.watchersCount = watchersCount;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getContributorsUrl() {
        return contributorsUrl;
    }

    public void setContributorsUrl(String contributorsUrl) {
        this.contributorsUrl = contributorsUrl;
    }
}
