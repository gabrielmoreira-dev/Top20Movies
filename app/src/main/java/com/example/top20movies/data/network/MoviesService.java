package com.example.top20movies.data.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MoviesService {

    public static JsonApi getService(){

        return setURL().create(JsonApi.class);

    }

    private static Retrofit setURL(){

        return new Retrofit.Builder()
                .baseUrl("https://desafio-mobile.nyc3.digitaloceanspaces.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }


}