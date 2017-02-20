package com.example.popularmovies.database;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by carvalhorr on 2/1/17.
 */

public class FavoriteMoviesContract {

    public static final String AUTHORIRY = "com.example.popularmovies";

    public static final Uri BASE_URI = Uri.parse("content://" + AUTHORIRY);

    private FavoriteMoviesContract() {}

    public interface FavoriteMovie extends BaseColumns {
        public static final String TABLE_NAME = "favorite_movie";

        // The movie id will be stored in the _ID column
        public static final String COLUMN_MOVIE_TITLE = "title";
        public static final String COLUMN_MOVIE_POSTER = "poster";

        public static final String CREATE_STATEMENT =
                "CREATE TABLE " + TABLE_NAME + "("
                    + _ID + " NUMERIC NOT NULL UNIQUE, "
                    + COLUMN_MOVIE_TITLE + " TEXT NOT NULL, "
                    + COLUMN_MOVIE_POSTER + " TEXT NOT NULL);";

        public static final String PATH = "favorite_movies";
        public static final Uri CONTENT_URI = BASE_URI.buildUpon().appendPath(PATH).build();
    }
}
