package com.example.popularmovies.control;

import com.example.popularmovies.injection.annotation.MovieDbApiKey;
import com.example.popularmovies.network.data.MovieInfoPageResponse;
import com.example.popularmovies.network.data.MovieReviewsPageResponse;
import com.example.popularmovies.network.data.MovieVideosResponse;
import com.example.popularmovies.model.MovieInfo;
import com.example.popularmovies.model.MovieReview;
import com.example.popularmovies.model.MovieVideo;
import com.example.popularmovies.network.PopularMoviesAPI;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by carvalhorr on 1/21/17.
 */

public class PopularMoviesController {

    private String theMovieDBKey;
    private PopularMoviesAPI popularMoviesAPI = null;

    @Inject
    public PopularMoviesController(PopularMoviesAPI popularMoviesAPI, @MovieDbApiKey String theMovieDBKey) {
        this.theMovieDBKey = theMovieDBKey;
        this.popularMoviesAPI = popularMoviesAPI;
    }

    public List<MovieInfo> getPopularMovies() throws IOException {
        Response<MovieInfoPageResponse> response =
                popularMoviesAPI.getPopularMovies(theMovieDBKey).execute();
        MovieInfoPageResponse movieInfoPageResponse = response.body();
        return movieInfoPageResponse.getMovieInfoList();
    }

    public MovieInfo getMovieInfo(String movieId) throws IOException {
        Call call = popularMoviesAPI.getMovieInfo(movieId, theMovieDBKey);
        Response<MovieInfo> r = call.execute();
        return r.body();
    }

    public List<MovieVideo> getVideosForMovie(String movieId) throws IOException {
        Call call = popularMoviesAPI.getMovieVideos(movieId, theMovieDBKey);
        Response<MovieVideosResponse> r = call.execute();
        MovieVideosResponse body = r.body();
        if (body == null) {
            return null;
        } else {
            return body.getVideos();
        }
    }

    public List<MovieReview> getReviewsForMovie(String movieId) throws IOException {
        Call call = popularMoviesAPI.getMovieReviews(movieId, theMovieDBKey);
        Response<MovieReviewsPageResponse> r = call.execute();
        MovieReviewsPageResponse body = r.body();
        if (body == null) {
            return null;
        } else {
            return body.getReviews();
        }
    }

    public List<MovieInfo> getTopRatedMovies() throws IOException {
        MovieInfoPageResponse movieInfoPageResponse =
                popularMoviesAPI.getTopRatedMovies(theMovieDBKey).execute().body();
        return movieInfoPageResponse.getMovieInfoList();

    }

}
