package com.example.popularmovies.model;

import com.google.gson.annotations.SerializedName;

/**
 * Information about each review avout a movie.
 *
 * Created by carvalhorr on 2/12/17.
 */

public class MovieReview {

    // Review id
    @SerializedName("id")
    private String id;

    // Review author
    @SerializedName("author")
    private String author;

    // The review text
    @SerializedName("content")
    private String content;

    // The review url
    @SerializedName("url")
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
