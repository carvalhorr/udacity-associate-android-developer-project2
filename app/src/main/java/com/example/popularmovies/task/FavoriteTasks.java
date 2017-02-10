package com.example.popularmovies.task;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Movie;
import android.net.Uri;
import android.os.AsyncTask;

import com.example.popularmovies.data.database.FavoriteMoviesContract;
import com.example.popularmovies.model.MovieInfo;

/**
 * Created by carvalhorr on 2/9/17.
 */

public class FavoriteTasks {

    public static void addToFavoriteTask(final Context context, final MovieInfo movieInfo, final FavoriteCallbacks callbacks) {
        new AsyncTask<MovieInfo, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(MovieInfo... params) {

                MovieInfo movieInfo = params[0];

                ContentValues contentValues = new ContentValues();
                contentValues.put(FavoriteMoviesContract.FavoriteMovie._ID, movieInfo.getMovieId());
                contentValues.put(FavoriteMoviesContract.FavoriteMovie.COLUMN_MOVIE_TITLE, movieInfo.getTitle());
                contentValues.put(FavoriteMoviesContract.FavoriteMovie.COLUMN_MOVIE_POSTER, movieInfo.getPosterPath());

                Uri uri = context.getContentResolver().insert(
                        FavoriteMoviesContract.FavoriteMovie.CONTENT_URI, contentValues);
                return uri != null && uri.getLastPathSegment().equals(movieInfo.getMovieId());
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

                int count = context.getContentResolver().delete(
                        FavoriteMoviesContract.FavoriteMovie.CONTENT_URI.buildUpon().appendEncodedPath(movieId).build(),
                        null,
                        null);
                return count == 1;
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

                Cursor cursor = context.getContentResolver().query(
                        FavoriteMoviesContract.FavoriteMovie.CONTENT_URI.buildUpon().appendPath(movieId).build(),
                        null,
                        null,
                        null,
                        null);
                return cursor.getCount() > 0;
            }

            @Override
            protected void onPostExecute(Boolean isFavorite) {
                super.onPostExecute(isFavorite);
                callbacks.isFavorite(isFavorite);
            }

        }.execute(movieId);

    }

    public interface FavoriteCallbacks {
        void addedToFavorite(String movieId);
        void removedFromFavorite(String movieId);
        void isFavorite(boolean isFavorite);
    }
}
