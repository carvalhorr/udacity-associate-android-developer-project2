package com.example.popularmovies.network.data;

import com.example.popularmovies.model.MovieReview;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by carvalhorr on 2/11/17.
 */

public class MovieReviewsPageResponse {
    @SerializedName("id")
    private String id;

    @SerializedName("results")
    private List<MovieReview> reviews;

    @SerializedName("page")
    private Integer page;

    @SerializedName("total_results")
    private Integer totalResults;

    @SerializedName("total_pages")
    private Integer totalPages;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<MovieReview> getReviews() {
        return reviews;
    }

    public void setReviews(List<MovieReview> reviews) {
        this.reviews = reviews;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }
}
