package com.example.top20movies.data.network.moviedetails;

import com.example.top20movies.data.model.MovieDetails;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MovieDetailsService {

    @GET("movies/{id}")
    Call<MovieDetails> getMovieDetails(@Path("id") int id);

}
