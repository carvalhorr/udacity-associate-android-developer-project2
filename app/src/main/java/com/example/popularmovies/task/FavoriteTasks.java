package com.example.popularmovies.task;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Movie;
import android.net.Uri;
import android.os.AsyncTask;

import com.example.popularmovies.control.FavoriteController;
import com.example.popularmovies.data.database.FavoriteMoviesContract;
import com.example.popularmovies.model.MovieInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by carvalhorr on 2/9/17.
 */

public class FavoriteTasks {

    public static void addToFavoriteTask(final Context context, final MovieInfo movieInfo, final FavoriteCallbacks callbacks) {
        new AsyncTask<MovieInfo, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(MovieInfo... params) {
                FavoriteController controller = new FavoriteController();
                return controller.addToFavoriteTask(context, movieInfo);
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                callbacks.addedToFavorite(movieInfo.getMovieId());
            }

        }.execute(movieInfo);
    }

    public static void removeFromFavoriteTask(final Context context, final String movieId, final FavoriteCallbacks callbacks) {

        new AsyncTask<String, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(String... params) {
                FavoriteController controller = new FavoriteController();
                return controller.removeFromFavoriteTask(context, movieId);
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                callbacks.removedFromFavorite(movieId);
            }

        }.execute(movieId);
    }

    public static void isFavorite(final Context context, final String movieId, final FavoriteCallbacks callbacks) {
        new AsyncTask<String, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(String... params) {
                FavoriteController controller = new FavoriteController();
                return controller.isFavorite(context, movieId);
            }

            @Override
            protected void onPostExecute(Boolean isFavorite) {
                super.onPostExecute(isFavorite);
                callbacks.isFavorite(isFavorite);
            }

        }.execute(movieId);
    }

    public static void loadFavorites(final Context context, final FavoriteCallbacks callbacks) {
        new AsyncTask<String, Void, List<MovieInfo>>() {

            @Override
            protected List<MovieInfo> doInBackground(String... params) {
                FavoriteController controller = new FavoriteController();
                return controller.getFavorites(context);
            }

            @Override
            protected void onPostExecute(List<MovieInfo> favorites) {
                super.onPostExecute(favorites);
                callbacks.favoritesLoaded(favorites);
            }

        }.execute();
    }

    public interface FavoriteCallbacks {
        void addedToFavorite(String movieId);
        void removedFromFavorite(String movieId);
        void isFavorite(boolean isFavorite);
        void favoritesLoaded(List<MovieInfo> favorites);
    }
}
