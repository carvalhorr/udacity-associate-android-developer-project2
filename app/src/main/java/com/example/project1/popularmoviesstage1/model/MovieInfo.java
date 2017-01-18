package com.example.project1.popularmoviesstage1.model;

/**
 * Created by carvalhorr on 1/18/17.
 */

public class MovieInfo {

    private String mMovieId;
    private String mTitle;
    private String mPosterImageURL;

    public MovieInfo(){}

    public MovieInfo(String movieId, String title, String posterImageURL) {
        this.mMovieId = movieId;
        this.mTitle = title;
        this.mPosterImageURL = posterImageURL;
    }

    public String getmMovieId() {
        return mMovieId;
    }

    public void setmMovieId(String mMovieId) {
        this.mMovieId = mMovieId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmPosterImageURL() {
        return mPosterImageURL;
    }

    public void setmPosterImageURL(String mPosterImageURL) {
        this.mPosterImageURL = mPosterImageURL;
    }
}
