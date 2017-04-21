package com.example.popularmovies.task;

import android.content.Context;

import com.example.popularmovies.control.FavoriteController;
import com.example.popularmovies.control.PopularMoviesController;
import com.example.popularmovies.model.MovieInfo;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by carvalhorr on 4/21/17.
 */

public class FavoriteMoviesListAsynTask extends MovieListAsyncTask {

    private FavoriteController favoriteController;

    @Inject
    public FavoriteMoviesListAsynTask(Context context, FavoriteController favoriteController) {
        super(context);
        this.favoriteController = favoriteController;
    }

    @Override
    public List<MovieInfo> loadInBackground() {
        return favoriteController.getFavorites(getContext());
    }

}
