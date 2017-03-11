package com.example.popularmovies.network.data;

import com.example.popularmovies.model.MovieReview;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Response returned from call to the get reviews endpoint from the movie db.
 *
 * Created by carvalhorr on 2/11/17.
 */

public class MovieReviewsPageResponse {

    // Movie id
    @SerializedName("id")
    private String id;

    // List of reviews
    @SerializedName("results")
    private List<MovieReview> reviews;

    // Retrieved page number
    @SerializedName("page")
    private Integer page;

    // Total number of reviews
    @SerializedName("total_results")
    private Integer totalResults;

    // Total number of pages
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
