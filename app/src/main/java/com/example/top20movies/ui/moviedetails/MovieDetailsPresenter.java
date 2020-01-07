package com.example.top20movies.ui.moviedetails;

import android.view.View;
import android.widget.ProgressBar;

import com.example.top20movies.data.model.MovieDetails;
import com.example.top20movies.data.network.MovieDetailsAPI;
import com.example.top20movies.data.network.MoviesService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsPresenter implements MovieDetailsContract.MovieDetailsPresenter {

    private MovieDetailsContract.MovieDetailsView view;
    private ProgressBar progressBar;

    //-------------------------- Initial settings --------------------------------------------------

    public MovieDetailsPresenter(MovieDetailsContract.MovieDetailsView view, ProgressBar progressBar) {
        this.view = view;
        this.progressBar = progressBar;
    }

    //----------------------------------------------------------------------------------------------

    //-------------------------- Manage view -------------------------------------------------------

    @Override
    public void setView(MovieDetailsContract.MovieDetailsView view) {
        this.view = view;
    }

    @Override
    public void destroyView() {
        this.view = null;
    }

    //----------------------------------------------------------------------------------------------

    //-------------------------- API Call ----------------------------------------------------------

    @Override
    public void getMovieDetails(int id) {
        MovieDetailsAPI api = MoviesService.getMovieDetails();

        setProgressBarVisibility(true);

        Call<MovieDetails> call = api.getMovieDetails(id);
        call.enqueue(new Callback<MovieDetails>() {
            @Override
            public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {

                setProgressBarVisibility(false);

                if(!response.isSuccessful()){
                    if(view != null)
                        view.showErrorMessage("Erro: "+response.code());
                    return;
                }

                if(view != null)
                    view.showMovieDetails(response.body());
            }

            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {

                setProgressBarVisibility(false);

                if(view != null)
                    view.showErrorMessage(t.getMessage());

            }
        });

    }

    //----------------------------------------------------------------------------------------------

    //-------------------------- Inform user that data is loading ----------------------------------

    private void setProgressBarVisibility(boolean setVisible){
        if(setVisible){
            progressBar.setVisibility(View.VISIBLE);
        }
        else progressBar.setVisibility(View.INVISIBLE);

    }

    //----------------------------------------------------------------------------------------------

}
