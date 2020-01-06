package com.example.top20movies.ui.moviedetails;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.top20movies.R;
import com.example.top20movies.data.model.MovieDetails;

public class MovieDetailsActivity extends AppCompatActivity implements MovieDetailsContract.MovieDetailsView {

    private int id;
    private TextView tv;
    private MovieDetails movieDetails;
    private MovieDetailsContract.MovieDetailsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Intent intent = getIntent();
        id = intent.getIntExtra("id",0);

        setTextField();
        setPresenter();
    }

    private void setPresenter(){
        presenter = new MovieDetailsPresenter(this);
        presenter.getMovieDetails(id);
    }

    public void setTextField(){
        tv = findViewById(R.id.movie_details_id);
    }

    @Override
    public void showMovieDetails(MovieDetails movieDetails) {
        tv.setText(movieDetails.getTitle());
    }

    @Override
    public void showErrorMessage(String msg) {
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT);
    }

    @Override
    protected void onDestroy() {
        presenter.destroyView();
        super.onDestroy();
    }
}
