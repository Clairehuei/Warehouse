package com.fabric.warehouse.Model;

import com.fabric.warehouse.Api.ApiResponseCode;
import com.google.gson.annotations.SerializedName;

public class UserInfoResponse {

    @SerializedName("response")
    ApiResponse response;

    @SerializedName("result")
    UserDataObjectResult dataResult;

    public boolean hasError() {
        return response.apiResponseCode() != ApiResponseCode.Success;
    }

    public String getError() {
        return response.apiResponseCode().name();
    }

    public UserDataObjectResult getDataResult() {
        return dataResult;
    }
}
