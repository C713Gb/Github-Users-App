package com.banerjee.githublistings.view.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.banerjee.githublistings.R;
import com.banerjee.githublistings.network.ApiCallInterface;
import com.banerjee.githublistings.network.model.UserItem;
import com.banerjee.githublistings.utils.Constants;
import com.banerjee.githublistings.view.adapters.UserAdapter;
import com.banerjee.githublistings.viewmodel.SearchViewModel;
import com.banerjee.githublistings.viewmodel.UsersViewModel;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = Constants.TAG;
    private UserAdapter adapter;
    private List<UserItem> list;
    private ApiCallInterface apiCallInterface;
    private RecyclerView recyclerView;
    private boolean isLoading = false, isScrolling = false;
    private ProgressBar progressBar;
    private SearchViewModel searchViewModel;
    private TextView noUsersTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        progressBar = findViewById(R.id.loading);
        list = new ArrayList<>();
        noUsersTxt = findViewById(R.id.no_users_txt);

        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);

        Button searchBtn = findViewById(R.id.search_btn);
        EditText searchTxt = findViewById(R.id.user_txt);

        searchBtn.setOnClickListener(v -> {
            getData(searchTxt.getText().toString().trim());
            searchTxt.setText(null);
            searchTxt.clearFocus();
        });
    }

    private void getData(String userName) {
        if (!TextUtils.isEmpty(userName)) {
            progressBar.setVisibility(View.VISIBLE);
            searchViewModel.getUser(userName);
            searchViewModel.listLiveData.observe(this, data -> {
                progressBar.setVisibility(View.GONE);
                if (!data.isEmpty()) {
                    list = data;
                    noUsersTxt.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    adapter = new UserAdapter(list, this, new UserAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(UserItem item) {
                            Intent intent = new Intent(SearchActivity.this, UserDetailsActivity.class);
                            intent.putExtra("userItem", item);
                            startActivity(intent);
                        }
                    });
                    recyclerView.setAdapter(adapter);
                }
                else {
                    noUsersTxt.setVisibility(View.VISIBLE);
                }
            });
        }
        else {
            Toast.makeText(this, "Cannot be empty", Toast.LENGTH_SHORT).show();
        }
    }
}