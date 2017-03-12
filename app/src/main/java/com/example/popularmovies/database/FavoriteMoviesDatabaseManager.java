package com.example.popularmovies.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * This class was created as a singleton and with synchronized methods to allow
 * simultaneous access from multiple threads.
 *
 * The DatabaseManager was made a singleton instead of the DBHelper in order to control
 * access to the getWritableDatabase and getReadableDatabase methods.
 *
 * Created by carvalhorr on 2/3/17.
 */

public class FavoriteMoviesDatabaseManager {

    // Singleton instance
    private static FavoriteMoviesDatabaseManager instance = null;

    // Single instance of the sql lite helper
    private static SQLiteOpenHelper sqLiteOpenHelperInstance = null;

    // Singleton's method to get the instance
    public static synchronized FavoriteMoviesDatabaseManager getInstance(Context context) {
        if (instance == null) {
            instance = new FavoriteMoviesDatabaseManager();
            sqLiteOpenHelperInstance = instance.new FavoriteMoviesDBHelper(context);
        }
        return instance;
    }

    // A writable instance of the database can only be obtained through this method.
    // It is syncronyed to make simultaneous access to the database threadsafe.
    public synchronized SQLiteDatabase getWritableDatabase() {
        return sqLiteOpenHelperInstance.getWritableDatabase();
    }

    // A readable instance of the database can only be obtained through this method.
    public synchronized SQLiteDatabase getReadableDatabase() {
        return sqLiteOpenHelperInstance.getReadableDatabase();
    }

    // Insert a favorite into the database
    // this method should not be called since a content provider is being used
    @Deprecated
    public long insertFavorite(long id, String title, String poster) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = getContentValues(id, title, poster);
        long rowId = database.insert(
                FavoriteMoviesContract.FavoriteMovie.TABLE_NAME, null, contentValues);
        database.close();
        return rowId;
    }

    // Update a favorite movie in the database
    // This method should not be called since a content provider is being used
    @Deprecated
    public void updateFavorite(long id, String title, String poster) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = getContentValues(id, title, poster);
        database.update(FavoriteMoviesContract.FavoriteMovie.TABLE_NAME,
                contentValues, "_id = ?", new String[]{String.valueOf(id)});
        database.close();
    }

    // Delete a favorite from the database
    // This method should not be called since a content provider is being used
    @Deprecated
    public void deleteFavorite(long id) {
        SQLiteDatabase database = getWritableDatabase();
        database.delete(FavoriteMoviesContract.FavoriteMovie.TABLE_NAME, "_id = ?", new String[]{String.valueOf(id)});
        database.close();
    }

    // Query a favorite movie by id
    // This method should not be called since a content provider is being used
    @Deprecated
    public Cursor queryFavoriteById(long id) {
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor =  database.query(FavoriteMoviesContract.FavoriteMovie.TABLE_NAME
                , null, "_id = ?", new String[]{String.valueOf(id)}, null, null, null);
        return cursor;
    }

    // Helper method to put the favorite movie parameters into a ContentValues to be used in the database
    private ContentValues getContentValues(long id, String title, String poster) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(FavoriteMoviesContract.FavoriteMovie._ID, id);
        contentValues.put(FavoriteMoviesContract.FavoriteMovie.COLUMN_MOVIE_TITLE, title);
        contentValues.put(FavoriteMoviesContract.FavoriteMovie.COLUMN_MOVIE_POSTER, poster);
        return contentValues;
    }

    // DB helper for the favorite movies database
    private class FavoriteMoviesDBHelper extends SQLiteOpenHelper {

        // Database version
        public static final int DATABASE_VERSION = 1;

        // Database file name
        public static final String DATABASE_NAME = "FavoriteMovies.db";

        public FavoriteMoviesDBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        // Create the tables in the database
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(FavoriteMoviesContract.FavoriteMovie.CREATE_STATEMENT);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // to be implemented when a different version of the database is created
        }

        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

}