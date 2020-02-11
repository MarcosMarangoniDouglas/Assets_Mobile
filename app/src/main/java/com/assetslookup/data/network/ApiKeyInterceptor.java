package com.assetslookup.data.network;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ApiKeyInterceptor implements Interceptor {

    private String token;

    public ApiKeyInterceptor(String token) {
        if(token != null) this.token = token;
        this.token = "";
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request authenticatedRequest = request.newBuilder()
                .header("Authorization", token).build();
        return chain.proceed(authenticatedRequest);
    }
}
