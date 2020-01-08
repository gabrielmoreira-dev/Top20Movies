package com.example.top20movies.ui.movielist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.top20movies.R;
import com.example.top20movies.data.model.Movie;
import com.example.top20movies.ui.moviedetails.MovieDetailsActivity;

import java.io.File;
import java.util.List;

public class MovieListActivity extends AppCompatActivity implements MovieListContract.MovieListView, MovieListAdapter.MovieClickListener {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private MovieListContract.MovieListPresenter presenter;
    private ProgressBar progressBar;

    //-------------------------- Initial Settings --------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        configureComponents();
        configurePresenter();

    }

    private void configureComponents(){
        this.progressBar = findViewById(R.id.loading_movie_list);
        configureRecyclerView();
    }

    private void configureRecyclerView(){
        this.recyclerView = findViewById(R.id.recycler_view);
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        this.adapter = new MovieListAdapter(this);
        this.recyclerView.setAdapter(adapter);
    }

    private void configurePresenter(){
        presenter = new MovieListPresenter(this, getCacheDir());
        presenter.getMovies();
    }

    //Break the view reference in presenter on view destroy
    @Override
    protected void onDestroy() {
        this.presenter.destroyView();
        super.onDestroy();
    }

    //----------------------------------------------------------------------------------------------

    //-------------------------- Output ------------------------------------------------------------

    @Override
    public void showMovies(List<Movie> movies){
        ((MovieListAdapter) adapter).setMovies(movies);
    }

    @Override
    public void showErrorMessage(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    @Override
    public void setLoadingBarVisibility(boolean setVisible) {
        if(setVisible){
            this.progressBar.setVisibility(View.VISIBLE);
        }
        else this.progressBar.setVisibility(View.INVISIBLE);
    }


    //----------------------------------------------------------------------------------------------

    //-------------------------- Event Handler -----------------------------------------------------

    @Override
    public void onMovieClick(Movie movie) {
        Intent intent = new Intent(getApplicationContext(), MovieDetailsActivity.class);
        intent.putExtra("id",movie.getId());
        startActivity(intent);
    }

    //----------------------------------------------------------------------------------------------



}
