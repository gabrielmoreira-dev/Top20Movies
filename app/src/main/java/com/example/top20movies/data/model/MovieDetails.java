package com.example.top20movies.data.model;

import java.util.List;

public class MovieDetails extends Movie {

    private boolean adult;
    private String backdrop_url;
    private Collection belongs_to_collection;
    private int budget;
    private String homepage;
    private String imdb_id;
    private String original_language;
    private String original_title;
    private String overview;
    private double popularity;
    private List<ProductionCompany> production_companies;
    private List<ProductionCountry> production_countries;
    private int revenue;
    private int runtime;
    private List<SpokenLanguage> spoken_languages;
    private String status;
    private String tagline;
    private boolean video;
    private int vote_count;

    public boolean isAdult() {
        return adult;
    }

    public String getBackdrop_url() {
        return backdrop_url;
    }

    public Collection getBelongs_to_collection() {
        return belongs_to_collection;
    }

    public int getBudget() {
        return budget;
    }

    public String getHomepage() {
        return homepage;
    }

    public String getImdb_id() {
        return imdb_id;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getOverview() {
        return overview;
    }

    public double getPopularity() {
        return popularity;
    }

    public List<ProductionCompany> getProduction_companies() {
        return production_companies;
    }

    public List<ProductionCountry> getProduction_countries() {
        return production_countries;
    }

    public int getRevenue() {
        return revenue;
    }

    public int getRuntime() {
        return runtime;
    }

    public List<SpokenLanguage> getSpoken_languages() {
        return spoken_languages;
    }

    public String getStatus() {
        return status;
    }

    public String getTagline() {
        return tagline;
    }

    public boolean isVideo() {
        return video;
    }

    public int getVote_count() {
        return vote_count;
    }
}
