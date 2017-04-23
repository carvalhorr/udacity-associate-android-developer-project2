package com.example.popularmovies.injection;

import android.content.Context;

import com.example.popularmovies.control.FavoriteController;
import com.example.popularmovies.model.MovieInfo;
import com.example.popularmovies.task.FavoriteMoviesListAsyncTask;

import java.util.List;

/**
 * Created by carvalhorr on 4/23/17.
 */
public class MockFavoriteMoviesListAsyncTask extends FavoriteMoviesListAsyncTask {

    public MockFavoriteMoviesListAsyncTask(Context context, FavoriteController favoriteController) {
        super(context, favoriteController);
    }

    @Override
    public List<MovieInfo> loadInBackground() {
        return createMockFavoriteMoviesList();
    }

    private List<MovieInfo> createMockFavoriteMoviesList() {
        return  null;
    }

}

