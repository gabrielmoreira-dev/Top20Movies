package com.example.top20movies.ui.movielist;

import com.example.top20movies.data.model.Movie;

import java.util.List;

public interface MovieListContract {

    interface MovieListView{

        void showMovies(List<Movie> movies);

        void showErrorMessage(String msg);

        void setLoadingBarVisibility(boolean setVisible);

    }

    interface MovieListPresenter{

        void setView(MovieListView view);

        void getMovies();

        void destroyView();

    }


}
