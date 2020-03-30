package com.assetslookup.data.network;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AssetsService {

  //final static String IPADDRESS = "http://ec2-3-87-64-207.compute-1.amazonaws.com:8000"; // This IP address doesn't change
    final static String IPADDRESS = "http://10.0.2.2:4000"; // This IP address doesn't change


    public static Retrofit retrofit;

    private static String token;

    public static void setToken(String newToken) {
        token = newToken;
    }

    public static void clearToken() {
        token = "";
    }

    public static Retrofit getInstance() {

        ApiKeyInterceptor apiKeyInterceptor = new ApiKeyInterceptor(token);
        OkHttpClient client = new OkHttpClient
                .Builder()
                .addInterceptor(apiKeyInterceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(IPADDRESS)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        return retrofit;
    }
}
