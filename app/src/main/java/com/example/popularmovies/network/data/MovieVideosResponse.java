package com.example.popularmovies.network.data;

import com.example.popularmovies.model.MovieVideo;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Response returned from call to the get video list endpoint from the movie db.
 *
 * Created by carvalhorr on 2/11/17.
 */

public class MovieVideosResponse {

    // Movie id
    @SerializedName("id")
    private String id;

    // List of videos
    @SerializedName("results")
    private List<MovieVideo> videos;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<MovieVideo> getVideos() {
        return videos;
    }

    public void setVideos(List<MovieVideo> videos) {
        this.videos = videos;
    }
}
