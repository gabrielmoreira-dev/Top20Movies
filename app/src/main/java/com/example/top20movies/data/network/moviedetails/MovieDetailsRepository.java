package com.example.top20movies.data.network.moviedetails;

import com.example.top20movies.data.cache.CacheStorage;
import com.example.top20movies.data.model.MovieDetails;
import com.google.gson.Gson;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieDetailsRepository {

    //-------------------------- API request -------------------------------------------------------

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

    //----------------------------------------------------------------------------------------------

    //-------------------------- Cache -------------------------------------------------------------

    public void storeMovieDetails(MovieDetails movieDetails, File folder){
        Gson gson = new Gson();
        String movieDetailsJson = gson.toJson(movieDetails);

        File file = new File(folder, "movieDetails"+movieDetails.getId()+".txt");

        CacheStorage.writeData(file, movieDetailsJson);
    }

    public MovieDetails loadMovieDetails(int id, File folder){
        File file = new File(folder, "movieDetails"+id+".txt");

        String movieDetailsJson = CacheStorage.readData(file);

        if(movieDetailsJson == null){
            return null;
        }

        Gson gson = new Gson();
        MovieDetails movieDetails = gson.fromJson(movieDetailsJson, MovieDetails.class);

        return movieDetails;
    }

    //----------------------------------------------------------------------------------------------

}
