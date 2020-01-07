package com.example.top20movies.ui.movielist;

import com.example.top20movies.data.model.Movie;
import com.example.top20movies.data.network.MovieListAPI;
import com.example.top20movies.data.network.MoviesService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListPresenter implements MovieListContract.MovieListPresenter {

    private MovieListContract.MovieListView view;

    //-------------------------- Initial settings --------------------------------------------------

    public MovieListPresenter(MovieListContract.MovieListView view) {
        this.view = view;
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

        if(view != null)
            view.setLoadingBarVisibility(true);

        Call<List<Movie>> call = api.getMovies();
        call.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {

                if(view != null) {
                    view.setLoadingBarVisibility(false);

                    if (!response.isSuccessful()) {
                        view.showErrorMessage("Erro: " + response.code());
                        return;
                    }

                    view.showMovies(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {

                if(view != null) {
                    view.setLoadingBarVisibility(false);
                    view.showErrorMessage(t.getMessage());
                }

            }
        });

    }

    //----------------------------------------------------------------------------------------------


}
