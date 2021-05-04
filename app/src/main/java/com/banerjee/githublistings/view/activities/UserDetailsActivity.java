package com.banerjee.githublistings.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.banerjee.githublistings.R;
import com.banerjee.githublistings.network.model.UserItem;
import com.banerjee.githublistings.utils.Constants;
import com.banerjee.githublistings.viewmodel.UserDetailsViewModel;
import com.banerjee.githublistings.viewmodel.UsersViewModel;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

public class UserDetailsActivity extends AppCompatActivity {

    private TextView followers, name;
    private ImageView profileImg;
    private UserDetailsViewModel userDetailsViewModel;
    private int followersNo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        followers = findViewById(R.id.followers_txt);
        name = findViewById(R.id.name);
        profileImg = findViewById(R.id.profile_img);

        userDetailsViewModel = new ViewModelProvider(this).get(UserDetailsViewModel.class);

        UserItem userItem = (UserItem)getIntent().getSerializableExtra("userItem");

        Gson gson = new Gson();
        String data = gson.toJson(userItem);

        Log.d(Constants.TAG, "onCreate: "+data);

        name.setText(userItem.getLogin());
        Glide.with(this)
                .load(userItem.getAvatarUrl())
                .circleCrop()
                .into(profileImg);

        getFollowers(userItem.getLogin());
    }

    private void getFollowers(String userName) {
        userDetailsViewModel.getAllFollowers(userName);
        userDetailsViewModel.listLiveData.observe(this, followersList -> {
            Log.d(Constants.TAG, "getFollowers: "+followersList.isEmpty());
            if (!followersList.isEmpty()) {
                followersNo = followersList.size();
                followers.setText(Integer.toString(followersNo));
            }
        });
    }
}