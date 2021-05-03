package com.banerjee.githublistings.network.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserResponse {

    @SerializedName("items")
    @Expose
    private List<UserItem> items;

    public List<UserItem> getItems(){
        return items;
    }
    public void setItems(List<UserItem>items){
        this.items = items;
    }

}
