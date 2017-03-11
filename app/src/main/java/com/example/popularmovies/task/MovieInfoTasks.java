package com.example.popularmovies.task;

import android.content.Context;
import android.os.AsyncTask;

import com.example.popularmovies.MainActivity;
import com.example.popularmovies.MovieGridFragment;
import com.example.popularmovies.control.PopularMoviesController;
import com.example.popularmovies.model.MovieInfo;
import com.example.popularmovies.model.MovieReview;
import com.example.popularmovies.model.MovieVideo;

import java.io.IOException;
import java.util.List;

/**
 * AsynTasks to load movie info, videos and reviews from the internet. These methods are intended
 * to be called from the UI in order to execute in a separate thread from the main UI thread.
 *
 * Created by carvalhorr on 2/9/17.
 */
public class MovieInfoTasks {

    /**
     * Retrieve the movie info for a movie from the internet using an AsyncTask.
     * @param context
     * @param movieId
     * @param callbacks
     */
    public static void retrieveMovieInfo(final Context context, final String movieId, final MovieInfoCallbacks callbacks) {
        new AsyncTask<String, Void, MovieInfo>() {

            @Override
            protected MovieInfo doInBackground(String... params) {
                // Call the corresponding controller method to retrieve the movie info from the internet
                PopularMoviesController controller = new PopularMoviesController(MovieGridFragment.MOVIE_DB_API_KEY);
                try {
                    return controller.getMovieInfo(movieId);
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(MovieInfo movieInfo) {
                super.onPostExecute(movieInfo);

                // Return the movie info retrieved from the internet to the caller
                callbacks.movieInfoLoaded(movieInfo);
            }

        }.execute(movieId);
    }

    /**
     * Retrieve from the internet the list of videos for a movie using an AsyncTask.
     * @param context
     * @param movieId
     * @param callbacks
     */
    public static void retrieveMovieVideos(final Context context, final String movieId, final MovieInfoCallbacks callbacks) {

        new AsyncTask<String, Void, List<MovieVideo>>() {

            @Override
            protected List<MovieVideo> doInBackground(String... params) {
                // Call the corresponding controller method to retrieve the list of videos
                // from the internet.
                PopularMoviesController controller = new PopularMoviesController(MovieGridFragment.MOVIE_DB_API_KEY);
                try {
                    return controller.getVideosForMovie(movieId);
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(List<MovieVideo> videos) {
                super.onPostExecute(videos);

                // Return the list of videos retrieved from the internet to the caller
                callbacks.movieVideosLoaded(videos);
            }

        }.execute(movieId);
    }

    /**
     * Retrieve from the internet the list of reviews for a movie using an AsyncTask.
     * @param context
     * @param movieId
     * @param callbacks
     */
    public static void retrieveMovieReviews(final Context context, final String movieId, final MovieInfoCallbacks callbacks) {
        new AsyncTask<String, Void, List<MovieReview>>() {

            @Override
            protected List<MovieReview> doInBackground(String... params) {
                // Call the corresponding controller method to retrieve the list of reviews for a
                // movie using an AsyncTask
                PopularMoviesController controller = new PopularMoviesController(MovieGridFragment.MOVIE_DB_API_KEY);
                try {
                    return controller.getReviewsForMovie(movieId);
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(List<MovieReview> reviews) {
                super.onPostExecute(reviews);

                // Return the list of reviews retrieved from the internet to the caller
                callbacks.movieReviewsLoaded(reviews);
            }

        }.execute(movieId);

    }

    /**
     * Interface that any class calling the methods defined in this class need to implement
     * in order to get the results of the methods execution
     */
    public interface MovieInfoCallbacks {
        /**
         * Return the movie info loaded to the caller
         * @param movieInfo
         */
        void movieInfoLoaded(MovieInfo movieInfo);

        /**
         * Return the list of videos loaded for a movie to the caller
         * @param videos
         */
        void movieVideosLoaded(List<MovieVideo> videos);

        /**
         * Return the list of reviews loaded for a movie to the caller
         * @param reviews
         */
        void movieReviewsLoaded(List<MovieReview> reviews);
    }
}
