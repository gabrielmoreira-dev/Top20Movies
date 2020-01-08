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
    private TextView voteAverage;
    private TextView year;
    private TextView runtime;
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
        setUpComponents();
        setUpPresenter();
    }

    private void setId(){
        this.id = getIntent().getIntExtra("id",0);
    }

    private void setUpComponents(){
        this.title = findViewById(R.id.movie_details_title);
        this.voteAverage = findViewById(R.id.movie_details_vote_average);
        this.year = findViewById(R.id.movie_details_year);
        this.runtime = findViewById(R.id.movie_details_runtime);
        this.genres = findViewById(R.id.movie_details_genres);
        this.overview = findViewById(R.id.movie_details_overview);
        this.backdrop = findViewById(R.id.image_movie_details);
        this.imageProgressBar = findViewById(R.id.loading_backdrop);
        this.screenProgressBar = findViewById(R.id.loading_movie_details);
    }


    private void setUpPresenter(){
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
        if(movieDetails.getTitle() != null && movieDetails.getTitle() != "")
            this.title.setText(movieDetails.getTitle());
        else this.title.setText(R.string.unknowedTitle);

        this.voteAverage.setText(String.valueOf(movieDetails.getVoteAverage()));
        this.voteAverage.setBackgroundResource(R.drawable.vote_average_border);

        if(movieDetails.getReleaseDate() != null && movieDetails.getReleaseDate() != "") {
            this.year.append(movieDetails.getReleaseDate().substring(0, 4));
            this.year.append(",");
        }
        else this.year.setText(R.string.unknowedYear);

        if(movieDetails.getRuntime() != 0)
            this.runtime.setText(movieDetails.getRuntime() + " " + R.string.min);
        else this.runtime.setText(R.string.unknowedRuntime);

        if(movieDetails.getGenres().size() != 0) {
            for (String genre : movieDetails.getGenres()) {
                if (!genre.equals(movieDetails.getGenres().get(0))) {
                    this.genres.append(", ");
                }
                this.genres.append(genre);
            }
        }
        else this.genres.setText(R.string.unknowedGenre);

        if(movieDetails.getOverview() != null && movieDetails.getOverview() != "")
            this.overview.setText(movieDetails.getOverview());
        else this.overview.setText(R.string.noOverview);

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
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
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
