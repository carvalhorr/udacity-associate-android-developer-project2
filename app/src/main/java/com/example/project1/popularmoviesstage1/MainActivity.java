package com.example.project1.popularmoviesstage1;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.PersistableBundle;
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
import android.view.Menu;
import android.view.MenuItem;
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
    private TextView mCurrentlyDisplayingTextView;
    private ProgressBar mLoadingIndicator;

    private QueryType mSelectedQueryType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_grid);

        mMovieGridRecyclerView = (RecyclerView) findViewById(R.id.rv_movies_grid);
        setupMovieGridRecyclerView();

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        mErrorMessageView = (TextView) findViewById(R.id.tv_error_message);
        mCurrentlyDisplayingTextView = (TextView) findViewById(R.id.tv_currently_displaying);

        if (savedInstanceState != null) {
            System.out.println(" onCreate there is saved instance");
            if (savedInstanceState.getString(QUERY_TYPE_PARAM) != null) {
                String savedSelectedQueryType = savedInstanceState.getString(QUERY_TYPE_PARAM);
                switch (savedSelectedQueryType) {
                    case "POPULAR": {
                        mSelectedQueryType = QueryType.POPULAR;
                        break;
                    }
                    case "TOP_RATED": {
                        mSelectedQueryType = QueryType.TOP_RATED;
                        break;
                    }
                }
            }
        } else {
            mSelectedQueryType = QueryType.POPULAR;
        }

        switch (mSelectedQueryType.toString()) {
            case "POPULAR": {
                showPopular();
                break;
            }
            case "TOP_RATED": {
                showTopRated();
                break;
            }
        }
        System.out.println("onCreate");

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(QUERY_TYPE_PARAM, mSelectedQueryType.toString());
        super.onSaveInstanceState(outState);
        System.out.println("onSaveInstanceState");

    }

    private void setupMovieGridRecyclerView() {
        int spanCount = getResources().getInteger(R.integer.column_count);
        MainActivity context = this;
        boolean reverseLayout = false;

        GridLayoutManager movieGridLayoutManager =
                new GridLayoutManager(context, spanCount, GridLayoutManager.VERTICAL, reverseLayout);
        mMovieGridRecyclerView.setLayoutManager(movieGridLayoutManager);

        mPopularMoviesAdapter = new PopularMoviesAdapter(this);
        mMovieGridRecyclerView.setAdapter(mPopularMoviesAdapter);
    }


    @Override
    public void onClick(MovieInfo movieInfo) {
        Intent movieDetailsIntent = new Intent(this, MovieDetailsActivity.class);
        movieDetailsIntent.putExtra(MovieDetailsActivity.MOVIE_INFO_INTENT_PARAM, movieInfo);
        startActivity(movieDetailsIntent);
    }

    private void startMoviesLoader(QueryType queryType) {

        Bundle queryBundle = new Bundle();
        queryBundle.putString(QUERY_TYPE_PARAM, queryType.toString());

        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<String> movieDbLoader = loaderManager.getLoader(MOVIE_DB_LOADER);
        if (movieDbLoader == null) {
            loaderManager.initLoader(MOVIE_DB_LOADER, queryBundle, this);
        } else {
            loaderManager.restartLoader(MOVIE_DB_LOADER, queryBundle, this);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.popular_movies_filter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_popular_movies: {
                showPopular();
                return true;
            }
            case R.id.action_top_rated: {
                showTopRated();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<List<MovieInfo>> onCreateLoader(int id, final Bundle args) {

        return new AsyncTaskLoader<List<MovieInfo>>(this) {

            @Override
            protected void onStartLoading() {
                if (args == null) {
                    return;
                }
                showLoader();
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
                            break;
                        }
                        case "TOP_RATED": {
                            listMovies = controller.getTopRatedMovies();
                            break;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Handler uiHandler = new Handler(getContext().getMainLooper());
                    uiHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            // TODO calls to .setVisibility(VISIBLE) don't work when called from here
                            showErrorMessage();
                        }
                    });
                }
                return listMovies;
            }
        };

    }

    private void showPopular() {
        mSelectedQueryType = QueryType.POPULAR;
        mCurrentlyDisplayingTextView.setText(getString(R.string.popular_movies_label));
        startMoviesLoader(QueryType.POPULAR);
    }

    private void showTopRated() {
        mSelectedQueryType = QueryType.TOP_RATED;
        mCurrentlyDisplayingTextView.setText(getString(R.string.top_rated_movies_label));
        startMoviesLoader(QueryType.TOP_RATED);
    }

    private void showLoader() {
        mLoadingIndicator.setVisibility(View.VISIBLE);
        mMovieGridRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageView.setVisibility(View.INVISIBLE);
    }

    private void showErrorMessage() {
        System.out.println("SHOW ERROR MESSAGE");
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mMovieGridRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageView.setVisibility(View.VISIBLE);
    }

    private void showMoviesGrid() {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mMovieGridRecyclerView.setVisibility(View.VISIBLE);
        mErrorMessageView.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onLoadFinished(Loader<List<MovieInfo>> loader, List<MovieInfo> data) {
        mPopularMoviesAdapter.setMovieInfoData(data);
        showMoviesGrid();
    }

    @Override
    public void onLoaderReset(Loader<List<MovieInfo>> loader) {

    }
}
