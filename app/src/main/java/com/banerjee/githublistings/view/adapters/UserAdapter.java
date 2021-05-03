package com.banerjee.githublistings.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.banerjee.githublistings.R;
import com.banerjee.githublistings.network.model.UserItem;
import com.bumptech.glide.Glide;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    List<UserItem> items;
    Context context;

    public UserAdapter(List<UserItem> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView name, githubLink;
        private final ImageView imageView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_txt);
            githubLink = itemView.findViewById(R.id.url_txt);
            imageView = itemView.findViewById(R.id.profile_img);
        }

        public void bind(UserItem userItem) {

            name.setText(userItem.getLogin());
            githubLink.setText(userItem.getHtmlUrl());

            Glide.with(itemView.getContext())
                    .load(userItem.getAvatarUrl())
                    .circleCrop()
                    .into(imageView);

        }
    }
}
