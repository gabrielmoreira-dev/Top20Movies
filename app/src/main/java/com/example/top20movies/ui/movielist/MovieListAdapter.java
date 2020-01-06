package com.example.top20movies.ui.movielist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.top20movies.R;
import com.example.top20movies.data.model.Movie;

import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder> {

    private List<Movie> movies;
    private MovieClickListener movieClickListener;

    public MovieListAdapter(MovieClickListener movieClickListener) {
        this.movieClickListener = movieClickListener;
    }

    public static class MovieListViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout linearLayout;
        private TextView title;

        public MovieListViewHolder(@NonNull View view) {
            super(view);
            this.title = view.findViewById(R.id.list_movie_title);
            this.linearLayout = view.findViewById(R.id.item_list_layout);
        }

    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
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

        //Set on click listener
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
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

    public interface MovieClickListener{

        void onMovieClick(Movie movie);

    }

}
