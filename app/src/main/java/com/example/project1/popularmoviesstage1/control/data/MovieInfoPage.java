package com.example.project1.popularmoviesstage1.control.data;

import com.example.project1.popularmoviesstage1.model.MovieInfo;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by carvalhorr on 1/21/17.
 */

public class MovieInfoPage {

    @SerializedName("page")
    private Integer mPageNumber;

    @SerializedName("results")
    private List<MovieInfo> mMovieInfoList;

    @SerializedName("total_results")
    private Integer mTotalResults;

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
