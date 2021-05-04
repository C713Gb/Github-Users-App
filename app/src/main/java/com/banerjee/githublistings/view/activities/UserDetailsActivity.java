package com.banerjee.githublistings.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.banerjee.githublistings.R;
import com.banerjee.githublistings.network.model.RepoItem;
import com.banerjee.githublistings.network.model.UserItem;
import com.banerjee.githublistings.utils.Constants;
import com.banerjee.githublistings.viewmodel.UserDetailsViewModel;
import com.banerjee.githublistings.viewmodel.UsersViewModel;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class UserDetailsActivity extends AppCompatActivity {

    private TextView followers, name;
    private ImageView profileImg;
    private UserDetailsViewModel userDetailsViewModel;
    private int followersNo = 0, reposNo = 0;
    private List<RepoItem> repoItemList;
    private ListView repoList;
    private ArrayAdapter<String> arrayAdapter;
    private List<String> stringList;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        followers = findViewById(R.id.followers_txt);
        name = findViewById(R.id.name);
        profileImg = findViewById(R.id.profile_img);
        repoItemList = new ArrayList<>();
        stringList = new ArrayList<>();
        repoList = findViewById(R.id.list_repos);
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.repo_details_diaolog);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);

        TextView repoName = dialog.findViewById(R.id.repo_name_txt);
        TextView repoStars = dialog.findViewById(R.id.stars_txt);
        TextView repoWatchers = dialog.findViewById(R.id.watchers_txt);
        TextView repoLanguage = dialog.findViewById(R.id.language_txt);
        TextView repoCreated = dialog.findViewById(R.id.created_at_txt);

        repoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(Constants.TAG, "onItemClick: "+repoItemList.get(position).getName());
                RepoItem repoItem = repoItemList.get(position);
                dialog.show();
                repoName.setText(repoItem.getName());
                repoCreated.setText(repoItem.getCreatedAt());

                if (repoItem.getStargazersCount() == null)
                    repoStars.setText("0");
                else repoStars.setText(repoItem.getStargazersCount());
                if (repoItem.getWatchersCount() == null)
                    repoWatchers.setText("0");
                else repoWatchers.setText(repoItem.getWatchersCount());
                if (repoItem.getLanguage() == null)
                    repoLanguage.setText("None");
                else repoLanguage.setText(repoItem.getLanguage());
            }
        });

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
        getRepos(userItem.getLogin());
    }

    private void getRepos(String userName) {
        userDetailsViewModel.getAllRepos(userName);
        userDetailsViewModel.repoListLiveData.observe(this, list -> {
            if (!list.isEmpty()){
                reposNo = list.size();
                repoItemList = list;
                for (RepoItem item: repoItemList){
                    stringList.add(item.getName());
                }

                arrayAdapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, stringList);
                repoList.setAdapter(arrayAdapter);

            }
        });
    }

    private void getFollowers(String userName) {
        userDetailsViewModel.getAllFollowers(userName);
        userDetailsViewModel.listLiveData.observe(this, followersList -> {
            if (!followersList.isEmpty()) {
                followersNo = followersList.size();
                followers.setText(Integer.toString(followersNo));
            }
        });
    }
}