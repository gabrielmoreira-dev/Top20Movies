package com.example.top20movies.ui.movielist;

import android.view.View;
import android.widget.ProgressBar;

import com.example.top20movies.data.model.Movie;
import com.example.top20movies.data.network.MovieListAPI;
import com.example.top20movies.data.network.MoviesService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListPresenter implements MovieListContract.MovieListPresenter {

    private MovieListContract.MovieListView view;
    private ProgressBar progressBar;

    //-------------------------- Initial settings --------------------------------------------------

    public MovieListPresenter(MovieListContract.MovieListView view, ProgressBar progressBar) {
        this.view = view;
        this.progressBar = progressBar;
    }

    //----------------------------------------------------------------------------------------------

    //-------------------------- Manage view -------------------------------------------------------

    @Override
    public void setView(MovieListContract.MovieListView view) {
        this.view = view;
    }

    @Override
    public void destroyView() {
        this.view = null;
    }

    //----------------------------------------------------------------------------------------------

    //-------------------------- API Call ----------------------------------------------------------

    @Override
    public void getMovies() {

        MovieListAPI api = MoviesService.getService();

        setProgressBarVisibility(true);

        Call<List<Movie>> call = api.getMovies();
        call.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {

                setProgressBarVisibility(false);

                if(!response.isSuccessful()){
                    if(view != null)
                        view.showErrorMessage("Erro: "+response.code());
                    return;
                }

                if(view != null)
                    view.showMovies(response.body());
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {

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
