package com.example.top20movies.ui.movielist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.top20movies.R;
import com.example.top20movies.data.model.Movie;

import java.util.List;

public class MovieListActivity extends AppCompatActivity implements MovieListContract.MovieListView {

    private RecyclerView recyclerView;
    private MovieListContract.MovieListPresenter presenter;

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

    @Override
    public void showMovies(List<Movie> movies){
        RecyclerView.Adapter adapter = new MovieListAdapter();
        ((MovieListAdapter) adapter).setMovies(movies);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void showErrorMessage(String msg){
        //
    }

    //Break the view reference in presenter on view destroy
    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.destroyView();
    }
}
