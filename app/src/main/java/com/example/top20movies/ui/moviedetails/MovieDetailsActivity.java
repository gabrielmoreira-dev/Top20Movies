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
    private MovieDetailsContract.MovieDetailsPresenter presenter;

    //-------------------------- Initial settings --------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        setId();
        setTextField();
        setPresenter();
    }

    private void setId(){
        Intent intent = getIntent();
        id = intent.getIntExtra("id",0);
    }

    private void setTextField(){
        tv = findViewById(R.id.movie_details_id);
    }

    private void setPresenter(){
        presenter = new MovieDetailsPresenter(this);
        presenter.getMovieDetails(id);
    }

    @Override
    protected void onDestroy() {
        presenter.destroyView();
        super.onDestroy();
    }

    //----------------------------------------------------------------------------------------------


    //-------------------------- Output ------------------------------------------------------------

    @Override
    public void showMovieDetails(MovieDetails movieDetails) {
        tv.setText(movieDetails.getTitle());
    }

    @Override
    public void showErrorMessage(String msg) {
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT);
    }

    //----------------------------------------------------------------------------------------------


}
