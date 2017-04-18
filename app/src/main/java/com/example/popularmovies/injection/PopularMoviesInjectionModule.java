package com.example.popularmovies.injection;

import com.example.popularmovies.MovieGridFragment;
import com.example.popularmovies.control.FavoriteController;
import com.example.popularmovies.control.PopularMoviesController;
import com.example.popularmovies.injection.annotation.MovieDbApiKey;
import com.example.popularmovies.network.PopularMoviesAPI;
import com.example.popularmovies.task.MovieInfoTasks;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by carvalhorr on 3/26/17.
 */
@Module
public class PopularMoviesInjectionModule {

    @Provides
    @Singleton
    public PopularMoviesController providesMovieController(PopularMoviesAPI api, @MovieDbApiKey String movieDbApi) {
        return new PopularMoviesController(api, movieDbApi);
    }

    @Provides
    @Singleton
    public FavoriteController provideFavoriteController() {
        return new FavoriteController();
    }

    @Provides
    @Singleton
    public MovieInfoTasks provideMovieInfoTasks(PopularMoviesController controller) {
        return new MovieInfoTasks(controller);
    }

    @Provides
    @Singleton
    public PopularMoviesAPI providePopularMoviesApi() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PopularMoviesAPI.MOVIE_DB_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(PopularMoviesAPI.class);
    }

    @Provides
    @Singleton
    @MovieDbApiKey
    public String provideMovieDbApiKey() {
        return MovieGridFragment.MOVIE_DB_API_KEY;
    }

}
