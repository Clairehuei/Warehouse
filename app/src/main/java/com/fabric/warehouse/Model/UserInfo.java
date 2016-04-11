package com.fabric.warehouse.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserInfo {

    @SerializedName("userId")
    String account;
    @SerializedName("userName")
    String name;
    @SerializedName("userMail")
    String email;
    @SerializedName("userCellphone")
    String phone;
    @SerializedName("userType")
    String userType;

    public String getAccount() {
        return account;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getUserType() {
        return userType;
    }

}
