package com.banerjee.githublistings.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.banerjee.githublistings.network.model.RepoItem;
import com.banerjee.githublistings.network.model.UserItem;
import com.banerjee.githublistings.repository.UsersRepository;

import java.util.List;

public class UserDetailsViewModel extends ViewModel {

    private final UsersRepository repository;
    public LiveData<List<UserItem>> listLiveData;
    public LiveData<List<RepoItem>> repoListLiveData;

    public UserDetailsViewModel() {
        repository = new UsersRepository();
    }

    public void getAllFollowers(String userName) {
        listLiveData = repository.getFollowers(userName);
    }

    public void getAllRepos(String userName) {
        repoListLiveData = repository.getRepos(userName);
    }

}
