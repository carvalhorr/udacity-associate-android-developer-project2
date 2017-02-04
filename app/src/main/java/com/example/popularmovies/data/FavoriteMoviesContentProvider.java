package com.example.popularmovies.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.example.popularmovies.data.database.FavoriteMoviesContract;
import com.example.popularmovies.data.database.FavoriteMoviesDatabaseManager;

/**
 * Created by carvalhorr on 2/3/17.
 */

public class FavoriteMoviesContentProvider extends ContentProvider {

    private static final int FAVORITE_MOVIES_PATH = 100;
    private static final int FAVORITE_MOVIES_ITEM_PATH = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private FavoriteMoviesDatabaseManager database;

    @Override
    public boolean onCreate() {
        // usually initialize the DBHelper here
        database = FavoriteMoviesDatabaseManager.getInstance(getContext());
        return true;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        final SQLiteDatabase db = database.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        Uri returnUri; // URI to be returned

        switch (match) {
            case FAVORITE_MOVIES_PATH: {
                long id = db.insert(FavoriteMoviesContract.FavoriteMovie.TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(FavoriteMoviesContract.FavoriteMovie.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase db = database.getReadableDatabase();

        int match = sUriMatcher.match(uri);
        Cursor retCursor;

        switch (match) {
            case FAVORITE_MOVIES_PATH: {
                retCursor = db.query(FavoriteMoviesContract.FavoriteMovie.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            }
            case FAVORITE_MOVIES_ITEM_PATH: {
                String id = uri.getPathSegments().get(1);
                retCursor = db.query(FavoriteMoviesContract.FavoriteMovie.TABLE_NAME,
                        null,
                        "_ID = ?",
                        new String[]{id},
                        null,
                        null,
                        null);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return retCursor;

    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = database.getWritableDatabase();

        int match = sUriMatcher.match(uri);

        int tasksDeleted = 0;

        switch (match) {
            case FAVORITE_MOVIES_PATH: {
                db.delete(
                        FavoriteMoviesContract.FavoriteMovie.TABLE_NAME,
                        selection,
                        selectionArgs);
                break;
            }
            case FAVORITE_MOVIES_ITEM_PATH: {
                String id = uri.getPathSegments().get(1);
                tasksDeleted = db.delete(
                        FavoriteMoviesContract.FavoriteMovie.TABLE_NAME,
                        "_id=?",
                        new String[]{id});
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (tasksDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return tasksDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        final SQLiteDatabase db = database.getWritableDatabase();

        int tasksUpdated;
        int match = sUriMatcher.match(uri);

        switch (match) {
            case FAVORITE_MOVIES_PATH: {
                tasksUpdated = db.update(
                        FavoriteMoviesContract.FavoriteMovie.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;
            }

            case FAVORITE_MOVIES_ITEM_PATH: {
                String id = uri.getPathSegments().get(1);
                tasksUpdated = db.update(
                        FavoriteMoviesContract.FavoriteMovie.TABLE_NAME,
                        values,
                        "_id=?",
                        new String[]{id});
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (tasksUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return tasksUpdated;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        final SQLiteDatabase db = database.getWritableDatabase();

        int match = sUriMatcher.match(uri);

        switch (match) {
            case FAVORITE_MOVIES_PATH: {
                db.beginTransaction();
                int rowsInserted = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(
                                FavoriteMoviesContract.FavoriteMovie.TABLE_NAME
                                , null, value);
                        if (_id != -1) {
                            rowsInserted++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }

                if (rowsInserted > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }

                return rowsInserted;
            }
            default:
                return super.bulkInsert(uri, values);
        }
    }

    @Override
    public String getType(@NonNull Uri uri) {
        int match = sUriMatcher.match(uri);
        switch (match) {
            case FAVORITE_MOVIES_PATH: {
                return "vnd.android.cursor.dir" + "/" +
                        FavoriteMoviesContract.AUTHORIRY + "/" +
                        FavoriteMoviesContract.FavoriteMovie.PATH;
            }
            case FAVORITE_MOVIES_ITEM_PATH: {
                return "vnd.android.cursor.item" + "/" +
                        FavoriteMoviesContract.AUTHORIRY + "/" +
                        FavoriteMoviesContract.FavoriteMovie.PATH;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    private static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(
                FavoriteMoviesContract.AUTHORIRY,
                FavoriteMoviesContract.FavoriteMovie.PATH,
                FAVORITE_MOVIES_PATH);
        uriMatcher.addURI(
                FavoriteMoviesContract.AUTHORIRY,
                FavoriteMoviesContract.FavoriteMovie.PATH + "/#",
                FAVORITE_MOVIES_ITEM_PATH);

        return uriMatcher;
    }
}
