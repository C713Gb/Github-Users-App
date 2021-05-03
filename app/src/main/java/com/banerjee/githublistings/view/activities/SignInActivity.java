package com.banerjee.githublistings.view.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.banerjee.githublistings.R;
import com.banerjee.githublistings.utils.Constants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.OAuthProvider;

public class SignInActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private OAuthProvider.Builder provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        CardView githubBtn = findViewById(R.id.github_btn);
        firebaseAuth = FirebaseAuth.getInstance();

        provider = OAuthProvider.newBuilder("github.com");

        githubBtn.setOnClickListener(v -> signIn());
    }

    private void signIn() {

        Task<AuthResult> pendingResultTask = firebaseAuth.getPendingAuthResult();
        if (pendingResultTask != null) {
            // There's something already here! Finish the sign-in for your user.
            pendingResultTask
                    .addOnSuccessListener(
                            new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    // User is signed in.
                                    // IdP data available in
                                    // authResult.getAdditionalUserInfo().getProfile().
                                    // The OAuth access token can also be retrieved:
                                    // authResult.getCredential().getAccessToken().
                                    Log.d(Constants.TAG, "onSuccess: Task1");
                                    goToMainActivity();
                                }
                            })
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Handle failure.
                                    Log.d(Constants.TAG, "onFailure: Task1 "+e.getMessage());
                                    e.printStackTrace();
                                }
                            });
        } else {
            // There's no pending result so you need to start the sign-in flow.
            // See below.
            startSignInFlow();
        }


    }

    private void startSignInFlow() {
        firebaseAuth
                .startActivityForSignInWithProvider(/* activity= */ this, provider.build())
                .addOnSuccessListener(
                        new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                // User is signed in.
                                // IdP data available in
                                // authResult.getAdditionalUserInfo().getProfile().
                                // The OAuth access token can also be retrieved:
                                // authResult.getCredential().getAccessToken().
                                Log.d(Constants.TAG, "onSuccess: Task2");
                                goToMainActivity();
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Handle failure.
                                Log.d(Constants.TAG, "onFailure: Task2 "+e.getMessage());
                                e.printStackTrace();
                            }
                        });

    }

    private void goToMainActivity(){
        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
        startActivity(intent);
    }
}