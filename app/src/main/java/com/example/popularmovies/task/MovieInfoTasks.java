package com.example.popularmovies.task;

import android.content.Context;
import android.os.AsyncTask;

import com.example.popularmovies.MainActivity;
import com.example.popularmovies.control.PopularMoviesController;
import com.example.popularmovies.model.MovieInfo;
import com.example.popularmovies.model.MovieReview;
import com.example.popularmovies.model.MovieVideo;

import java.io.IOException;
import java.util.List;

/**
 * Created by carvalhorr on 2/9/17.
 */

public class MovieInfoTasks {

    public static void retrieveMovieInfo(final Context context, final String movieId, final MovieInfoCallbacks callbacks) {
        new AsyncTask<String, Void, MovieInfo>() {

            @Override
            protected MovieInfo doInBackground(String... params) {
                PopularMoviesController controller = new PopularMoviesController(MainActivity.MOVIE_DB_API_KEY);
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
                callbacks.movieInfoLoaded(movieInfo);
            }

        }.execute(movieId);
    }

    public static void retrieveMovieVideos(final Context context, final String movieId, final MovieInfoCallbacks callbacks) {
        new AsyncTask<String, Void, List<MovieVideo>>() {

            @Override
            protected List<MovieVideo> doInBackground(String... params) {
                PopularMoviesController controller = new PopularMoviesController(MainActivity.MOVIE_DB_API_KEY);
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
                callbacks.movieVideosLoaded(videos);
            }

        }.execute(movieId);
    }

    public static void retrieveMovieReviews(final Context context, final String movieId, final MovieInfoCallbacks callbacks) {
        new AsyncTask<String, Void, List<MovieReview>>() {

            @Override
            protected List<MovieReview> doInBackground(String... params) {
                PopularMoviesController controller = new PopularMoviesController(MainActivity.MOVIE_DB_API_KEY);
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
                callbacks.movieReviewsLoaded(reviews);
            }

        }.execute(movieId);

    }

    public interface MovieInfoCallbacks {
        void movieInfoLoaded(MovieInfo movieInfo);
        void movieVideosLoaded(List<MovieVideo> videos);
        void movieReviewsLoaded(List<MovieReview> reviews);
    }
}
