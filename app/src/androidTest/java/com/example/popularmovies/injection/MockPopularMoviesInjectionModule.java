package com.example.popularmovies.injection;

import android.content.Context;

import com.example.popularmovies.control.FavoriteController;
import com.example.popularmovies.control.PopularMoviesController;
import com.example.popularmovies.injection.annotation.MovieDbApiKey;
import com.example.popularmovies.network.PopularMoviesAPI;
import com.example.popularmovies.task.FavoriteMoviesListAsyncTask;
import com.example.popularmovies.task.MovieListAsyncTaskFactory;
import com.example.popularmovies.task.PopularMoviesListAsyncTask;
import com.example.popularmovies.task.TopRatedMoviesListAsyncTask;

import java.io.IOException;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by carvalhorr on 4/21/17.
 */

public class MockPopularMoviesInjectionModule extends PopularMoviesInjectionModule {

    private FavoriteMoviesListAsyncTask favoriteMoviesMovieAsyncTask;

    public MockPopularMoviesInjectionModule(Context context) {
        super(context);
        favoriteMoviesMovieAsyncTask = new MockFavoriteMoviesListAsyncTask(context, null);
    }

    @Override
    public MovieListAsyncTaskFactory provideMovieListAsyncTaskFactory(PopularMoviesController popularMoviesController, FavoriteController favoriteController) {

        MovieListAsyncTaskFactory mockFactory = mock(MovieListAsyncTaskFactory.class);

        PopularMoviesListAsyncTask popularMoviesAsyncTask = new MockPopularMoviesListAsyncTask(context, null);
        when(mockFactory.getMovieListAsyncTask(1)).thenReturn(popularMoviesAsyncTask);

        TopRatedMoviesListAsyncTask topRatedMoviesAsynktask = new MockTopRatedMoviesListAsyncTask(context, null);
        when(mockFactory.getMovieListAsyncTask(2)).thenReturn(topRatedMoviesAsynktask);

        when(mockFactory.getMovieListAsyncTask(3)).thenReturn(favoriteMoviesMovieAsyncTask);

        return mockFactory;
    }


    @Override
    public FavoriteController provideFavoriteController() {
        FavoriteController favoriteController = mock(FavoriteController.class);
        when(favoriteController.isFavorite(any(Context.class), anyString())).thenReturn(true);
        return favoriteController;
    }

    @Override
    public PopularMoviesController providesMovieController(PopularMoviesAPI api, @MovieDbApiKey String movieDbApi) {
        PopularMoviesController popularMoviesController = mock(PopularMoviesController.class);
        try {
            when(popularMoviesController.getMovieInfo(any(String.class))).thenReturn(favoriteMoviesMovieAsyncTask.loadInBackground().get(0));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return popularMoviesController;
    }

}
