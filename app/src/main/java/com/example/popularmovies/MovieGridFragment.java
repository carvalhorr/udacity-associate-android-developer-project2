package com.example.popularmovies;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.popularmovies.control.FavoriteController;
import com.example.popularmovies.control.PopularMoviesController;
import com.example.popularmovies.injection.PopularMoviesApplication;
import com.example.popularmovies.model.MovieInfo;
import com.example.popularmovies.task.MovieListAsyncTask;
import com.example.popularmovies.task.MovieListAsyncTaskFactory;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by carvalhorr on 2/11/17.
 */
public class MovieGridFragment
        extends Fragment
        implements LoaderManager.LoaderCallbacks<List<MovieInfo>> {

    // Declaration of user interface elements
    private RecyclerView movieGridRecyclerView;
    private TextView errorMessageView;
    private ProgressBar loadingIndicator;

    // Declaration of list of movies adapter
    private MovieListAdapter movieListAdapter;

    // Constants for the different loaders
    public static final int POPULAR_MOVIE_LOADER = 1;
    public static final int TOP_RATED_MOVIE_LOADER = 2;
    public static final int FAVORITE_MOVIE_LOADER = 3;

    // Name of the parameter passed from the activity
    public static final String PARAM_QUERY_TYPE = "query_type";

    // Type of query of this fragment instance. It musb be one of the loader constants and it is received as a parameter from the activity using the PARAM_QUERY_TYPE.
    private int queryType;

    // themoviedb.org API key. Check api.properties to see the actual value. Gradle is used to inject it here.
    public static final String MOVIE_DB_API_KEY = BuildConfig.API_KEY;

    // Class responsible to handle click on a movie item. The activity where the fragment is located must implement MovieOnClickHandler
    private MovieListAdapter.MovieOnClickHandler movieOnClickHandler;

    private InternetConnectionBroadcastReceiver internetConnectivityBroadcastReceiver;

    @Inject
    public MovieListAsyncTaskFactory movieListAsyncTaskFactory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Load the fragment layout
        View rootView = inflater.inflate(R.layout.fragment_movie_grid, container, false);

        // Get references for view elements
        movieGridRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_movies_grid);
        loadingIndicator = (ProgressBar) rootView.findViewById(R.id.pb_loading_indicator);
        errorMessageView = (TextView) rootView.findViewById(R.id.tv_error_message);

        return rootView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity() instanceof MovieListAdapter.MovieOnClickHandler) {
            movieOnClickHandler = (MovieListAdapter.MovieOnClickHandler) getActivity();
        }

        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(PARAM_QUERY_TYPE)) {
            queryType = bundle.getInt(PARAM_QUERY_TYPE);
            setupMovieGridRecyclerView();
        }

        PopularMoviesApplication.get(getContext()).getComponent().inject(this);

    }

    @Override
    public void onResume() {
        super.onResume();

        // Load the movie list
        startMoviesLoaderIfConnectedToInternet(queryType);

        // Intent filter for internet connectivity action
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);

        // Create new broadcast receiver
        internetConnectivityBroadcastReceiver = new InternetConnectionBroadcastReceiver();

        // Register the receiver to listen to changes on connectivity status
        getActivity().registerReceiver(internetConnectivityBroadcastReceiver, intentFilter);
    }

    @Override
    public void onPause() {

        super.onPause();
        // Stop listening to changes on connectivity status change when the fragment is paused
        getActivity().unregisterReceiver(internetConnectivityBroadcastReceiver);
    }

    private void setupMovieGridRecyclerView() {
        boolean reverseLayout = false;
        int spanCount = getResources().getInteger(R.integer.column_count);

        GridLayoutManager movieGridLayoutManager =
                new GridLayoutManager(getContext(), spanCount, GridLayoutManager.VERTICAL, reverseLayout);
        movieGridRecyclerView.setLayoutManager(movieGridLayoutManager);

        movieListAdapter = new MovieListAdapter(movieOnClickHandler, queryType == FAVORITE_MOVIE_LOADER);


        movieGridRecyclerView.setAdapter(movieListAdapter);
    }

    @Override
    public Loader<List<MovieInfo>> onCreateLoader(final int id, final Bundle args) {
        return movieListAsyncTaskFactory.getMovieListAsyncTask(id);
    }

    @Override
    public void onLoadFinished(Loader<List<MovieInfo>> loader, List<MovieInfo> data) {
        if (data == null) {
            showError();
        } else {
            movieListAdapter.setMovieInfoData(data);
            showData();
        }
    }

    @Override
    public void onLoaderReset(Loader<List<MovieInfo>> loader) {
    }

    private void startMoviesLoaderIfConnectedToInternet(int loader) {

        if (isConnected()) {
            showLoader();

            Bundle bundle = new Bundle();

            // Get a reference to the loader
            LoaderManager loaderManager = getActivity().getSupportLoaderManager();
            Loader<String> movieDbLoader = loaderManager.getLoader(loader);

            loaderManager.initLoader(loader, bundle, this);

        } else {
            showError();
        }
    }

    /**
     * Leave only the loader visible on the user interface.
     */
    private void showLoader() {
        movieGridRecyclerView.setVisibility(View.INVISIBLE);
        errorMessageView.setVisibility(View.INVISIBLE);
        loadingIndicator.setVisibility(View.VISIBLE);
    }

    /**
     * Leave only the RecyclerView visible on the user interface.
     */
    private void showData() {
        movieGridRecyclerView.setVisibility(View.VISIBLE);
        errorMessageView.setVisibility(View.INVISIBLE);
        loadingIndicator.setVisibility(View.INVISIBLE);

    }

    /**
     * Leave only the error message visible on the user interface.
     */
    private void showError() {
        movieGridRecyclerView.setVisibility(View.INVISIBLE);
        errorMessageView.setVisibility(View.VISIBLE);
        loadingIndicator.setVisibility(View.INVISIBLE);

    }

    /**
     * Checks if the device is connected to the internet (Wifi or Mobile data)
     * @return
     */
    private boolean isConnected() {

        final ConnectivityManager cm = (ConnectivityManager) getActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        return (netInfo != null && netInfo.isConnected());
    }

    /**
     * Check if connected to internet and adjust the user interface accordingly.
     */
    private void setupConnectivity() {
        if(isConnected()) {
            startMoviesLoaderIfConnectedToInternet(queryType);
        } else {
            showError();
        }
    }

    /**
     * BroadcastReceiver called when connectivity status change
     */
    class InternetConnectionBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            setupConnectivity();
        }
    }

}

