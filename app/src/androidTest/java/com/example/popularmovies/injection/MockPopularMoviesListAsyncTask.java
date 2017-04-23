package com.example.popularmovies.injection;

import android.content.Context;

import com.example.popularmovies.control.PopularMoviesController;
import com.example.popularmovies.model.MovieInfo;
import com.example.popularmovies.task.PopularMoviesListAsyncTask;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by carvalhorr on 4/23/17.
 */

public class MockPopularMoviesListAsyncTask extends PopularMoviesListAsyncTask {

    public MockPopularMoviesListAsyncTask(Context context, PopularMoviesController popularMoviesController) {
        super(context, popularMoviesController);
    }

    @Override
    public List<MovieInfo> loadInBackground() {
        return createMockPopularMoviesList();
    }

    private List<MovieInfo> createMockPopularMoviesList() {
        List<MovieInfo> movies = new ArrayList<MovieInfo>();
        MovieInfo mi1 = new MovieInfo();
        mi1.setMovieId("1");
        mi1.setTitle("A popular movie title");
        mi1.setPosterPath("path.png");
        mi1.setBackdropPath("backdrop.png");
        mi1.setPlot("Blah");
        mi1.setReleaseDate(new Date());
        mi1.setVoteAverage(10d);
        movies.add(mi1);

        MovieInfo mi2 = new MovieInfo();
        mi2.setMovieId("1");
        mi2.setTitle("A popular movie title");
        mi2.setPosterPath("path.png");
        mi2.setBackdropPath("backdrop.png");
        mi2.setPlot("Blah");
        mi2.setReleaseDate(new Date());
        mi2.setVoteAverage(10d);
        movies.add(mi2);
        return movies;
    }


}
