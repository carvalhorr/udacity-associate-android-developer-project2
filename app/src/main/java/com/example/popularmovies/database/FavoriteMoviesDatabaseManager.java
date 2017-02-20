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
 * Created by carvalhorr on 2/3/17.
 */

public class FavoriteMoviesDatabaseManager {

    private static FavoriteMoviesDatabaseManager instance = null;
    private static SQLiteOpenHelper sqLiteOpenHelperInstance = null;


    public static synchronized FavoriteMoviesDatabaseManager getInstance(Context context) {
        if (instance == null) {
            instance = new FavoriteMoviesDatabaseManager();
            sqLiteOpenHelperInstance = instance.new FavoriteMoviesDBHelper(context);
        }
        return instance;
    }

    // An instance of the database can only be obtained through this method which is in a singleton.
    // It is syncronyed to make simultaneous access to the database threadsafe.
    public synchronized SQLiteDatabase getWritableDatabase() {
        return sqLiteOpenHelperInstance.getWritableDatabase();
    }

    public synchronized SQLiteDatabase getReadableDatabase() {
        return sqLiteOpenHelperInstance.getReadableDatabase();
    }

    public long insertFavorite(long id, String title, String poster) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = getContentValues(id, title, poster);
        long rowId = database.insert(
                FavoriteMoviesContract.FavoriteMovie.TABLE_NAME, null, contentValues);
        database.close();
        return rowId;
    }

    public void updateFavorite(long id, String title, String poster) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = getContentValues(id, title, poster);
        database.update(FavoriteMoviesContract.FavoriteMovie.TABLE_NAME,
                contentValues, "_id = ?", new String[]{String.valueOf(id)});
        database.close();
    }

    public void deleteFavorite(long id) {
        SQLiteDatabase database = getWritableDatabase();
        database.delete(FavoriteMoviesContract.FavoriteMovie.TABLE_NAME, "_id = ?", new String[]{String.valueOf(id)});
        database.close();
    }

    public Cursor queryFavoriteById(long id) {
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor =  database.query(FavoriteMoviesContract.FavoriteMovie.TABLE_NAME
                , null, "_id = ?", new String[]{String.valueOf(id)}, null, null, null);
        return cursor;
    }

    private ContentValues getContentValues(long id, String title, String poster) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(FavoriteMoviesContract.FavoriteMovie._ID, id);
        contentValues.put(FavoriteMoviesContract.FavoriteMovie.COLUMN_MOVIE_TITLE, title);
        contentValues.put(FavoriteMoviesContract.FavoriteMovie.COLUMN_MOVIE_POSTER, poster);
        return contentValues;
    }

    private class FavoriteMoviesDBHelper extends SQLiteOpenHelper {

        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "FavoriteMovies.db";

        public FavoriteMoviesDBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(FavoriteMoviesContract.FavoriteMovie.CREATE_STATEMENT);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

}