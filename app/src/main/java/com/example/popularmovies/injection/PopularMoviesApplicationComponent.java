package com.example.popularmovies.injection;

import com.example.popularmovies.MovieDetailsActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by carvalhorr on 3/26/17.
 */

@Singleton
@Component(modules = {PopularMoviesInjectionModule.class})
public interface PopularMoviesApplicationComponent {

    void inject(MovieDetailsActivity activity);

}