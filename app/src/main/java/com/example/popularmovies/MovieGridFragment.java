package com.example.popularmovies;

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

import com.example.popularmovies.control.PopularMoviesController;
import com.example.popularmovies.model.MovieInfo;
import com.example.popularmovies.task.FavoriteTasks;

import java.io.IOException;
import java.util.List;

/**
 * Created by carvalhorr on 2/11/17.
 */

public class MovieGridFragment
        extends Fragment
        implements LoaderManager.LoaderCallbacks<List<MovieInfo>> {

    private RecyclerView mMovieGridRecyclerView;
    private TextView mErrorMessageView;
    private ProgressBar mLoadingIndicator;

    private PopularMoviesAdapter mPopularMoviesAdapter;

    public static final int POPULAR_MOVIE_LOADER = 1;
    public static final int TOP_RATED_MOVIE_LOADER = 2;
    public static final int FAVORITE_MOVIE_LOADER = 3;

    public static final String PARAM_QUERY_TYPE = "query_type";

    private int mQueryType;

    public static final String MOVIE_DB_API_KEY = BuildConfig.API_KEY;

    private PopularMoviesAdapter.MovieOnClickHandler mMovieOnClickHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_grid, container, false);
        mMovieGridRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_movies_grid);

        mLoadingIndicator = (ProgressBar) rootView.findViewById(R.id.pb_loading_indicator);
        mErrorMessageView = (TextView) rootView.findViewById(R.id.tv_error_message);

        return rootView;
    }


    private void startMoviesLoader(int loader) {
        showLoader();
        Bundle  bundle = new Bundle();
        LoaderManager loaderManager = getActivity().getSupportLoaderManager();
        Loader<String> movieDbLoader = loaderManager.getLoader(loader);
        if (movieDbLoader == null) {
            loaderManager.initLoader(loader, bundle, this);
        } else {
            loaderManager.restartLoader(loader, bundle, this);
        }
    }

    private void showLoader() {
        mMovieGridRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageView.setVisibility(View.INVISIBLE);
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    private void showData() {
        mMovieGridRecyclerView.setVisibility(View.VISIBLE);
        mErrorMessageView.setVisibility(View.INVISIBLE);
        mLoadingIndicator.setVisibility(View.INVISIBLE);

    }

    private void showError() {
        mMovieGridRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageView.setVisibility(View.VISIBLE);
        mLoadingIndicator.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        if (getActivity() instanceof PopularMoviesAdapter.MovieOnClickHandler) {
            mMovieOnClickHandler = (PopularMoviesAdapter.MovieOnClickHandler) getActivity();
        }
        if (bundle != null && bundle.containsKey(PARAM_QUERY_TYPE)) {
            mQueryType = bundle.getInt(PARAM_QUERY_TYPE);
            setupMovieGridRecyclerView();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        startMoviesLoader(mQueryType);
    }

    private void setupMovieGridRecyclerView() {
        int spanCount = getResources().getInteger(R.integer.column_count);
        boolean reverseLayout = false;

        GridLayoutManager movieGridLayoutManager =
                new GridLayoutManager(getContext(), spanCount, GridLayoutManager.VERTICAL, reverseLayout);
        mMovieGridRecyclerView.setLayoutManager(movieGridLayoutManager);

        mPopularMoviesAdapter = new PopularMoviesAdapter(mMovieOnClickHandler, mQueryType == FAVORITE_MOVIE_LOADER);


        mMovieGridRecyclerView.setAdapter(mPopularMoviesAdapter);
    }

    @Override
    public Loader<List<MovieInfo>> onCreateLoader(final int id, final Bundle args) {
        return new AsyncTaskLoader<List<MovieInfo>>(getContext()) {

            @Override
            protected void onStartLoading() {
                if (args == null) {
                    return;
                }
                //showLoader();
                if (id == FAVORITE_MOVIE_LOADER) {
                    FavoriteTasks.loadFavorites(getContext(), new FavoriteTasks.FavoriteCallbacks() {
                        @Override
                        public void addedToFavorite(String movieId) {

                        }

                        @Override
                        public void removedFromFavorite(String movieId) {

                        }

                        @Override
                        public void isFavorite(boolean isFavorite) {

                        }

                        @Override
                        public void favoritesLoaded(List<MovieInfo> favorites) {
                            deliverResult(favorites);
                        }
                    });

                } else {
                    forceLoad();
                }
            }

            @Override
            public List<MovieInfo> loadInBackground() {
                PopularMoviesController controller = new PopularMoviesController(MOVIE_DB_API_KEY);
                List<MovieInfo> listMovies = null;
                try {
                    switch (mQueryType) {
                        case POPULAR_MOVIE_LOADER: {
                            listMovies = controller.getPopularMovies();
                            break;
                        }
                        case TOP_RATED_MOVIE_LOADER: {
                            listMovies = controller.getTopRatedMovies();
                            break;
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
        setMovieInfoList(mPopularMoviesAdapter, data);
    }

    private void setMovieInfoList(PopularMoviesAdapter adapter, List<MovieInfo> data) {
        if (data == null) {
            showError();
        } else {
            adapter.setMovieInfoData(data);
            showData();
        }

    }

    @Override
    public void onLoaderReset(Loader<List<MovieInfo>> loader) {
    }

}
