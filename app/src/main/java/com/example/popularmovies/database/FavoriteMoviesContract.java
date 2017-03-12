package com.example.popularmovies.database;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Favorite movies database contract information.
 *
 * Created by carvalhorr on 2/1/17.
 */

public class FavoriteMoviesContract {

    // Authority used by the content provider that gives access to the database
    public static final String AUTHORIRY = "com.example.popularmovies";

    // Base URI for the content stored in the database (to be used with content providers)
    public static final Uri BASE_URI = Uri.parse("content://" + AUTHORIRY);

    private FavoriteMoviesContract() {}

    /**
     * Contract information about the favorite movies table stored in the database
     */
    public interface FavoriteMovie extends BaseColumns {

        // Favorite movies table name
        public static final String TABLE_NAME = "favorite_movie";

        // The movie id will be stored in the _ID column provided by BaseColumns

        // Name of the field for the movie title in the table
        public static final String COLUMN_MOVIE_TITLE = "title";

        // Name of the field for the poster image file name in the table
        public static final String COLUMN_MOVIE_POSTER = "poster";

        // SQL create table statement for the favorite movies
        public static final String CREATE_STATEMENT =
                "CREATE TABLE " + TABLE_NAME + "("
                    + _ID + " NUMERIC NOT NULL UNIQUE, "
                    + COLUMN_MOVIE_TITLE + " TEXT NOT NULL, "
                    + COLUMN_MOVIE_POSTER + " TEXT NOT NULL);";

        // Path for the favorite movie in the content provider
        public static final String PATH = "favorite_movies";

        // URI for the favorite movies in the content provider
        public static final Uri CONTENT_URI = BASE_URI.buildUpon().appendPath(PATH).build();
    }
}
