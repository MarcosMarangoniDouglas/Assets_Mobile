package com.warehousemanager.data.network;

import com.warehousemanager.data.db.entities.Product;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WarehouseService {

    public static Retrofit retrofit;

    public static Retrofit getInstance() {
        if(retrofit==null) {
            OkHttpClient client = new OkHttpClient.Builder().build();
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:8000")
                    //.baseUrl("http://140.161.89.19:8000")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }

}
