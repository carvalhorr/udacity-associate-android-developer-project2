package com.example.popularmovies.task;

import android.content.Context;

import com.example.popularmovies.control.PopularMoviesController;
import com.example.popularmovies.model.MovieInfo;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by carvalhorr on 4/21/17.
 */

public class PopularMoviesListAsyncTask extends MovieListAsyncTask {

    private PopularMoviesController popularMobviesController;

    @Inject
    public PopularMoviesListAsyncTask(Context context, PopularMoviesController popularMoviesController) {
        super(context);
        this.popularMobviesController = popularMoviesController;
    }

    @Override
    public List<MovieInfo> loadInBackground() {
        try {
            return popularMobviesController.getPopularMovies();
        } catch (IOException e) {
            return null;
        }
    }

}
