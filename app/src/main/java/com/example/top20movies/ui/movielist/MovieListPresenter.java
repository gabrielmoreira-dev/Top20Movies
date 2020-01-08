package com.example.top20movies.ui.movielist;

import com.example.top20movies.R;
import com.example.top20movies.data.model.Movie;
import com.example.top20movies.data.network.movielist.MovieListRepository;
import com.example.top20movies.ui.moviedetails.MovieDetailsActivity;

import java.io.File;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListPresenter implements MovieListContract.MovieListPresenter {

    private MovieListContract.MovieListView view;
    private MovieListRepository repository;
    private final File folder;

    //-------------------------- Initial settings --------------------------------------------------

    public MovieListPresenter(MovieListContract.MovieListView view) {
        this.view = view;
        this.repository = new MovieListRepository();
        this.folder = MovieListActivity.getAppContext().getCacheDir();
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
                        view.showErrorMessage(
                                String.format(
                                    MovieListActivity.getAppContext().getResources().getString(R.string.error),
                                        response.code(),
                                        response.message()
                                )
                        );
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
                    view.showErrorMessage(
                            MovieListActivity.getAppContext().getResources().getString(
                                    R.string.error_connection
                            )
                    );
                    List<Movie> movieListCache = repository.loadMovieList(folder);
                    if(movieListCache != null)
                        view.showMovies(movieListCache);
                }
            }
        });

    }

    //----------------------------------------------------------------------------------------------


}
