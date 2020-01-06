package com.example.top20movies.data.network;

import com.example.top20movies.data.model.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MovieListAPI {

    @GET("movies")
    Call<List<Movie>> getMovies();

}
