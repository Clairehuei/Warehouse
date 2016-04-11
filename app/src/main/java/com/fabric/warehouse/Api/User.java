package com.fabric.warehouse.Api;

import com.fabric.warehouse.Model.UserInfoResponse;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import retrofit.http.Path;
import rx.Observable;

public interface User {

    /**
     * Get user account information
     *
     * @param version version of API. e.g: {@value com.fabric.warehouse.Config#API_VERSION}
     * @param tokenId token id of the logged in user
     * @param userId  user account
     * @return an {@link Observable<UserInfoResponse>} object which
     * contains the response message for latter parsing.
     */
    @FormUrlEncoded
    @POST("/APCCE/api/{version}/user/getUserInfo")
    Observable<UserInfoResponse> getUserInfo(
            @Path("version") String version,
            @Field("tokenId") String tokenId,
            @Field("queryUserId") String userId);

}
