package com.example.popularmovies.control;

import com.example.popularmovies.control.data.MovieInfoPage;
import com.example.popularmovies.model.MovieInfo;
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

public class PopularMoviesController implements Callback<MovieInfoPage> {

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
        Response<MovieInfoPage> response =
                mPopularMoviesAPI.getPopularMovies(mTheMovieDBKey).execute();
        //if (response.code() == 200) {
            MovieInfoPage movieInfoPage = response.body();
            return movieInfoPage.getMovieInfoList();
        //} else {
        //    throw new RuntimeException("Failed to load list of movies.");
        //}
    }

    public void getTopRatedMoviesAsync() {
        mPopularMoviesAPI.getTopRatedMovies(mTheMovieDBKey).enqueue(this);
    }

    public List<MovieInfo> getTopRatedMovies() throws IOException {
        MovieInfoPage movieInfoPage =
                mPopularMoviesAPI.getTopRatedMovies(mTheMovieDBKey).execute().body();
        return movieInfoPage.getMovieInfoList();

    }

    @Override
    public void onResponse(Call<MovieInfoPage> call, Response<MovieInfoPage> response) {
        if (mCallback != null) {
            if (response.code() == 200) {
                mCallback.movieListRetrieved(response.body().getMovieInfoList());
            } else
                mCallback.movieListRetrievalFailure();
        }
    }

    @Override
    public void onFailure(Call<MovieInfoPage> call, Throwable t) {
        if (mCallback != null)
            mCallback.movieListRetrievalFailure();
    }

    public interface ListMoviesResponseCallback {
        void movieListRetrieved(List<MovieInfo> movieList);

        void movieListRetrievalFailure();

        // methods added to allow testing
        boolean isFinished();

        boolean isFailure();

        List<MovieInfo> getMovieList();

    }
}
