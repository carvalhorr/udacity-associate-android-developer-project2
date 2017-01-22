package com.example.project1.popularmoviesstage1;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project1.popularmoviesstage1.control.PopularMoviesController;
import com.example.project1.popularmoviesstage1.model.MovieInfo;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity
        extends AppCompatActivity
        implements PopularMoviesAdapter.MovieOnClickHandler,
        LoaderManager.LoaderCallbacks<List<MovieInfo>> {


    private static final String QUERY_TYPE_PARAM = "query_type";

    private static final String MOVIE_DB_API_KEY = "a803f4555ef3c766306871fe297ef16a";

    private static final int MOVIE_DB_LOADER = 1;

    private PopularMoviesAdapter mPopularMoviesAdapter;

    private RecyclerView mMovieGridRecyclerView;
    private TextView mErrorMessageView;
    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_grid);

        mMovieGridRecyclerView = (RecyclerView) findViewById(R.id.rv_movies_grid);
        setupMovieGridRecyclerView();

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        mErrorMessageView = (TextView) findViewById(R.id.tv_error_message);

        getSupportLoaderManager().initLoader(MOVIE_DB_LOADER, null, this);

        startMoviesLoader(QueryType.POPULAR);
    }

    private void setupMovieGridRecyclerView() {

        int spanCount = 2;
        MainActivity context = this;
        boolean reverseLayout = false;

        GridLayoutManager movieGridLayoutManager =
                new GridLayoutManager(context, spanCount, LinearLayoutManager.VERTICAL, reverseLayout);
        mMovieGridRecyclerView.setLayoutManager(movieGridLayoutManager);

        mPopularMoviesAdapter = new PopularMoviesAdapter(this);
        mMovieGridRecyclerView.setAdapter(mPopularMoviesAdapter);
    }



    @Override
    public void onClick(String movieId) {
        Toast.makeText(this, "Movie " + movieId + " clicked.", Toast.LENGTH_LONG).show();
    }

    private void startMoviesLoader(QueryType queryType) {

        Bundle queryBundle = new Bundle();
        queryBundle.putString(QUERY_TYPE_PARAM, queryType.toString());

        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<String> githubSearchLoader = loaderManager.getLoader(MOVIE_DB_LOADER);
        if (githubSearchLoader == null) {
            loaderManager.initLoader(MOVIE_DB_LOADER, queryBundle, this);
        } else {
            loaderManager.restartLoader(MOVIE_DB_LOADER, queryBundle, this);
        }

    }

    @Override
    public Loader<List<MovieInfo>> onCreateLoader(int id, final Bundle args) {

        return new AsyncTaskLoader<List<MovieInfo>>(this) {

            @Override
            protected void onStartLoading() {
                if (args == null) {
                    return;
                }
                mLoadingIndicator.setVisibility(View.VISIBLE);
                forceLoad();
            }

            @Override
            public List<MovieInfo> loadInBackground() {
                String queryType = args.getString(QUERY_TYPE_PARAM);
                PopularMoviesController controller = new PopularMoviesController(MOVIE_DB_API_KEY);

                List<MovieInfo> listMovies = null;
                try {
                    switch (queryType) {
                        case "POPULAR": {
                            listMovies = controller.getPopularMovies();
                        }
                        case "TOP_RATED": {
                            listMovies = controller.getTopRatedMovies();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return listMovies;
            }
        };

    }

    @Override
    public void onLoadFinished(Loader<List<MovieInfo>> loader, List<MovieInfo> data) {
        mPopularMoviesAdapter.setMovieInfoData(data);
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mMovieGridRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoaderReset(Loader<List<MovieInfo>> loader) {

    }
}
