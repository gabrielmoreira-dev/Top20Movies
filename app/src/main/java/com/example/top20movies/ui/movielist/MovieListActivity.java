package com.example.top20movies.ui.movielist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.top20movies.R;
import com.example.top20movies.data.model.Movie;
import com.example.top20movies.ui.moviedetails.MovieDetailsActivity;

import java.util.List;

public class MovieListActivity extends AppCompatActivity implements MovieListContract.MovieListView, MovieListAdapter.MovieClickListener {

    private RecyclerView recyclerView;
    private MovieListContract.MovieListPresenter presenter;

    //-------------------------- Initial Settings --------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        setRecyclerView();
        setPresenter();

    }

    private void setRecyclerView(){
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }

    private void setPresenter(){
        presenter = new MovieListPresenter(this);
        presenter.getMovies();
    }

    //Break the view reference in presenter on view destroy
    @Override
    protected void onDestroy() {
        presenter.destroyView();
        super.onDestroy();
    }

    //----------------------------------------------------------------------------------------------

    //-------------------------- Output ------------------------------------------------------------

    @Override
    public void showMovies(List<Movie> movies){
        RecyclerView.Adapter adapter = new MovieListAdapter(this);
        ((MovieListAdapter) adapter).setMovies(movies);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void showErrorMessage(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
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
