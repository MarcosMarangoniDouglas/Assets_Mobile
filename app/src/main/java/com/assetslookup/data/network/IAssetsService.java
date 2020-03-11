package com.assetslookup.data.network;


import com.assetslookup.data.db.entities.Asset;
import com.assetslookup.data.db.entities.Assets;
import com.assetslookup.data.db.entities.SearchQuote;
import com.assetslookup.data.db.entities.Token;
import com.assetslookup.data.db.entities.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

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

    @GET("/api/users/read/:id")
    Call<User> getUser();

    @PUT("/api/users/update/:id")
    Call<Void> updateUser(@Body User user);

    // =========================

    // ASSETS ENDPOINTS
    @GET("/api/assets")
    Call<Assets> getAllAssets();

    @GET("/api/assets/{assetId}")
    Call<Asset> getAsset(@Path("assetId") String assetId);

    @GET("/api/assets/queryquote")
    Call<List<SearchQuote>> searchQuote(@Query("query") String quote);

    @POST("/api/assets")
    Call<Void> createAsset(@Body Asset asset);

    @DELETE("/api/assets")
    Call<Void> deleteAsset(@Body Asset asset);
    // =========================
}
