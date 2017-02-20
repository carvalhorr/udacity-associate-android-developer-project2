package com.example.popularmovies.network.data;

import com.example.popularmovies.model.MovieVideo;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by carvalhorr on 2/11/17.
 */

public class MovieVideosResponse {
    @SerializedName("id")
    private String id;

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
