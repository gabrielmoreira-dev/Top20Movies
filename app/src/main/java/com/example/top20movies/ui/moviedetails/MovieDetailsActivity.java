package com.example.top20movies.ui.moviedetails;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.top20movies.R;
import com.example.top20movies.data.model.MovieDetails;
import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity implements MovieDetailsContract.MovieDetailsView {

    private int id;
    private TextView title;
    private TextView year;
    private TextView genres;
    private TextView overview;
    private ImageView backdrop;
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
        Intent intent = getIntent();
        id = intent.getIntExtra("id",0);
    }

    private void configureComponents(){
        this.title = findViewById(R.id.movie_details_title);
        this.year = findViewById(R.id.movie_details_year);
        this.genres = findViewById(R.id.movie_details_genres);
        this.overview = findViewById(R.id.movie_details_overview);
        this.backdrop = findViewById(R.id.image_movie_details);
    }


    private void configurePresenter(){
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

        //Show title
        title.setText(movieDetails.getTitle());

        //Show release year
        year.append("Year: ");
        year.append(movieDetails.getRelease_date());

        //Show genres
        genres.append("Genre: ");
        for(String genre : movieDetails.getGenres()){
            if(!genre.equals(movieDetails.getGenres().get(0))){
                genres.append(", ");
            }
            genres.append(genre);
        }

        //Show overview
        overview.append("Overview: ");
        overview.append(movieDetails.getOverview());

        //Show backdrop image
        Picasso.get().load(movieDetails.getBackdrop_url()).into(backdrop);
    }

    @Override
    public void showErrorMessage(String msg) {
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT);
    }

    //----------------------------------------------------------------------------------------------


}
