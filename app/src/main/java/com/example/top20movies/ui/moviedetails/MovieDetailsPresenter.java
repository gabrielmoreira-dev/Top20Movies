package com.example.top20movies.ui.moviedetails;

import android.widget.Toast;

import com.example.top20movies.data.model.MovieDetails;
import com.example.top20movies.data.network.MovieDetailsAPI;
import com.example.top20movies.data.network.MoviesService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsPresenter implements MovieDetailsContract.MovieDetailsPresenter {

    private MovieDetailsContract.MovieDetailsView view;

    public MovieDetailsPresenter(MovieDetailsContract.MovieDetailsView view) {
        this.view = view;
    }

    @Override
    public void setView(MovieDetailsContract.MovieDetailsView view) {

    }

    @Override
    public void getMovieDetails(int id) {
        MovieDetailsAPI api = MoviesService.getMovieDetails();

        Call<MovieDetails> call = api.getMovieDetails(id);
        call.enqueue(new Callback<MovieDetails>() {
            @Override
            public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                if(!response.isSuccessful()){
                    view.showErrorMessage("Erro: "+response.code());
                    return;
                }
                view.showMovieDetails(response.body());
            }

            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {
                view.showErrorMessage(t.getMessage());
            }
        });
    }

    @Override
    public void destroyView() {
        this.view = null;
    }

}