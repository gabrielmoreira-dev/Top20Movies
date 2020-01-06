package com.example.top20movies.ui.moviedetails;

import com.example.top20movies.data.model.MovieDetails;

public interface MovieDetailsContract {

    interface MovieDetailsView{

        void showMovieDetails(MovieDetails movieDetails);

        void showErrorMessage(String msg);

    }

    interface MovieDetailsPresenter{

        void setView(MovieDetailsView view);

        void getMovieDetails(int id);

        void destroyView();

    }

}
