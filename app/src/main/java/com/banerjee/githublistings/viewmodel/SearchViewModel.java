package com.banerjee.githublistings.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.banerjee.githublistings.network.model.UserItem;
import com.banerjee.githublistings.repository.UsersRepository;

import java.util.List;

public class SearchViewModel extends ViewModel {

    private final UsersRepository repository;
    public LiveData<List<UserItem>> listLiveData;

    public SearchViewModel()  {
        repository = new UsersRepository();
    }

    public void getUser(String userName) {
        listLiveData = repository.searchUser(userName);
    }

}
