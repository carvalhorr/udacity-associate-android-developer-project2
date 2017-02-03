package com.example.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.popularmovies.popularmoviesstage1.R;
import com.example.popularmovies.model.MovieInfo;
import com.example.popularmovies.network.PopularMoviesAPI;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

public class MovieDetailsActivity extends AppCompatActivity {

    public static final String MOVIE_INFO_INTENT_PARAM = "movie_info";

    private TextView mMovieTitleTextView;
    private TextView mReleaseDateTextView;
    private TextView mVoteAverageTextView;
    private ImageView mMoviePosterImageView;
    private TextView mMoviePlot;

    private MovieInfo mMovieInfo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        getViewsReferences();
        getMovieInfoFromIntent();
        if (mMovieInfo == null) {
            getMovieInfoFromIntent();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        showMovieDetails();
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
        if (intent.hasExtra(MOVIE_INFO_INTENT_PARAM)){
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
        }
    }

}
