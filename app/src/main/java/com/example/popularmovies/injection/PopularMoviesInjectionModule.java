package com.example.popularmovies.injection;

import com.example.popularmovies.MovieGridFragment;
import com.example.popularmovies.control.PopularMoviesController;
import com.example.popularmovies.task.MovieInfoTasks;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by carvalhorr on 3/26/17.
 */
@Module
public class PopularMoviesInjectionModule {

    @Provides
    @Singleton
    public PopularMoviesController providesMovieController() {
        return new PopularMoviesController(MovieGridFragment.MOVIE_DB_API_KEY);
    }

    @Provides
    @Singleton
    public MovieInfoTasks provideMovieInfoTasks() {
        return new MovieInfoTasks();
    }

}
