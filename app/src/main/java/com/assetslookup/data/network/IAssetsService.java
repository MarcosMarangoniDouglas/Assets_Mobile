package com.assetslookup.data.network;


import com.assetslookup.data.db.entities.Token;
import com.assetslookup.data.db.entities.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IAssetsService {

    // USERS ENDPOINTS
    @POST("/api/signup")
    Call<User> signUp(@Body User user);

    @POST("/api/login")
    Call<Token> login(@Body User user);

    @POST("/api/forgot_password")
    Call<Void> forgotPassword(@Body User user);

    @POST("/api/reset_password")
    Call<Void> resetPassword(@Body User user);
    // =========================


}
