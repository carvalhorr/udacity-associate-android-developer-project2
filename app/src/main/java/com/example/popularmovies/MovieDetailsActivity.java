package com.example.popularmovies;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.popularmovies.control.PopularMoviesController;
import com.example.popularmovies.databinding.ActivityMovieDetailsBinding;
import com.example.popularmovies.model.MovieInfo;
import com.example.popularmovies.model.MovieVideo;
import com.example.popularmovies.network.PopularMoviesAPI;
import com.example.popularmovies.task.FavoriteTasks;
import com.example.popularmovies.task.MovieInfoTasks;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

public class MovieDetailsActivity
        extends AppCompatActivity
        implements FavoriteTasks.FavoriteCallbacks, MovieInfoTasks.MovieInfoCallbacks, MovieVideosAdapter.VideoOnClickHandler {

    public static final String MOVIE_INFO_INTENT_PARAM = "movie_info";

    public static final String MOVIE_ID_PARAM = "movie_id";

    private static final int MOVIE_INFO_LOADER = 1;

    private ActivityMovieDetailsBinding mBinding;

    private MovieInfo mMovieInfo = null;
    private String mMovieId = null;
    private boolean isFavorite = false;
    private boolean isFavoriteLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details);

        getMovieInfoFromIntent();
        if (mMovieInfo != null) {
            mMovieId = mMovieInfo.getMovieId();
            movieInfoLoaded(mMovieInfo);
        } else {
            mMovieId = getIntent().getStringExtra(MOVIE_ID_PARAM);
            showLoader();
            MovieInfoTasks.retrieveMovieInfo(this, mMovieId, this);
        }
        MovieInfoTasks.retrieveMovieVideos(this, mMovieId, this);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
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
            mBinding.tvMovieTitle.setText(mMovieInfo.getTitle());
            if (mMovieInfo.getReleaseDate() != null) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                mBinding.tvReleaseDate.setText(dateFormat.format(mMovieInfo.getReleaseDate()));

            }
            if (mMovieInfo.getVoteAverage() != null)
                mBinding.tvVoteAverage.setText(mMovieInfo.getVoteAverage().toString() + " / 10");
            Picasso.with(this).load(
                    PopularMoviesAPI.BASE_POSTER_PATH + "w780" + mMovieInfo.getPosterPath())
                    .into(mBinding.ivMovieThumbnail);
            mBinding.tvMoviePlot.setText(mMovieInfo.getPlot());
        }
    }

    /**
     * Code added because the if the scrollview had any padding or margin the bottom would be cut.
     */
    private void setCorrectContentPadding() {
        mBinding.tvMovieTitle.measure(View.MeasureSpec.makeMeasureSpec(mBinding.tvMovieTitle.getWidth(), View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(1000, View.MeasureSpec.AT_MOST));
        mBinding.llContent.setPadding(
                0,
                mBinding.tvMovieTitle.getMeasuredHeight(),
                0,
                0);
    }

    private void showLoader() {

    }

    private void hideLoader() {

    }

    private void showError() {

    }

    private void showFavorite() {
        if (isFavorite) {
            mBinding.floatingActionButton.setImageResource(android.R.drawable.btn_star_big_on);
        } else {
            mBinding.floatingActionButton.setImageResource(android.R.drawable.btn_star_big_off);
        }
    }

    public void onFavoriteClick(View view) {
        if (isFavoriteLoaded)
            if (!isFavorite) {
                FavoriteTasks.addToFavoriteTask(this, mMovieInfo, this);
            } else {
                FavoriteTasks.removeFromFavoriteTask(this, mMovieInfo.getMovieId(), this);
            }
    }

    @Override
    public void addedToFavorite(String movieId) {
        isFavorite = true;
        showFavorite();
    }

    @Override
    public void removedFromFavorite(String movieId) {
        isFavorite = false;
        showFavorite();
    }

    @Override
    public void isFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
        isFavoriteLoaded = true;
        showFavorite();
    }

    @Override
    public void favoritesLoaded(List<MovieInfo> favorites) {

    }

    @Override
    public void movieInfoLoaded(MovieInfo movieInfo) {
        if (movieInfo == null) {
            showError();
        } else {
            mMovieInfo = movieInfo;
            FavoriteTasks.isFavorite(this, mMovieInfo.getMovieId(), this);
            hideLoader();
            showMovieDetails();
            //setCorrectContentPadding();
        }
    }

    @Override
    public void movieVideosLoaded(List<MovieVideo> videos) {
        MovieVideosAdapter adapter = new MovieVideosAdapter(this);
        LinearLayoutManager movieGridLayoutManager =
                new LinearLayoutManager(this, GridLayoutManager.HORIZONTAL, false);
        movieGridLayoutManager.setAutoMeasureEnabled(true);
        mBinding.rvVideos.setLayoutManager(movieGridLayoutManager);
        mBinding.rvVideos.setAdapter(adapter);
        adapter.setMovieInfoData(videos);
    }

    @Override
    public void onClick(MovieVideo movieVideo) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + movieVideo.getKey())));
    }
}
