package com.assetslookup.data.network;


import com.assetslookup.data.db.entities.Asset;
import com.assetslookup.data.db.entities.Assets;
import com.assetslookup.data.db.entities.Token;
import com.assetslookup.data.db.entities.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
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

    // ASSETS ENDPOINTS
    @GET("/api/assets")
    Call<Assets> getAllAssets();

    @GET("/api/asset/{assetId}")
    Call<Asset> getAsset(@Path("assetId") String assetId);
    // =========================
}
