package com.example.popularmovies.task;

import android.content.Context;
import android.os.AsyncTask;

import com.example.popularmovies.control.FavoriteController;
import com.example.popularmovies.model.MovieInfo;

import java.util.List;

/**
 *
 * AsyncTasks to perform database functions related to favorite movies. These methods should be called
 * from the UI in order to execute in a separate thread from the main UI thread.
 *
 * Created by carvalhorr on 2/9/17.
 */

public class FavoriteTasks {

    /**
     * Add a new movie to favorites using an AsyncTask.
     *
     * @param context
     * @param movieInfo
     * @param callbacks
     */
    public static void addToFavoriteTask(final Context context, final MovieInfo movieInfo, final FavoriteCallbacks callbacks) {
        new AsyncTask<MovieInfo, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(MovieInfo... params) {
                // Call the controller method to add the movie to favorites
                FavoriteController controller = new FavoriteController();
                return controller.addToFavoriteTask(context, movieInfo);
            }

            @Override
            protected void onPostExecute(Boolean isFavorite) {
                super.onPostExecute(isFavorite);

                // Notify the caller that the movie was added to favorites
                callbacks.addedToFavorite(movieInfo.getMovieId());
            }

        }.execute(movieInfo);
    }


    /**
     * Remove a movie from favorites using an AsyncTask.
     * @param context
     * @param movieId
     * @param callbacks
     */
    public static void removeFromFavoriteTask(final Context context, final String movieId, final FavoriteCallbacks callbacks) {

        new AsyncTask<String, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(String... params) {
                // Call the controller to remove the movie from favorites
                FavoriteController controller = new FavoriteController();
                return controller.removeFromFavoriteTask(context, movieId);
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);

                // Notify the caller that the movie was removed from favorites
                callbacks.removedFromFavorite(movieId);
            }

        }.execute(movieId);
    }

    /**
     * Verify if a movie was added to favorites using an AsyncTask
     * @param context
     * @param movieId
     * @param callbacks
     */
    public static void isFavorite(final Context context, final String movieId, final FavoriteCallbacks callbacks) {
        new AsyncTask<String, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(String... params) {
                // Call the controller to verify if the movie is part of favorites
                FavoriteController controller = new FavoriteController();
                return controller.isFavorite(context, movieId);
            }

            @Override
            protected void onPostExecute(Boolean isFavorite) {
                super.onPostExecute(isFavorite);

                // Notify the caller whether the movie is favorite or not
                callbacks.isFavorite(isFavorite);
            }

        }.execute(movieId);
    }

    /**
     * Load the list of favorite movies using an AsyncTask
     * @param context
     * @param callbacks
     */
    public static void loadFavoritesAsync(final Context context, final FavoriteCallbacks callbacks) {
        new AsyncTask<String, Void, List<MovieInfo>>() {

            @Override
            protected List<MovieInfo> doInBackground(String... params) {
                // Call the controller to get the list of favorite movies
                FavoriteController controller = new FavoriteController();
                return controller.getFavorites(context);
            }

            @Override
            protected void onPostExecute(List<MovieInfo> favorites) {
                super.onPostExecute(favorites);

                // Send the list of favorite movies to the caller
                callbacks.favoritesLoaded(favorites);
            }

        }.execute();
    }

    /**
     * Interface that any class calling the methods defined in this class need to
     * implement in order to get the responses to the called methods.
     */
    public interface FavoriteCallbacks {
        /**
         * Notify that a movie was added to favorites.
         * @param movieId id of the movie added to favorites
         */
        void addedToFavorite(String movieId);

        /**
         * Notify that a movie was removed from favorites
         * @param movieId id of the movie removed from favorites
         */
        void removedFromFavorite(String movieId);

        /**
         * Notify wheather a movie is favorite or not
         * @param isFavorite indicate whether the movie is favorite or not
         */
        void isFavorite(boolean isFavorite);

        /**
         * Return the list of favorite movies stored on the device
         * @param favorites list of favorite movies
         */
        void favoritesLoaded(List<MovieInfo> favorites);
    }
}
