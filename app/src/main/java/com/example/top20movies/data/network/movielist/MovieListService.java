package com.example.top20movies.data.network.movielist;

import com.example.top20movies.data.model.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MovieListService {

    @GET("movies")
    Call<List<Movie>> getMovies();

}
