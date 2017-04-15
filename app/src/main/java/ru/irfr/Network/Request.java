package ru.irfr.Network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class Request {

    private static API instance;

    public static API getRetrofit() {
       if (instance == null){
           final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                   .readTimeout(10, TimeUnit.SECONDS)
                   .connectTimeout(10, TimeUnit.SECONDS)
                   .writeTimeout(10, TimeUnit.SECONDS)
                   .build();

           Retrofit retrofit = new Retrofit.Builder()
                   .baseUrl("http://mydelivery.96.lt/")
                   .client(okHttpClient)
                   .build();
           instance = retrofit.create(API.class);
       }
        return instance;
    }

}
