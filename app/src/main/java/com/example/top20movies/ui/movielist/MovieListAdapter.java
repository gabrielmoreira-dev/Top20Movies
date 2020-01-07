package com.example.top20movies.ui.movielist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.top20movies.R;
import com.example.top20movies.data.model.Movie;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder> {

    private List<Movie> movies;
    private MovieClickListener movieClickListener;

    //-------------------------- Initial Settings --------------------------------------------------

    public MovieListAdapter(MovieClickListener movieClickListener) {
        this.movieClickListener = movieClickListener;
        this.movies = new ArrayList<>();
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    //----------------------------------------------------------------------------------------------

    //-------------------------- View Holder -------------------------------------------------------

    public static class MovieListViewHolder extends RecyclerView.ViewHolder{

        private RelativeLayout relativeLayout;
        private TextView title;
        private ImageView poster;
        private ProgressBar progressBar;

        public MovieListViewHolder(@NonNull View view) {
            super(view);
            this.title = view.findViewById(R.id.list_movie_title);
            this.poster = view.findViewById(R.id.image_movie_poster);
            this.relativeLayout = view.findViewById(R.id.item_list_layout);
            this.progressBar = view.findViewById(R.id.loading_poster);
        }

        public void setProgressBarVisibility(boolean setVisible){
            if(setVisible){
                this.progressBar.setVisibility(View.VISIBLE);
            }
            else this.progressBar.setVisibility(View.INVISIBLE);
        }

    }

    @NonNull
    @Override
    public MovieListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_movie,parent,false);
        return new MovieListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieListViewHolder holder, int position) {
        holder.title.setText(movies.get(position).getTitle());

        holder.setProgressBarVisibility(true);

        Picasso.get().load(movies.get(position).getPoster_url()).into(holder.poster, new Callback() {
            @Override
            public void onSuccess() {
                holder.setProgressBarVisibility(false);
            }

            @Override
            public void onError(Exception e) {
                holder.setProgressBarVisibility(false);
            }
        });

        //Set click listener
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                movieClickListener.onMovieClick(movies.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    //----------------------------------------------------------------------------------------------

    //-------------------------- Click Listener ----------------------------------------------------

    public interface MovieClickListener{

        void onMovieClick(Movie movie);

    }

    //----------------------------------------------------------------------------------------------

}
