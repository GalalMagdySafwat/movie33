package com.example.ga.movieapp;


import java.util.List;

public class Movie {

    private String originalTitle;
    private String moviePoster;
    private String overView;
    private String userRating;
    private String releaseDate;
    private String movieID;
    private List<Videos> videos;
    private List<Reviews> reviews;


    public Movie() {};

    public Movie(String originalTitle, String moviePoster, String overView, String userRating, String releaseDate, String movieID, List<Videos> videos, List<Reviews> reviews) {
        this.originalTitle = originalTitle;
        this.moviePoster = moviePoster;
        this.overView = overView;
        this.userRating = userRating;
        this.releaseDate = releaseDate;
        this.movieID = movieID;
        this.videos = videos;
        this.reviews = reviews;
    }


    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public String getOverView() {
        return overView;
    }

    public String getUserRating() {
        return userRating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getMovieID() {
        return movieID;
    }

    public void setVideos(List<Videos> videos) {
        this.videos = videos;
    }

    public void setReviews(List<Reviews> reviews) {
        this.reviews = reviews;
    }

    public List<Videos> getVideos() {
        return videos;
    }

    public List<Reviews> getReviews() {
        return reviews;
    }
}
