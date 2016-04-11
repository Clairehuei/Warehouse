package com.fabric.warehouse.Model;

import com.google.gson.annotations.SerializedName;

public class UserDataObjectResult {

    @SerializedName("dataObject")
    UserInfo userInfo;

    public UserInfo getUserInfo() {
        return userInfo;
    }
}
