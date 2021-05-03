package com.banerjee.githublistings.network;

import com.banerjee.githublistings.network.model.UserResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiCallInterface {

    @GET(value = "/search/users")
    Call<UserResponse> getUsers(@Query("q") String language,
                                @Query("page") int pageNo);

}
