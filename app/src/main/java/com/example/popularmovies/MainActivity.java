package com.example.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.popularmovies.control.PopularMoviesController;
import com.example.popularmovies.model.MovieInfo;
import com.example.popularmovies.task.FavoriteTasks;

import java.io.IOException;
import java.util.List;

public class MainActivity
        extends AppCompatActivity
        implements PopularMoviesAdapter.MovieOnClickHandler,
        LoaderManager.LoaderCallbacks<List<MovieInfo>> {


    private static final String LOADER_PARAM = "query_type";
    public static final String MOVIE_DB_API_KEY = "a803f4555ef3c766306871fe297ef16a";
    private static final int POPULAR_MOVIE_LOADER = 1;
    private static final int TOP_RATED_MOVIE_LOADER = 2;
    private static final int FAVORITE_MOVIE_LOADER = 3;

    private PopularMoviesAdapter mPopularMoviesAdapter;
    private PopularMoviesAdapter mTopRatedMoviesAdapter;
    private PopularMoviesAdapter mFavoriteMoviesAdapter;

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.movie_collections);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupAdapters();
        mSectionsPagerAdapter = new SectionsPagerAdapter(
                getSupportFragmentManager(),
                mPopularMoviesAdapter,
                mTopRatedMoviesAdapter,
                mFavoriteMoviesAdapter);

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        startMoviesLoader(POPULAR_MOVIE_LOADER);
        startMoviesLoader(TOP_RATED_MOVIE_LOADER);
        startMoviesLoader(FAVORITE_MOVIE_LOADER);
    }

    private void setupAdapters() {
        mPopularMoviesAdapter = new PopularMoviesAdapter(this, false);
        mTopRatedMoviesAdapter = new PopularMoviesAdapter(this, false);
        mFavoriteMoviesAdapter = new PopularMoviesAdapter(this, true);
    }

    @Override
    public void onClick(MovieInfo movieInfo) {
        Intent movieDetailsIntent = new Intent(this, MovieDetailsActivity.class);
        movieDetailsIntent.putExtra(MovieDetailsActivity.MOVIE_INFO_INTENT_PARAM, movieInfo);
        startActivity(movieDetailsIntent);
    }

    @Override
    public void onClick(String movieId) {
        Intent movieDetailsIntent = new Intent(this, MovieDetailsActivity.class);
        movieDetailsIntent.putExtra(MovieDetailsActivity.MOVIE_ID_PARAM, movieId);
        startActivity(movieDetailsIntent);
    }

    private void startMoviesLoader(int loader) {

        Bundle queryBundle = new Bundle();
        queryBundle.putInt(LOADER_PARAM, loader);

        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<String> movieDbLoader = loaderManager.getLoader(loader);
        if (movieDbLoader == null) {
            loaderManager.initLoader(loader, queryBundle, this);
        } else {
            loaderManager.restartLoader(loader, queryBundle, this);
        }
    }

    @Override
    public Loader<List<MovieInfo>> onCreateLoader(final int id, final Bundle args) {

        return new AsyncTaskLoader<List<MovieInfo>>(this) {

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
                int queryType = args.getInt(LOADER_PARAM);
                PopularMoviesController controller = new PopularMoviesController(MOVIE_DB_API_KEY);
                List<MovieInfo> listMovies = null;
                try {
                    switch (queryType) {
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
        switch (loader.getId()) {
            case POPULAR_MOVIE_LOADER: {
                setMovieInfoList(mPopularMoviesAdapter, data);
                break;
            }
            case TOP_RATED_MOVIE_LOADER: {
                setMovieInfoList(mTopRatedMoviesAdapter, data);
                break;
            }
            case FAVORITE_MOVIE_LOADER: {
                setMovieInfoList(mFavoriteMoviesAdapter, data);
                break;
            }

        }
    }

    private void setMovieInfoList(PopularMoviesAdapter adapter, List<MovieInfo> data) {
        if (data == null) {
            //showErrorMessage();
        } else {
            adapter.setMovieInfoData(data);
            //showMoviesGrid();
        }

    }

    @Override
    public void onLoaderReset(Loader<List<MovieInfo>> loader) {

    }
}

class SectionsPagerAdapter extends FragmentPagerAdapter {

    private PopularMoviesAdapter mPopularMoviesAdapter;
    private PopularMoviesAdapter mTopRatedMoviesAdapter;
    private PopularMoviesAdapter mFavoriteMoviesAdapter;

    public SectionsPagerAdapter(FragmentManager fm,
                                PopularMoviesAdapter popularMoviesAdapter,
                                PopularMoviesAdapter topRateMoviesAdapter,
                                PopularMoviesAdapter favoriteMoviesAdapter) {
        super(fm);
        mPopularMoviesAdapter = popularMoviesAdapter;
        mTopRatedMoviesAdapter = topRateMoviesAdapter;
        mFavoriteMoviesAdapter = favoriteMoviesAdapter;
    }

    @Override
    public Fragment getItem(int position) {
        MovieGridFragment movieGridFragment = new MovieGridFragment();
        switch (position) {
            case 0: {
                movieGridFragment.setPopularMovieAdapter(mPopularMoviesAdapter);
                break;
            }
            case 1: {
                movieGridFragment.setPopularMovieAdapter(mTopRatedMoviesAdapter);
                break;
            }
            case 2: {
                movieGridFragment.setPopularMovieAdapter(mFavoriteMoviesAdapter);
                break;
            }
        }
        return movieGridFragment;
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Popular";
            case 1:
                return "Top rated";
            case 2:
                return "Favorite";
        }
        return null;
    }
}

