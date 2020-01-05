package com.example.top20movies.ui.movielist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.top20movies.R;
import com.example.top20movies.data.model.Movie;

import java.util.List;

public class MovieListActivity extends AppCompatActivity implements MovieListContract.MovieListView {

    private TextView tv1;
    private MovieListContract.MovieListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        setTextField();
        setPresenter();

    }

    private void setTextField(){
        tv1 = findViewById(R.id.tv1);
    }

    private void setPresenter(){
        presenter = new MovieListPresenter(this);
        presenter.getMovies();
    }

    @Override
    public void showMovies(List<Movie> movies){
        for(Movie movie : movies){
            tv1.append(movie.getTitle()+"\n");
        }
    }


    @Override
    public void showErrorMessage(String msg){
        tv1.setText(msg);
    }

    //Break the view reference in presenter on view destroy
    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.destroyView();
    }
}
