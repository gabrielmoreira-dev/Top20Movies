package com.example.top20movies.ui.movielist;

import com.example.top20movies.data.model.Movie;
import com.example.top20movies.data.network.movielist.MovieListRepository;

import java.io.File;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListPresenter implements MovieListContract.MovieListPresenter {

    private MovieListContract.MovieListView view;
    private MovieListRepository repository;
    private File folder;

    //-------------------------- Initial settings --------------------------------------------------

    public MovieListPresenter(MovieListContract.MovieListView view, File folder) {
        this.view = view;
        this.repository = new MovieListRepository();
        this.folder = folder;
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

    //-------------------------- Service Call ------------------------------------------------------

    @Override
    public void getMovies() {

        if (view != null)
            this.view.setLoadingBarVisibility(true);

        this.repository.getMovieList(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                if (view != null) {
                    view.setLoadingBarVisibility(false);
                    if (!response.isSuccessful()) {
                        view.showErrorMessage("Erro: " + response.code());
                        List<Movie> movieListCache = repository.loadMovieList(folder);
                        if(movieListCache != null)
                            view.showMovies(movieListCache);
                        return;
                    }
                    view.showMovies(response.body());
                    repository.storeMovieList(response.body(), folder);
                }
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                if (view != null) {
                    view.setLoadingBarVisibility(false);
                    view.showErrorMessage(t.getMessage());
                    List<Movie> movieListCache = repository.loadMovieList(folder);
                    if(movieListCache != null)
                        view.showMovies(movieListCache);
                }
            }
        });

    }

    //----------------------------------------------------------------------------------------------


}
