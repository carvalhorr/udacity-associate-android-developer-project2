package com.example.popularmovies.injection;

import com.example.popularmovies.MovieDetailsActivity;
import com.example.popularmovies.MovieGridFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by carvalhorr on 3/26/17.
 */

@Singleton
@Component(modules = {PopularMoviesInjectionModule.class})
public interface PopularMoviesApplicationComponent {

    void inject(MovieDetailsActivity activity);

    void inject(MovieGridFragment grafment);
}