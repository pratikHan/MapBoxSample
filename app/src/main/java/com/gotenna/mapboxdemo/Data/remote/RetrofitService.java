package com.gotenna.mapboxdemo.Data.remote;

import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {

    private static Retrofit retrofit;


    public static Retrofit getRetrofitInstance(String baseUrl){

        Gson gson = new Gson();

        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;


    }
}
