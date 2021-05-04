package com.banerjee.githublistings.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.banerjee.githublistings.network.ApiCallInterface;
import com.banerjee.githublistings.network.RetrofitClientInstance;
import com.banerjee.githublistings.network.model.RepoItem;
import com.banerjee.githublistings.network.model.UserItem;
import com.banerjee.githublistings.network.model.UserResponse;
import com.banerjee.githublistings.utils.Constants;
import com.banerjee.githublistings.view.activities.MainActivity;
import com.banerjee.githublistings.view.adapters.UserAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersRepository {

    private final ApiCallInterface apiCallInterface;
    private final String TAG = Constants.TAG;
    private List<UserItem> list;
    private List<RepoItem> repoList;

    public UsersRepository() {
        apiCallInterface = RetrofitClientInstance
                .getRetrofitInstance()
                .create(ApiCallInterface.class);
    }

    public MutableLiveData<List<UserItem>> getUsers(String language, int pageNo) {
        MutableLiveData<List<UserItem>> listMutableLiveData = new MutableLiveData<>();
        list = new ArrayList<>();
        try {

            Call<UserResponse> call = apiCallInterface.getUsers(language, pageNo);
            call.enqueue(new Callback<UserResponse>() {
                @Override
                public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                    if (!response.isSuccessful()){
                        Log.d(TAG, "onResponse: Failed");
                        return;
                    }

                    Log.d(TAG, "onResponse: Success");

                    list = response.body().getItems();
                    listMutableLiveData.setValue(list);
                }

                @Override
                public void onFailure(Call<UserResponse> call, Throwable t) {
                    Log.d(TAG, "onFailure: "+t.getMessage());
                    t.printStackTrace();
                }
            });

        } catch (Exception e){
            Log.d(TAG, "loadJSON: "+e.getMessage());
            e.printStackTrace();
        }
        return listMutableLiveData;
    }

    public MutableLiveData<List<UserItem>> getFollowers(String userName){
        MutableLiveData<List<UserItem>> listMutableLiveData = new MutableLiveData<>();
        list = new ArrayList<>();
        try {

            Call<List<UserItem>> call = apiCallInterface.getFollowers(userName);
            call.enqueue(new Callback<List<UserItem>>() {
                @Override
                public void onResponse(Call<List<UserItem>> call, Response<List<UserItem>> response) {
                    if (!response.isSuccessful()){
                        Log.d(TAG, "onResponse: Failed");
                        return;
                    }

                    Log.d(TAG, "onResponse: Success");

                    list = response.body();
                    listMutableLiveData.setValue(list);
                }

                @Override
                public void onFailure(Call<List<UserItem>> call, Throwable t) {
                    Log.d(TAG, "onFailure: "+t.getMessage());
                    t.printStackTrace();
                }
            });

        } catch (Exception e){
            Log.d(TAG, "loadJSON: "+e.getMessage());
            e.printStackTrace();
        }
        return listMutableLiveData;
    }

    public MutableLiveData<List<RepoItem>> getRepos(String userName) {
        MutableLiveData<List<RepoItem>> listMutableLiveData = new MutableLiveData<>();
        repoList = new ArrayList<>();
        try {

            Call<List<RepoItem>> call = apiCallInterface.getRepos(userName);
            call.enqueue(new Callback<List<RepoItem>>() {
                @Override
                public void onResponse(Call<List<RepoItem>> call, Response<List<RepoItem>> response) {
                    if (!response.isSuccessful()){
                        Log.d(TAG, "onResponse: Failed");
                        return;
                    }

                    Log.d(TAG, "onResponse: Success");

                    repoList = response.body();
                    listMutableLiveData.setValue(repoList);
                }

                @Override
                public void onFailure(Call<List<RepoItem>> call, Throwable t) {
                    Log.d(TAG, "onFailure: "+t.getMessage());
                    t.printStackTrace();
                }
            });

        } catch (Exception e){
            Log.d(TAG, "loadJSON: "+e.getMessage());
            e.printStackTrace();
        }
        return listMutableLiveData;
    }
}
