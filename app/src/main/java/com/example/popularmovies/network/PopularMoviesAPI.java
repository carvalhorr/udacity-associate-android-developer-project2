package com.example.popularmovies.network;

import com.example.popularmovies.network.data.MovieInfoPageResponse;
import com.example.popularmovies.network.data.MovieReviewsPageResponse;
import com.example.popularmovies.network.data.MovieVideosResponse;
import com.example.popularmovies.model.MovieInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Interface interface used by retrofit to make requests to the movie DB rest api.
 *
 * Created by carvalhorr on 1/21/17.
 */

public interface PopularMoviesAPI {

    // Base URL for the movie db REST API
    public static final String MOVIE_DB_BASE_URL = "http://api.themoviedb.org/";

    // Path within the movie db rest api to retrieve the popular movies
    public static final String POPULAR_MOVIES_PATH = "/3/movie/popular/";

    // Path within the movie db rest api to retrieve the top rated movies
    public static final String TOP_RATED_MOVIES_PATH = "/3/movie/top_rated/";

    // Path within the movie db rest api to retrieve the movie info for a movie
    public static final String MOVIE_INFO_PATH = "3/movie/{movieId}";

    // Path within the movie db rest api to retrieve the videos for a movie
    public static final String MOVIE_VIDEOS_PATH = "3/movie/{movieId}/videos";

    // Path withing the mobie db rest api to retrieve the revies for a movie
    public static final String MOVIE_REVIEWS_PATH = "3/movie/{movieId}/reviews";

    // Path within the movie db rest api to retrieve an image
    public static final String BASE_POSTER_PATH = "http://image.tmdb.org/t/p/";

    /**
     * Retrieve the movie info for a specified movie id
     * @param movieId
     * @param key
     * @return
     */
    @GET(MOVIE_INFO_PATH)
    public Call<MovieInfo> getMovieInfo(@Path("movieId") String movieId, @Query("api_key") String key);

    /**
     * Retrieve video list for a specified movie id
     * @param movieId
     * @param key
     * @return
     */
    @GET(MOVIE_VIDEOS_PATH)
    public Call<MovieVideosResponse> getMovieVideos(@Path("movieId") String movieId, @Query("api_key") String key);

    // Retrieve list of reviews for a specified movie id
    @GET(MOVIE_REVIEWS_PATH)
    public Call<MovieReviewsPageResponse> getMovieReviews(@Path("movieId") String movieId, @Query("api_key") String key);

    // Retrieve first page of list of popular movies
    @GET(POPULAR_MOVIES_PATH)
    public Call<MovieInfoPageResponse> getPopularMovies(@Query("api_key") String key);

    // Retrieve specified page of list of popular movies
    @GET(POPULAR_MOVIES_PATH)
    public Call<MovieInfoPageResponse> getPopularMovies(@Query("api_key") String key, @Query("page") Integer page);

    // Retrieve first page of top rated movied
    @GET(TOP_RATED_MOVIES_PATH)
    public Call<MovieInfoPageResponse> getTopRatedMovies(@Query("api_key") String key);

    // Retrieve specified page of list of top rated movies
    @GET(TOP_RATED_MOVIES_PATH)
    public Call<MovieInfoPageResponse>  getTopRatedMovies(@Query("api_key") String key, @Query("page") Integer page);

}
