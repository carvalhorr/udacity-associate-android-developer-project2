package com.example.popularmovies.injection;

import android.content.Context;

import com.example.popularmovies.MovieDetailsActivity;
import com.example.popularmovies.MovieGridFragment;
import com.example.popularmovies.database.provider.FavoriteMoviesContentProvider;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by carvalhorr on 3/26/17.
 */

@Singleton
@Component(modules = {PopularMoviesInjectionModule.class})
public interface PopularMoviesApplicationComponent {

    Context context();

    void inject(MovieDetailsActivity activity);

    void inject(MovieGridFragment fragment);

    void inject(FavoriteMoviesContentProvider contentProvider);
}