package com.example.top20movies.data.model;

import java.util.List;

public class Movie {

    private int id;
    private transient double vote_average;
    private String title;
    private String poster_url;
    private transient List<String> genres;
    private transient String release_date;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster_url() {
        return poster_url;
    }

}