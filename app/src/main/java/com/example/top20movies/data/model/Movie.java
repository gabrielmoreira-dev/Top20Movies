package com.example.top20movies.data.model;

import java.util.List;

public class Movie {

    private int id;
    private double vote_average;
    private String title;
    private String poster_url;
    private List<String> genres;
    private String release_date;

    public int getId() {
        return id;
    }

    public double getVote_average() {
        return vote_average;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster_url() {
        return poster_url;
    }

    public List<String> getGenres() {
        return genres;
    }

    public String getRelease_date() {
        return release_date;
    }
}