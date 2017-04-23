package com.example.popularmovies.task;

import android.content.Context;

import com.example.popularmovies.control.FavoriteController;
import com.example.popularmovies.control.PopularMoviesController;

/**
 * Created by carvalhorr on 4/21/17.
 */

public class MovieListAsyncTaskFactory {

    private Context context;

    private PopularMoviesController popularMoviesController;

    private FavoriteController favoriteController;

    public MovieListAsyncTaskFactory(Context context, PopularMoviesController popularMoviesController, FavoriteController favoriteController) {
        this.context = context;
        this.popularMoviesController = popularMoviesController;
        this.favoriteController = favoriteController;
    }

    // Constants for the different loaders
    public static final int POPULAR_MOVIE_LOADER = 1;
    public static final int TOP_RATED_MOVIE_LOADER = 2;
    public static final int FAVORITE_MOVIE_LOADER = 3;

    public MovieListAsyncTask getMovieListAsyncTask(int queryType) {
        MovieListAsyncTask movieListAsyncTask = null;
        switch (queryType) {
            case POPULAR_MOVIE_LOADER: {
                movieListAsyncTask = new PopularMoviesListAsyncTask(context, popularMoviesController);
                break;
            }
            case TOP_RATED_MOVIE_LOADER: {
                movieListAsyncTask = new TopRatedMoviesListAsyncTask(context, popularMoviesController);
                break;
            }
            case FAVORITE_MOVIE_LOADER: {
                movieListAsyncTask = new FavoriteMoviesListAsyncTask(context, favoriteController);
                break;
            }
        }
        return movieListAsyncTask;

    }
}
