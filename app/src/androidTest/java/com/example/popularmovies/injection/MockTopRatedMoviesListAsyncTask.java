package com.example.popularmovies.injection;

import android.content.Context;

import com.example.popularmovies.control.PopularMoviesController;
import com.example.popularmovies.model.MovieInfo;
import com.example.popularmovies.task.TopRatedMoviesListAsyncTask;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by carvalhorr on 4/23/17.
 */


public class MockTopRatedMoviesListAsyncTask extends TopRatedMoviesListAsyncTask {

    public MockTopRatedMoviesListAsyncTask(Context context, PopularMoviesController popularMoviesController) {
        super(context, popularMoviesController);
    }

    @Override
    public List<MovieInfo> loadInBackground() {
        return createMockTopRatedMoviesList();
    }

    private List<MovieInfo> createMockTopRatedMoviesList() {
        List<MovieInfo> movies = new ArrayList<MovieInfo>();
        MovieInfo mi = new MovieInfo();
        mi.setMovieId("1");
        mi.setTitle("A top rated movie title");
        mi.setPosterPath("path.png");
        mi.setBackdropPath("backdrop.png");
        mi.setPlot("Blah");
        mi.setReleaseDate(new Date());
        mi.setVoteAverage(10d);
        movies.add(mi);
        return movies;
    }

}
