package com.example.top20movies.ui.moviedetails;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.top20movies.R;
import com.example.top20movies.data.model.MovieDetails;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity implements MovieDetailsContract.MovieDetailsView {

    private int id;
    private TextView title;
    private TextView year;
    private TextView genres;
    private TextView overview;
    private ImageView backdrop;
    private ProgressBar imageProgressBar;
    private ProgressBar screenProgressBar;
    private MovieDetailsContract.MovieDetailsPresenter presenter;

    //-------------------------- Initial settings --------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        setId();
        configureComponents();
        configurePresenter();
    }

    private void setId(){
        this.id = getIntent().getIntExtra("id",0);
    }

    private void configureComponents(){
        this.title = findViewById(R.id.movie_details_title);
        this.year = findViewById(R.id.movie_details_year);
        this.genres = findViewById(R.id.movie_details_genres);
        this.overview = findViewById(R.id.movie_details_overview);
        this.backdrop = findViewById(R.id.image_movie_details);
        this.imageProgressBar = findViewById(R.id.loading_backdrop);
        this.screenProgressBar = findViewById(R.id.loading_movie_details);
    }


    private void configurePresenter(){
        this.presenter = new MovieDetailsPresenter(this, getCacheDir());
        this.presenter.getMovieDetails(id);
    }

    @Override
    protected void onDestroy() {
        this.presenter.destroyView();
        super.onDestroy();
    }

    //----------------------------------------------------------------------------------------------


    //-------------------------- Output ------------------------------------------------------------

    @Override
    public void showMovieDetails(MovieDetails movieDetails) {

        //Show movie info
        this.title.setText(movieDetails.getTitle());
        this.year.append(movieDetails.getRelease_date().substring(0,4));
        for(String genre : movieDetails.getGenres()){
            if(!genre.equals(movieDetails.getGenres().get(0))){
                this.genres.append(", ");
            }
            this.genres.append(genre);
        }
        this.overview.append(movieDetails.getOverview());

        //Show backdrop image
        setLoadingBarVisibility(true,2);
        Picasso.get().load(movieDetails.getBackdrop_url()).into(backdrop, new Callback() {
            @Override
            public void onSuccess() {
                setLoadingBarVisibility(false,2);
            }

            @Override
            public void onError(Exception e) {
                setLoadingBarVisibility(false,2);
            }
        });
    }

    @Override
    public void showErrorMessage(String msg) {
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT);
    }

    @Override
    public void setLoadingBarVisibility(boolean setVisible, int code) {
        switch (code){
            case 1: if(setVisible){
                    this.screenProgressBar.setVisibility(View.VISIBLE);
                }
                else this.screenProgressBar.setVisibility(View.INVISIBLE);
                break;
            case 2: if(setVisible){
                    this.imageProgressBar.setVisibility(View.VISIBLE);
                }
                else this.imageProgressBar.setVisibility(View.INVISIBLE);;
                break;
        }
    }

    //----------------------------------------------------------------------------------------------

}
