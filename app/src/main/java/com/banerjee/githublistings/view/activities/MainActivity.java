package com.banerjee.githublistings.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.banerjee.githublistings.R;
import com.banerjee.githublistings.network.ApiCallInterface;
import com.banerjee.githublistings.network.model.UserItem;
import com.banerjee.githublistings.utils.Constants;
import com.banerjee.githublistings.view.adapters.UserAdapter;
import com.banerjee.githublistings.viewmodel.UsersViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = Constants.TAG;
    private UserAdapter adapter;
    private List<UserItem> list;
    private ApiCallInterface apiCallInterface;
    private RecyclerView recyclerView;
    private UsersViewModel usersViewModel;
    private boolean isLoading = false, isScrolling = false;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        progressBar = findViewById(R.id.loading);
        list = new ArrayList<>();
        TextView searchUser = findViewById(R.id.search_txt);
        searchUser.setOnClickListener(v -> {
            goToSearch();
        });

        usersViewModel = new ViewModelProvider(this).get(UsersViewModel.class);

        usersViewModel.getAllUsers();
        usersViewModel.listMutableLiveData.observe(this, usersList -> {
            list = usersList;
            progressBar.setVisibility(View.GONE);
            adapter = new UserAdapter(list, this, new UserAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(UserItem item) {
                    Intent intent = new Intent(MainActivity.this, UserDetailsActivity.class);
                    intent.putExtra("userItem", item);
                    startActivity(intent);
                }
            });
            recyclerView.setAdapter(adapter);
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
                int visibleItemCount = linearLayoutManager.getChildCount();
                int totalItemCount = linearLayoutManager.getItemCount();

                boolean isNotLoadingAndNotLastPage = !isLoading;
                boolean isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount;
                boolean isNotAtBeginning = firstVisibleItemPosition >= 0;
                boolean shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning
                        && isScrolling;

                if(shouldPaginate) {
                    progressBar.setVisibility(View.VISIBLE);
                    getData();
                    isScrolling = false;
                    isLoading = true; // to only do till 2nd page
                }
            }
        });

    }

    private void goToSearch() {
        startActivity(new Intent(MainActivity.this, SearchActivity.class));
    }

    private void getData(){
        usersViewModel.getAllUsers();
        usersViewModel.listMutableLiveData.observe(this, usersList -> {
            progressBar.setVisibility(View.GONE);
            list.addAll(usersList);
            adapter.notifyDataSetChanged();
        });
    }

}