package com.fabric.warehouse.Model;

import com.fabric.warehouse.Api.ApiResponseCode;
import com.google.gson.annotations.SerializedName;

/**
 * POJO class for modeling the API response.
 * @author Allen
 */
public class ApiResponse {

    @SerializedName("code")
    String code;

    /**
     * Get the code of the API response.
     *
     * @return the response code
     */
    public ApiResponseCode apiResponseCode() {
        return ApiResponseCode.getState(code);
    }
}
