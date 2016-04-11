package com.fabric.warehouse.Api;

import com.fabric.warehouse.Model.Token;

import retrofit.Callback;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Api interface of login and get user token.
 *
 * @author Allen
 */
public interface GetAuthToken {

    /**
     *  API to login and get user token.
     *
     * @param version version of API. e.g: {@value com.fabric.warehouse.Config#API_VERSION}
     * @param AES_Key After the encrypted account and password
     * @param callback an {@link Callback<Token>} converted from the return <i>JSON</i>.
     */
    @POST("/APCCE/api/{version}/auth/getAuthToken")
    void get(
            @Path("version") String version,
            @Header("Authorization") String AES_Key,
            Callback<Token> callback);
}
