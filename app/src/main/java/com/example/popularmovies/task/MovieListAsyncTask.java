package com.example.popularmovies.task;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.example.popularmovies.model.MovieInfo;

import java.io.IOException;
import java.util.List;

/**
 * Created by carvalhorr on 4/21/17.
 */

public abstract class MovieListAsyncTask extends AsyncTaskLoader<List<MovieInfo>> {

    public MovieListAsyncTask(Context context) {
        super(context);
    }


    @Override
    protected void onStartLoading() {
        forceLoad();
    }

}
