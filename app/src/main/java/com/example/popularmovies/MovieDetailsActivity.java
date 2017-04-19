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
import com.example.popularmovies.injection.PopularMoviesApplication;
import com.example.popularmovies.model.MovieInfo;
import com.example.popularmovies.model.MovieReview;
import com.example.popularmovies.model.MovieVideo;
import com.example.popularmovies.network.PopularMoviesAPI;
import com.example.popularmovies.task.FavoriteTasks;
import com.example.popularmovies.task.MovieInfoTasks;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.inject.Inject;

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

    @Inject
    public MovieInfoTasks movieInfoTasks;

    // Name of MovieInfo parameter received from caller
    public static final String MOVIE_INFO_INTENT_PARAM = "movie_info";

    // Name of movieId parameter received from caller
    public static final String MOVIE_ID_PARAM = "movie_id";

    // Declare data binding for the activity_movie_details.xml layout elements
    private ActivityMovieDetailsBinding binding;

    // MovieInfo received as parameter
    private MovieInfo movieInfo = null;

    // MovieId received as parameter
    private String movieId = null;

    // Indicate if the movie is a favorite or not
    private boolean isFavorite = false;

    // Indicate if finished loading the information if the movie is favorite from the ContentProvider
    private boolean isFavoriteLoaded = false;

    private boolean movieInfoLoaded = false;
    private boolean videosLoaded = false;
    private boolean reviewsLoaded = false;

    private InternetConnectionBroadcastReceiver internetConnectivityBroadcastReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inject dependencies
        ((PopularMoviesApplication) getApplication()).getComponent().inject(this);

        // Load activity layout
        setContentView(R.layout.activity_movie_details);

        // Bind the layout views
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details);

        // Get the movieInfo or movieId passed as parameter

        // Show the loading indicator
        showLoader();

        getMovieInfoFromIntent();
        if (movieInfo != null) {

            // If a movieInfo was received as parameter
            movieId = movieInfo.getMovieId();
        } else {

            // If only a movieId was passed as parameter

            movieId = getIntent().getStringExtra(MOVIE_ID_PARAM);
        }
        loadAndDisplayData();
    }

    private void loadAndDisplayData() {
        if (movieInfo != null) {

            // Show the info on the user interface
            movieInfoLoaded(movieInfo);
        } else {

            // Load the movieInfo asynchronously from the internet
            movieInfoTasks.retrieveMovieInfo(this, movieId, this);
        }

        // Load the movie videos asynchronously from the internet
        movieInfoTasks.retrieveMovieVideos(this, movieId, this);

        // Load the movie reviews asynchronously from the internet
        movieInfoTasks.retrieveMovieReviews(this, movieId, this);

    }

    @Override
    protected void onPause() {
        super.onPause();

        // Stop listening to changes on connectivity status change when the fragment is paused
        unregisterReceiver(internetConnectivityBroadcastReceiver);

    }

    @Override
    protected void onResume() {
        super.onResume();

        // Intent filter for internet connectivity action
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);

        // Create new broadcast receiver
        internetConnectivityBroadcastReceiver = new InternetConnectionBroadcastReceiver();

        // Register the receiver to listen to changes on connectivity status
        registerReceiver(internetConnectivityBroadcastReceiver, intentFilter);

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
            movieInfo = intent.getParcelableExtra(MOVIE_INFO_INTENT_PARAM);
        }
    }

    // Display the movie info in the user interface
    private void showMovieDetails() {

        if (movieInfo != null) {
            binding.tvMovieTitle.setText(movieInfo.getTitle());

            if (movieInfo.getReleaseDate() != null) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                binding.tvReleaseDate.setText(dateFormat.format(movieInfo.getReleaseDate()));

            }

            if (movieInfo.getVoteAverage() != null)
                binding.tvVoteAverage.setText(movieInfo.getVoteAverage().toString() + " / 10");

            Picasso.with(this)
                    .load(PopularMoviesAPI.BASE_POSTER_PATH + "w780" + movieInfo.getPosterPath())
                    .placeholder(R.drawable.poster)
                    .error(R.drawable.poster)
                    .into(binding.ivMovieThumbnail);

            Picasso.with(this)
                    .load(PopularMoviesAPI.BASE_POSTER_PATH + "w780" + movieInfo.getBackdropPath())
                    .placeholder(R.drawable.backdrop)
                    .error(R.drawable.backdrop)
                    .into(binding.ivBackdrop);

            binding.tvMoviePlot.setText(movieInfo.getPlot());
        }

    }

    private void showLoader() {
        binding.pbLoadingIndicator.setVisibility(View.VISIBLE);
    }

    private void hideLoader() {
        if (movieInfoLoaded && videosLoaded && reviewsLoaded)
            binding.pbLoadingIndicator.setVisibility(View.INVISIBLE);
    }

    private void showError() {
        binding.tvErrorMessage.setVisibility(View.VISIBLE);
    }

    private void hideError() {
        binding.tvErrorMessage.setVisibility(View.INVISIBLE);
    }

    /**
     * Show the correct icon on the favorite floating action bar based on wheather the
     * movie is favorite or not
     */
    private void showFavorite() {

        if (isFavorite) {
            binding.floatingActionButton.setImageResource(android.R.drawable.btn_star_big_on);
        } else {
            binding.floatingActionButton.setImageResource(android.R.drawable.btn_star_big_off);
        }

    }

    /**
     * Handle click on the favorite floating action bar
     * @param view
     */
    public void onFavoriteClick(View view) {

        if (isFavoriteLoaded) {
            if (!isFavorite) {
                FavoriteTasks.addToFavoriteTask(this, movieInfo, this);
            } else {
                FavoriteTasks.removeFromFavoriteTask(this, movieInfo.getMovieId(), this);
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

        movieInfoLoaded = true;

        if (movieInfo == null) {

            showError();

        } else {

            // Save the movieInfo information received
            this.movieInfo = movieInfo;

            // Check if the movie is favorite
            FavoriteTasks.isFavorite(this, this.movieInfo.getMovieId(), this);
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
        binding.rvVideos.setLayoutManager(movieGridLayoutManager);
        binding.rvVideos.setAdapter(adapter);
        adapter.setMovieInfoData(videos);

        videosLoaded = true;
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
        binding.rvReviews.setLayoutManager(reviewLayoutManager);
        binding.rvReviews.setAdapter(adapter);
        adapter.setMovieReviews(reviews);

        reviewsLoaded = true;
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
