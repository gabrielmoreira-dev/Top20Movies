package com.example.top20movies.data.model;

import java.util.List;

public class MovieDetails{

    private int id;
    private double vote_average;
    private String title;
    private transient String poster_url;
    private List<String> genres;
    private String release_date;
    private transient boolean adult;
    private String backdrop_url;
    private transient Collection belongs_to_collection;
    private transient int budget;
    private transient String homepage;
    private transient String imdb_id;
    private transient String original_language;
    private transient String original_title;
    private String overview;
    private transient double popularity;
    private transient List<ProductionCompany> production_companies;
    private transient List<ProductionCountry> production_countries;
    private transient int revenue;
    private int runtime;
    private transient List<SpokenLanguage> spoken_languages;
    private transient String status;
    private transient String tagline;
    private transient boolean video;
    private transient int vote_count;

    public MovieDetails(){
        super();
    }

    public int getId() {
        return id;
    }

    public double getVote_average() {
        return vote_average;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getGenres() {
        return genres;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getBackdrop_url() {
        return backdrop_url;
    }

    public String getOverview() {
        return overview;
    }

    public int getRuntime() {
        return runtime;
    }
}
