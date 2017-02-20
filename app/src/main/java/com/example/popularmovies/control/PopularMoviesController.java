package com.example.popularmovies.control;

import com.example.popularmovies.network.data.MovieInfoPageResponse;
import com.example.popularmovies.network.data.MovieReviewsPageResponse;
import com.example.popularmovies.network.data.MovieVideosResponse;
import com.example.popularmovies.model.MovieInfo;
import com.example.popularmovies.model.MovieReview;
import com.example.popularmovies.model.MovieVideo;
import com.example.popularmovies.network.PopularMoviesAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by carvalhorr on 1/21/17.
 */

public class PopularMoviesController implements Callback<MovieInfoPageResponse> {

    private String mTheMovieDBKey;
    private PopularMoviesAPI mPopularMoviesAPI = null;
    private ListMoviesResponseCallback mCallback;

    public PopularMoviesController(String theMovieDBKey) {
        this(theMovieDBKey, null);
    }

    public PopularMoviesController(String theMovieDBKey, ListMoviesResponseCallback callback) {
        this.mTheMovieDBKey = theMovieDBKey;
        this.mCallback = callback;
        setupPopularMoviesAPI();
    }

    private void setupPopularMoviesAPI() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PopularMoviesAPI.MOVIE_DB_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        mPopularMoviesAPI = retrofit.create(PopularMoviesAPI.class);

    }

    public void getPopularMoviesAsync() {
        mPopularMoviesAPI.getPopularMovies(mTheMovieDBKey).enqueue(this);
    }

    public List<MovieInfo> getPopularMovies() throws IOException {
        Response<MovieInfoPageResponse> response =
                mPopularMoviesAPI.getPopularMovies(mTheMovieDBKey).execute();
        //if (response.code() == 200) {
        MovieInfoPageResponse movieInfoPageResponse = response.body();
        return movieInfoPageResponse.getMovieInfoList();
        //} else {
        //    throw new RuntimeException("Failed to load list of movies.");
        //}
    }

    public MovieInfo getMovieInfo(String movieId) throws IOException {
        Call call = mPopularMoviesAPI.getMovieInfo(movieId, mTheMovieDBKey);
        Response<MovieInfo> r = call.execute();
        return r.body();
    }

    public List<MovieVideo> getVideosForMovie(String movieId) throws IOException {
        Call call = mPopularMoviesAPI.getMovieVideos(movieId, mTheMovieDBKey);
        Response<MovieVideosResponse> r = call.execute();
        MovieVideosResponse body = r.body();
        if (body == null) {
            return null;
        } else {
            return body.getVideos();
        }
    }

    public List<MovieReview> getReviewsForMovie(String movieId) throws IOException {
        Call call = mPopularMoviesAPI.getMovieReviews(movieId, mTheMovieDBKey);
        Response<MovieReviewsPageResponse> r = call.execute();
        MovieReviewsPageResponse body = r.body();
        if (body == null) {
            return null;
        } else {
            return body.getReviews();
        }
    }

    public void getTopRatedMoviesAsync() {
        mPopularMoviesAPI.getTopRatedMovies(mTheMovieDBKey).enqueue(this);
    }

    public List<MovieInfo> getTopRatedMovies() throws IOException {
        MovieInfoPageResponse movieInfoPageResponse =
                mPopularMoviesAPI.getTopRatedMovies(mTheMovieDBKey).execute().body();
        return movieInfoPageResponse.getMovieInfoList();

    }

    @Override
    public void onResponse(Call<MovieInfoPageResponse> call, Response<MovieInfoPageResponse> response) {
        if (mCallback != null) {
            if (response.code() == 200) {
                mCallback.movieListRetrieved(response.body().getMovieInfoList());
            } else
                mCallback.movieListRetrievalFailure();
        }
    }

    @Override
    public void onFailure(Call<MovieInfoPageResponse> call, Throwable t) {
        if (mCallback != null)
            mCallback.movieListRetrievalFailure();
    }

    public interface ListMoviesResponseCallback {
        void movieListRetrieved(List<MovieInfo> movieList);

        void movieListRetrievalFailure();

        // methods added to allow testing async calls
        boolean isFinished();

        boolean isFailure();

        List<MovieInfo> getMovieList();

    }
}
