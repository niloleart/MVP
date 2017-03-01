package com.example.noleart.mvp.api;

import android.content.Context;

import com.example.noleart.mvp.utils.Constants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by noleart on 3/02/17.
 */

public class serviceGenerator {
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(Constants.API_URL)
            .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass, Context mContext){
        OkHttpClient.Builder client = httpClient;
        Retrofit retrofit = builder.client(client.build()).build();
        return retrofit.create(serviceClass);
    }

}
