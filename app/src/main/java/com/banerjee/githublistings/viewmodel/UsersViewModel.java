package com.banerjee.githublistings.viewmodel;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.banerjee.githublistings.network.model.UserItem;
import com.banerjee.githublistings.repository.UsersRepository;
import com.banerjee.githublistings.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class UsersViewModel extends ViewModel {

    private final UsersRepository repository;
    private LiveData<List<UserItem>> listLiveData;
    public MutableLiveData<List<UserItem>> listMutableLiveData;
    private int pageNo;
    private List<UserItem> list = new ArrayList<>();

    public UsersViewModel() {
        repository = new UsersRepository();
        pageNo = 0;
    }

    public void getAllUsers(){
        String language = "language:python";
        pageNo++;
        listLiveData = repository.getUsers(language, pageNo);
        listMutableLiveData = repository.getUsers(language, pageNo);

        listLiveData.observeForever(usersList -> {
            if (list.isEmpty()) {
                list = usersList;
            } else {
                list.addAll(usersList);
            }
            listMutableLiveData.setValue(list);
        });

    }

}
