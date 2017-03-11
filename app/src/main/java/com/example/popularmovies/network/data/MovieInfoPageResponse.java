package com.example.popularmovies.network.data;

import com.example.popularmovies.model.MovieInfo;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Response returned from the call to get popular movies or top rated movies endpoints
 * from the movie db.
 *
 * Created by carvalhorr on 1/21/17.
 */

public class MovieInfoPageResponse {

    // Number of the page returned
    @SerializedName("page")
    private Integer mPageNumber;

    // List of movies
    @SerializedName("results")
    private List<MovieInfo> mMovieInfoList;

    // Total number of movies in the corresponding category (popular or top rated)
    @SerializedName("total_results")
    private Integer mTotalResults;

    // Total number of pages in the corresponding category (popular or top rated)
    @SerializedName("total_pages")
    private Integer mTotalPages;

    public Integer getPageNumber() {
        return mPageNumber;
    }

    public void setPageNumber(Integer mPageNumber) {
        this.mPageNumber = mPageNumber;
    }

    public List<MovieInfo> getMovieInfoList() {
        return mMovieInfoList;
    }

    public void setMovieInfoList(List<MovieInfo> mMovieInfoList) {
        this.mMovieInfoList = mMovieInfoList;
    }

    public Integer getTotalResults() {
        return mTotalResults;
    }

    public void setTotalResults(Integer mTotalResults) {
        this.mTotalResults = mTotalResults;
    }

    public Integer getTotalPages() {
        return mTotalPages;
    }

    public void setTotalPages(Integer mTotalPages) {
        this.mTotalPages = mTotalPages;
    }
}
