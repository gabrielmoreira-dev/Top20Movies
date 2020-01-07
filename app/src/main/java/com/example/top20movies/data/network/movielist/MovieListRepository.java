package com.example.top20movies.data.network.movielist;

import com.example.top20movies.data.model.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieListRepository {

    public void getMovieList(Callback<List<Movie>> callback) {

        MovieListService service = buildRetrofit().create(MovieListService.class);
        Call<List<Movie>> call = service.getMovies();
        call.enqueue(callback);

    }

    private static Retrofit buildRetrofit() {

        return new Retrofit.Builder()
                .baseUrl("https://desafio-mobile.nyc3.digitaloceanspaces.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

}
