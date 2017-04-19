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
    private Integer pageNumber;

    // List of movies
    @SerializedName("results")
    private List<MovieInfo> movieInfoList;

    // Total number of movies in the corresponding category (popular or top rated)
    @SerializedName("total_results")
    private Integer totalResults;

    // Total number of pages in the corresponding category (popular or top rated)
    @SerializedName("total_pages")
    private Integer totalPages;

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer mPageNumber) {
        this.pageNumber = mPageNumber;
    }

    public List<MovieInfo> getMovieInfoList() {
        return movieInfoList;
    }

    public void setMovieInfoList(List<MovieInfo> mMovieInfoList) {
        this.movieInfoList = mMovieInfoList;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer mTotalResults) {
        this.totalResults = mTotalResults;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer mTotalPages) {
        this.totalPages = mTotalPages;
    }
}
