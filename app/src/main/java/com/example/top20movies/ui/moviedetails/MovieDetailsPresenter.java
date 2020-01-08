package com.example.top20movies.ui.moviedetails;

import com.example.top20movies.R;
import com.example.top20movies.data.model.MovieDetails;
import com.example.top20movies.data.network.moviedetails.MovieDetailsRepository;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsPresenter implements MovieDetailsContract.MovieDetailsPresenter {

    private MovieDetailsContract.MovieDetailsView view;
    private MovieDetailsRepository repository;
    private File folder;

    //-------------------------- Initial settings --------------------------------------------------

    public MovieDetailsPresenter(MovieDetailsContract.MovieDetailsView view) {

        this.view = view;
        this.repository = new MovieDetailsRepository();
        this.folder = MovieDetailsActivity.getAppContext().getCacheDir();

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

        if(view != null)
            this.view.setLoadingBarVisibility(true,1);

        this.repository.getMovieDetails(id, new Callback<MovieDetails>() {
            @Override
            public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {

                if(view != null) {
                    view.setLoadingBarVisibility(false, 1);
                    if (!response.isSuccessful()) {
                        view.showErrorMessage(
                                String.format(
                                        MovieDetailsActivity.getAppContext().getResources().getString(R.string.error),
                                        response.code(),
                                        response.message()
                                )
                        );
                        MovieDetails movieDetailsCache = repository.loadMovieDetails(id, folder);
                        if(movieDetailsCache != null)
                            view.showMovieDetails(movieDetailsCache);
                        return;
                    }
                    view.showMovieDetails(response.body());
                    repository.storeMovieDetails(response.body(), folder);
                }
            }

            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {

                if(view != null) {
                    view.setLoadingBarVisibility(false, 1);
                    view.showErrorMessage(
                            MovieDetailsActivity.getAppContext().getResources().getString(
                                    R.string.error_connection
                            )
                    );
                    MovieDetails movieDetailsCache = repository.loadMovieDetails(id, folder);
                    if(movieDetailsCache != null)
                        view.showMovieDetails(movieDetailsCache);
                }

            }
        });

    }

    //----------------------------------------------------------------------------------------------

}
