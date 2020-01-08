package com.example.top20movies.data.network.movielist;

import com.example.top20movies.data.cache.CacheStorage;
import com.example.top20movies.data.model.Movie;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieListRepository {

    //-------------------------- API request -------------------------------------------------------

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

    //----------------------------------------------------------------------------------------------

    //-------------------------- Cache -------------------------------------------------------------

    public void storeMovieList(List<Movie> movieList, File folder){
        Gson gson = new Gson();
        String movieListJson = gson.toJson(movieList);

        File file = new File(folder, "movieList.txt");

        CacheStorage.writeData(file, movieListJson);
    }

    public List<Movie> loadMovieList(File folder){
        File file = new File(folder, "movieList.txt");

        String movieListJson = CacheStorage.readData(file);

        if(movieListJson == null){
            return null;
        }

        Gson gson = new Gson();
        Type movieListType = new TypeToken<List<Movie>>(){}.getType();
        List<Movie> movieList = gson.fromJson(movieListJson, movieListType);

        return movieList;
    }

    //----------------------------------------------------------------------------------------------

}
