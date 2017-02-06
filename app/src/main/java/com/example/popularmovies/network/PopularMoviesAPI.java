package com.example.popularmovies.network;

import android.graphics.Movie;

import com.example.popularmovies.control.data.MovieInfoPage;
import com.example.popularmovies.model.MovieInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by carvalhorr on 1/21/17.
 */

public interface PopularMoviesAPI {
    public static final String MOVIE_DB_BASE_URL = "http://api.themoviedb.org/";

    public static final String POPULAR_MOVIES_PATH = "/3/movie/popular/";

    public static final String TOP_RATED_MOVIES_PATH = "/3/movie/top_rated/";

    public static final String MOVIE_INFO_PATH = "3/movie/{movieId}";

    public static final String BASE_POSTER_PATH = "http://image.tmdb.org/t/p/";

    @GET(MOVIE_INFO_PATH)
    public Call<MovieInfo> getMovieInfo(@Path("movieId") String movieId, @Query("api_key") String key);

    @GET(POPULAR_MOVIES_PATH)
    public Call<MovieInfoPage> getPopularMovies(@Query("api_key") String key);

    @GET(POPULAR_MOVIES_PATH)
    public Call<MovieInfoPage> getPopularMovies(@Query("api_key") String key, @Query("page") Integer page);

    @GET(TOP_RATED_MOVIES_PATH)
    public Call<MovieInfoPage> getTopRatedMovies(@Query("api_key") String key);

    @GET(TOP_RATED_MOVIES_PATH)
    public Call<MovieInfoPage>  getTopRatedMovies(@Query("api_key") String key, @Query("page") Integer page);
}
