package com.banerjee.githublistings.network;

import com.banerjee.githublistings.network.model.RepoItem;
import com.banerjee.githublistings.network.model.UserItem;
import com.banerjee.githublistings.network.model.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiCallInterface {

    @GET(value = "/search/users")
    Call<UserResponse> getUsers(@Query("q") String language,
                                @Query("page") int pageNo);

    @GET(value = "/users/{user_name}")
    Call<UserItem> getUser(@Path(value = "user_name", encoded = true) String userName);

    @GET(value = "/users/{user_name}/followers")
    Call<List<UserItem>> getFollowers(@Path(value = "user_name", encoded = true) String userName);

    @GET(value = "/users/{user_name}/repos")
    Call<List<RepoItem>> getRepos(@Path(value = "user_name", encoded = true) String userName);

}
