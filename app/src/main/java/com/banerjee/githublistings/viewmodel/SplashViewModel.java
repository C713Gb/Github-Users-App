package com.banerjee.githublistings.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.banerjee.githublistings.model.User;
import com.banerjee.githublistings.repository.SplashRepository;

public class SplashViewModel extends ViewModel {

    private final SplashRepository splashRepository;
    public LiveData<User> isUserAuthenticatedLiveData;

    public SplashViewModel() {
        splashRepository = new SplashRepository();
    }

    public void checkIfUserIsAuthenticated() {
        isUserAuthenticatedLiveData = splashRepository.checkIfUserIsAuthenticatedInFirebase();
    }

}
