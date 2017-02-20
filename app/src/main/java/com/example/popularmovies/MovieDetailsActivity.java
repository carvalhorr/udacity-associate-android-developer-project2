package com.example.popularmovies;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;
import android.view.View;

import com.example.popularmovies.databinding.ActivityMovieDetailsBinding;
import com.example.popularmovies.model.MovieInfo;
import com.example.popularmovies.model.MovieReview;
import com.example.popularmovies.model.MovieVideo;
import com.example.popularmovies.network.PopularMoviesAPI;
import com.example.popularmovies.task.FavoriteTasks;
import com.example.popularmovies.task.MovieInfoTasks;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Show the details for a specific movie. The details consist of the movie title, poster, r
 * elease date, vote average, plot, list of movies and list of reviews.
 * <p>
 * Created by carvalhorr
 */
public class MovieDetailsActivity
        extends AppCompatActivity
        implements FavoriteTasks.FavoriteCallbacks,
        MovieInfoTasks.MovieInfoCallbacks,
        MovieVideosAdapter.VideoOnClickHandler,
        MovieReviewsAdapter.ReviewOnClickHandler {

    // Name of MovieInfo parameter received from caller
    public static final String MOVIE_INFO_INTENT_PARAM = "movie_info";

    // Name of movieId parameter received from caller
    public static final String MOVIE_ID_PARAM = "movie_id";

    // Declare data binding for the activity_movie_details.xml layout elements
    private ActivityMovieDetailsBinding mBinding;

    // MovieInfo received as parameter
    private MovieInfo mMovieInfo = null;

    // MovieId received as parameter
    private String mMovieId = null;

    // Indicate if the movie is a favorite or not
    private boolean isFavorite = false;

    // Indicate if finished loading the information if the movie is favorite from the ContentProvider
    private boolean isFavoriteLoaded = false;

    private boolean mMovieInfoLoaded = false;
    private boolean mVideosLoaded = false;
    private boolean mReviewsLoaded = false;

    private InternetConnectionBroadcastReceiver mInternetConnectivityBroadcastReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load activity layout
        setContentView(R.layout.activity_movie_details);

        // Bind the layout views
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details);

        // Get the movieInfo or movieId passed as parameter


        // Show the loading indicator
        showLoader();

        getMovieInfoFromIntent();
        if (mMovieInfo != null) {

            // If a movieInfo was received as parameter
            mMovieId = mMovieInfo.getMovieId();
        } else {

            // If only a movieId was passed as parameter

            mMovieId = getIntent().getStringExtra(MOVIE_ID_PARAM);
        }
        loadAndDisplayData();
    }

    private void loadAndDisplayData() {
        if (mMovieInfo != null) {

            // Show the info on the user interface
            movieInfoLoaded(mMovieInfo);
        } else {

            // Load the movieInfo asynchronously from the internet
            MovieInfoTasks.retrieveMovieInfo(this, mMovieId, this);
        }

        // Load the movie videos asynchronously from the internet
        MovieInfoTasks.retrieveMovieVideos(this, mMovieId, this);

        // Load the movie reviews asynchronously from the internet
        MovieInfoTasks.retrieveMovieReviews(this, mMovieId, this);

    }

    @Override
    protected void onPause() {
        super.onPause();

        // Stop listening to changes on connectivity status change when the fragment is paused
        unregisterReceiver(mInternetConnectivityBroadcastReceiver);

    }

    @Override
    protected void onResume() {
        super.onResume();

        // Intent filter for internet connectivity action
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);

        // Create new broadcast receiver
        mInternetConnectivityBroadcastReceiver = new InternetConnectionBroadcastReceiver();

        // Register the receiver to listen to changes on connectivity status
        registerReceiver(mInternetConnectivityBroadcastReceiver, intentFilter);

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

    // Get the movieInfo if passed as parameter
    private void getMovieInfoFromIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra(MOVIE_INFO_INTENT_PARAM)) {
            mMovieInfo = intent.getParcelableExtra(MOVIE_INFO_INTENT_PARAM);
        }
    }

    // Display the movie info in the user interface
    private void showMovieDetails() {

        if (mMovieInfo != null) {
            mBinding.tvMovieTitle.setText(mMovieInfo.getTitle());

            if (mMovieInfo.getReleaseDate() != null) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                mBinding.tvReleaseDate.setText(dateFormat.format(mMovieInfo.getReleaseDate()));

            }

            if (mMovieInfo.getVoteAverage() != null)
                mBinding.tvVoteAverage.setText(mMovieInfo.getVoteAverage().toString() + " / 10");

            Picasso.with(this)
                    .load(PopularMoviesAPI.BASE_POSTER_PATH + "w780" + mMovieInfo.getPosterPath())
                    .placeholder(R.drawable.poster)
                    .error(R.drawable.poster)
                    .into(mBinding.ivMovieThumbnail);

            Picasso.with(this)
                    .load(PopularMoviesAPI.BASE_POSTER_PATH + "w780" + mMovieInfo.getBackdropPath())
                    .placeholder(R.drawable.backdrop)
                    .error(R.drawable.backdrop)
                    .into(mBinding.ivBackdrop);

            mBinding.tvMoviePlot.setText(mMovieInfo.getPlot());
        }

    }

    private void showLoader() {
        mBinding.pbLoadingIndicator.setVisibility(View.VISIBLE);
    }

    private void hideLoader() {
        if (mMovieInfoLoaded && mVideosLoaded && mReviewsLoaded)
            mBinding.pbLoadingIndicator.setVisibility(View.INVISIBLE);
    }

    private void showError() {
        mBinding.tvErrorMessage.setVisibility(View.VISIBLE);
    }

    private void hideError() {
        mBinding.tvErrorMessage.setVisibility(View.INVISIBLE);
    }

    /**
     * Show the correct icon on the favorite floating action bar based on wheather the
     * movie is favorite or not
     */
    private void showFavorite() {

        if (isFavorite) {
            mBinding.floatingActionButton.setImageResource(android.R.drawable.btn_star_big_on);
        } else {
            mBinding.floatingActionButton.setImageResource(android.R.drawable.btn_star_big_off);
        }

    }

    /**
     * Handle click on the favorite floating action bar
     * @param view
     */
    public void onFavoriteClick(View view) {

        if (isFavoriteLoaded) {
            if (!isFavorite) {
                FavoriteTasks.addToFavoriteTask(this, mMovieInfo, this);
            } else {
                FavoriteTasks.removeFromFavoriteTask(this, mMovieInfo.getMovieId(), this);
            }
        }

    }

    /**
     * Update the state of the movie to favorite when it was added to favorite
     * @param movieId
     */
    @Override
    public void addedToFavorite(String movieId) {

        isFavorite = true;
        showFavorite();

    }

    /**
     * Update the state of the movie no favorite when it was removed from favorites
     * @param movieId
     */
    @Override
    public void removedFromFavorite(String movieId) {

        isFavorite = false;
        showFavorite();

    }

    /**
     * Called from task that checks if the movie is favorite to update the info retrieved from the
     * ContentProvider
     *
     * @param isFavorite
     */
    @Override
    public void isFavorite(boolean isFavorite) {

        this.isFavorite = isFavorite;
        isFavoriteLoaded = true;
        showFavorite();

    }

    @Override
    public void favoritesLoaded(List<MovieInfo> favorites) {
        // Not used in this class
    }

    /**
     * Called when the movieInfo was loaded from the internet or was passed as parameter
     * from the intent caller
     *
     * @param movieInfo
     */
    @Override
    public void movieInfoLoaded(MovieInfo movieInfo) {

        mMovieInfoLoaded = true;

        if (movieInfo == null) {

            showError();

        } else {

            // Save the movieInfo information received
            mMovieInfo = movieInfo;

            // Check if the movie is favorite
            FavoriteTasks.isFavorite(this, mMovieInfo.getMovieId(), this);
            hideLoader();
            hideError();

            // Display the movie details
            showMovieDetails();

        }

    }

    /**
     * Called when task responsible for laoding the movie videos finished.
     */
    @Override
    public void movieVideosLoaded(List<MovieVideo> videos) {

        // Setup the movie videos adapter
        MovieVideosAdapter adapter = new MovieVideosAdapter(this);
        LinearLayoutManager movieGridLayoutManager =
                new LinearLayoutManager(this, GridLayoutManager.HORIZONTAL, false);
        movieGridLayoutManager.setAutoMeasureEnabled(true);
        mBinding.rvVideos.setLayoutManager(movieGridLayoutManager);
        mBinding.rvVideos.setAdapter(adapter);
        adapter.setMovieInfoData(videos);

        mVideosLoaded = true;
        hideLoader();

    }

    /**
     * Called when the task responsible for loading the movie reviews finished
     * @param reviews
     */
    @Override
    public void movieReviewsLoaded(List<MovieReview> reviews) {

        // Setup the reviews adapter
        MovieReviewsAdapter adapter = new MovieReviewsAdapter(this);
        LinearLayoutManager reviewLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mBinding.rvReviews.setLayoutManager(reviewLayoutManager);
        mBinding.rvReviews.setAdapter(adapter);
        adapter.setMovieReviews(reviews);

        mReviewsLoaded = true;
        hideLoader();

    }

    /**
     * Open youtube to play the video clicked
     * @param movieVideo
     */
    @Override
    public void onClick(MovieVideo movieVideo) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + movieVideo.getKey())));
    }

    /**
     * Open a review on the browser when clicked
     * @param movieReview
     */
    @Override
    public void onClick(MovieReview movieReview) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(movieReview.getUrl())));
    }

    /**
     * Checks if the device is connected to the internet (Wifi or Mobile data)
     * @return
     */
    private boolean isConnected() {

        final ConnectivityManager cm = (ConnectivityManager) this
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        return (netInfo != null && netInfo.isConnected());
    }

    /**
     * Check if connected to internet and adjust the user interface accordingly.
     */
    private void setupConnectivity() {
        if(isConnected()) {
            loadAndDisplayData();
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
