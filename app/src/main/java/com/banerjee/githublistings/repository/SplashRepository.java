package com.banerjee.githublistings.repository;

import androidx.lifecycle.MutableLiveData;

import com.banerjee.githublistings.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import static com.banerjee.githublistings.utils.Constants.USERS;

public class SplashRepository {
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private final FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
    private final CollectionReference usersRef = rootRef.collection(USERS);
    private final User user = new User();

    public MutableLiveData<User> checkIfUserIsAuthenticatedInFirebase() {
        MutableLiveData<User> isUserAuthenticateInFirebaseMutableLiveData = new MutableLiveData<>();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser == null) {
            user.isAuthenticated = false;
        } else {
            user.uid = firebaseUser.getUid();
            user.isAuthenticated = true;
        }
        isUserAuthenticateInFirebaseMutableLiveData.setValue(user);
        return isUserAuthenticateInFirebaseMutableLiveData;
    }

}
