package com.example.project1.popularmoviesstage1.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by carvalhorr on 1/18/17.
 */

public class MovieInfo {

    @SerializedName("id")
    private String mMovieId;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("poster_path")
    private String mPosterPath;

    public MovieInfo() {
    }

    public MovieInfo(String movieId, String title, String posterImageURL) {
        this.mMovieId = movieId;
        this.mTitle = title;
        this.mPosterPath = posterImageURL;
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

    public String getmPosterPath() {
        return mPosterPath;
    }

    public void setmPosterPath(String mPosterPath) {
        this.mPosterPath = mPosterPath;
    }
}
