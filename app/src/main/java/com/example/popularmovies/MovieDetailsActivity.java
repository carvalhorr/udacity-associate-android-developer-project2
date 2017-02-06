package com.example.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.popularmovies.control.PopularMoviesController;
import com.example.popularmovies.model.MovieInfo;
import com.example.popularmovies.network.PopularMoviesAPI;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class MovieDetailsActivity
        extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<MovieInfo> {

    public static final String MOVIE_INFO_INTENT_PARAM = "movie_info";

    public static final String MOVIE_ID_PARAM = "movie_id";

    private static final int MOVIE_INFO_LOADER = 1;

    private TextView mMovieTitleTextView;
    private TextView mReleaseDateTextView;
    private TextView mVoteAverageTextView;
    private ImageView mMoviePosterImageView;
    private TextView mMoviePlot;

    private MovieInfo mMovieInfo = null;
    private String mMovieId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        getViewsReferences();
        getMovieInfoFromIntent();
        if (mMovieInfo != null) {
            mMovieId = mMovieInfo.getMovieId();
        } else {
            mMovieId = getIntent().getStringExtra(MOVIE_ID_PARAM);
        }
        startLoader();
    }

    private void startLoader() {
        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<String> movieDbLoader = loaderManager.getLoader(MOVIE_INFO_LOADER);
        if (movieDbLoader == null) {
            loaderManager.initLoader(MOVIE_INFO_LOADER, null, this);
        } else {
            loaderManager.restartLoader(MOVIE_INFO_LOADER, null, this);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void getViewsReferences() {
        mMovieTitleTextView = (TextView) findViewById(R.id.tv_movie_title);
        mReleaseDateTextView = (TextView) findViewById(R.id.tv_release_date);
        mVoteAverageTextView = (TextView) findViewById(R.id.tv_vote_average);
        mMoviePosterImageView = (ImageView) findViewById(R.id.iv_movie_thumbnail);
        mMoviePlot = (TextView) findViewById(R.id.tv_movie_plot);
    }

    private void getMovieInfoFromIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra(MOVIE_INFO_INTENT_PARAM)) {
            mMovieInfo = intent.getParcelableExtra(MOVIE_INFO_INTENT_PARAM);
        }
    }

    private void getMovieInfoFromSavedBundle(Bundle savedInstanceState) {

    }

    private void showMovieDetails() {
        if (mMovieInfo != null) {
            mMovieTitleTextView.setText(mMovieInfo.getTitle());
            if (mMovieInfo.getReleaseDate() != null) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                mReleaseDateTextView.setText(dateFormat.format(mMovieInfo.getReleaseDate()));

            }
            if (mMovieInfo.getVoteAverage() != null)
                mVoteAverageTextView.setText(mMovieInfo.getVoteAverage().toString());
            Picasso.with(this).load(PopularMoviesAPI.BASE_POSTER_PATH + "w780" + mMovieInfo.getPosterPath()).into(mMoviePosterImageView);
            mMoviePlot.setText(mMovieInfo.getPlot());
            System.out.println(mMovieInfo.getMovieId());
        }
    }

    private void showLoader() {

    }

    private void hideLoader() {

    }

    private void showError() {

    }

    @Override
    public Loader<MovieInfo> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<MovieInfo>(this) {

            @Override
            protected void onStartLoading() {
                if (mMovieInfo == null) {
                    if (mMovieId == null) {
                        return;
                    }
                    showLoader();
                    forceLoad();
                } else {
                    deliverResult(mMovieInfo);
                }
            }

            @Override
            public MovieInfo loadInBackground() {
                String movieId = mMovieId;
                PopularMoviesController controller = new PopularMoviesController(MainActivity.MOVIE_DB_API_KEY);
                try {
                    return controller.getMovieInfo(movieId);
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<MovieInfo> loader, MovieInfo data) {
        if (data == null) {
            showError();
        } else {
            mMovieInfo = data;
            hideLoader();
            showMovieDetails();
        }
    }

    @Override
    public void onLoaderReset(Loader<MovieInfo> loader) {

    }
}
