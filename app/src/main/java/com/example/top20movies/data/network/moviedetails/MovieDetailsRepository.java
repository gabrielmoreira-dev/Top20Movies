package com.example.top20movies.data.network.moviedetails;

import com.example.top20movies.data.model.MovieDetails;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieDetailsRepository {

    public void getMovieDetails(int id, Callback<MovieDetails> callback){

        MovieDetailsService service = buildRetrofit().create(MovieDetailsService.class);
        Call<MovieDetails> call = service.getMovieDetails(id);
        call.enqueue(callback);

    }

    private static Retrofit buildRetrofit(){
        return new Retrofit.Builder()
                .baseUrl("https://desafio-mobile.nyc3.digitaloceanspaces.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}
