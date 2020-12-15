package com.example.boatrsvapp.service;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import kotlin.jvm.internal.Intrinsics;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public final class ServiceGenerator {
    private final String BASE_URL = "http://192.168.0.234:8080/"; // TODO do zmiany w przypadku zmiany adresu api
    private final HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    private final Retrofit.Builder retrofitBuilder;
    private Retrofit retrofit;
    private final okhttp3.OkHttpClient.Builder httpClient;

    public ServiceGenerator() {
        this.retrofitBuilder = (new Retrofit.Builder())
                .baseUrl(this.BASE_URL)
                .addCallAdapterFactory((CallAdapter.Factory) RxJava2CallAdapterFactory
                        .create()).client((new okhttp3.OkHttpClient.Builder())
                        .build()).addConverterFactory((retrofit2.Converter.Factory) GsonConverterFactory.create());
        this.retrofit = this.retrofitBuilder.build();
        this.httpClient = new okhttp3.OkHttpClient.Builder();
    }

    public final <S> S createService(Class<S> serviceClass) {
        return this.retrofit.create(serviceClass);
    }

    public final <S> S createServiceWithToken(Class<S> serviceClass, Context context) {
        final String token = getToken(context);
        this.logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        this.httpClient.addInterceptor(this.logging);
        if (!Intrinsics.areEqual(token, "")) {
            this.httpClient.interceptors().clear();
            this.httpClient.addInterceptor((chain -> {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder().addHeader("Authorization", "Bearer " + token).addHeader("Content-Type", "application/json");
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }));
            this.retrofitBuilder.client(this.httpClient.build());
            this.retrofit = this.retrofitBuilder.build();
        }

        return this.retrofit.create(serviceClass);
    }

    private String getToken(Context context) {
        if(context!=null) {
            SharedPreferences preferences = context.getSharedPreferences("myPrefs", Activity.MODE_PRIVATE);
            return preferences != null ? preferences.getString("token", "") : "";
        }else{
            Log.e("ServiceGenerator","Context is null !!!");
            return "";
        }
    }

}