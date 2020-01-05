package com.example.top20movies.ui.movielist;

import com.example.top20movies.data.model.Movie;
import com.example.top20movies.data.network.JsonApi;
import com.example.top20movies.data.network.MoviesService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListPresenter implements MovieListContract.MovieListPresenter {

    private MovieListContract.MovieListView view;

    public MovieListPresenter(MovieListContract.MovieListView view) {
        this.view = view;
    }

    @Override
    public void setView(MovieListContract.MovieListView view) {
        this.view = view;
    }

    @Override
    public void getMovies() {

        JsonApi api = MoviesService.getService();

        Call<List<Movie>> call = api.getMovies();
        call.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                if(!response.isSuccessful()){
                    view.showErrorMessage("Erro: "+response.code());
                    return;
                }
                List<Movie> movies = response.body();
                view.showMovies(movies);
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                view.showErrorMessage(t.getMessage());
            }
        });

    }

    @Override
    public void destroyView() {
        this.view = null;
    }

}