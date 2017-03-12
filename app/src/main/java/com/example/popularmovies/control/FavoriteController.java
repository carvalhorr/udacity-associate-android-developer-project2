package com.example.popularmovies.control;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.popularmovies.database.FavoriteMoviesContract;
import com.example.popularmovies.model.MovieInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Control for adding/removing movies to favorite in the local database
 *
 * Created by carvalhorr on 2/11/17.
 */

public class FavoriteController {


    public Boolean addToFavorite(Context context, MovieInfo movieInfo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(FavoriteMoviesContract.FavoriteMovie._ID, movieInfo.getMovieId());
        contentValues.put(FavoriteMoviesContract.FavoriteMovie.COLUMN_MOVIE_TITLE, movieInfo.getTitle());
        contentValues.put(FavoriteMoviesContract.FavoriteMovie.COLUMN_MOVIE_POSTER, movieInfo.getPosterPath());

        Uri uri = context.getContentResolver().insert(
                FavoriteMoviesContract.FavoriteMovie.CONTENT_URI, contentValues);
        return uri != null && uri.getLastPathSegment().equals(movieInfo.getMovieId());
    }

    public Boolean removeFromFavorite(Context context, String movieId) {
        int count = context.getContentResolver().delete(
                FavoriteMoviesContract.FavoriteMovie.CONTENT_URI.buildUpon().appendEncodedPath(movieId).build(),
                null,
                null);
        return count == 1;
    }

    public Boolean isFavorite(Context context, String movieId) {
        Cursor cursor = context.getContentResolver().query(
                FavoriteMoviesContract.FavoriteMovie.CONTENT_URI.buildUpon().appendPath(movieId).build(),
                null,
                null,
                null,
                null);
        return cursor.getCount() > 0;
    }

    public List<MovieInfo> getFavorites(Context context) {
        List<MovieInfo> favorites = new ArrayList<MovieInfo>();
        Cursor cursor = context.getContentResolver().query(
                FavoriteMoviesContract.FavoriteMovie.CONTENT_URI,
                null,
                null,
                null,
                null);
        cursor.moveToFirst();
        int idIndex = cursor.getColumnIndex(FavoriteMoviesContract.FavoriteMovie._ID);
        int titleIndex = cursor.getColumnIndex(FavoriteMoviesContract.FavoriteMovie.COLUMN_MOVIE_TITLE);
        int posterIndex = cursor.getColumnIndex(FavoriteMoviesContract.FavoriteMovie.COLUMN_MOVIE_POSTER);
        for(int i = 0; i <cursor.getCount(); i++) {
            MovieInfo movieInfo = new MovieInfo();
            movieInfo.setMovieId(cursor.getString(idIndex));
            movieInfo.setTitle(cursor.getString(titleIndex));
            movieInfo.setPosterPath(cursor.getString(posterIndex));
            favorites.add(movieInfo);
            cursor.moveToNext();
        }
        return favorites;
    }

}


