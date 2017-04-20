package com.example.popularmovies.database.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.example.popularmovies.database.FavoriteMoviesContract;
import com.example.popularmovies.database.FavoriteMoviesDatabaseManager;
import com.example.popularmovies.injection.PopularMoviesApplication;

import javax.inject.Inject;

/**
 * Content provider for favorite movies
 *
 * Created by carvalhorr on 2/3/17.
 */

public class FavoriteMoviesContentProvider extends ContentProvider {

    // Constant used in the URI matcher to indicate a matched list of faovite movies URI
    private static final int FAVORITE_MOVIES_PATH = 100;

    // Constant used in the URI matcher to indicate a specific movie matched
    private static final int FAVORITE_MOVIES_ITEM_PATH = 101;

    // Variable to store the URI matcher
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    // Instance of the database manager
    @Inject
    public FavoriteMoviesDatabaseManager databaseManager;

    @Override
    public boolean onCreate() {

        return true;
    }

    private FavoriteMoviesDatabaseManager getDatabaseManager() {
        if (databaseManager == null) {
            PopularMoviesApplication.get(getContext()).getComponent().inject(this);
        }
        return databaseManager;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {

        // get a writable database
        final SQLiteDatabase writableDatabase = getDatabaseManager().getWritableDatabase();

        // check witch uri was called
        int match = sUriMatcher.match(uri);

        // URI to be returned in case of successfull insertion of a movie into favorites
        Uri returnUri;

        switch (match) {
            // only insert if the URI called is the list of all favorites.
            case FAVORITE_MOVIES_PATH: {

                // insert movie into the favorite list
                long id = writableDatabase.insert(FavoriteMoviesContract.FavoriteMovie.TABLE_NAME, null, values);

                // check if the insertion was successfull
                if (id > 0) {
                    // set the return uri in case it was successful
                    returnUri = ContentUris.withAppendedId(FavoriteMoviesContract.FavoriteMovie.CONTENT_URI, id);
                } else {
                    // throw exception otherwise
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;

            }
            // Throw exception in case the other URI was passed
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }

        // Notify registered observers that content has changed
        getContext().getContentResolver().notifyChange(uri, null);

        // Return the inserted favorite movie URI
        return returnUri;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        // get a readable database
        final SQLiteDatabase readableDatabase = getDatabaseManager().getReadableDatabase();

        // check which uri was called
        int match = sUriMatcher.match(uri);

        // Cursor with the list of movies to return
        Cursor retCursor;

        switch (match) {
            case FAVORITE_MOVIES_PATH: {
                // return list of all favorite movies
                retCursor = readableDatabase.query(FavoriteMoviesContract.FavoriteMovie.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            }
            case FAVORITE_MOVIES_ITEM_PATH: {
                // return specific movie
                String id = uri.getPathSegments().get(1);
                retCursor = readableDatabase.query(FavoriteMoviesContract.FavoriteMovie.TABLE_NAME,
                        null,
                        "_ID = ?",
                        new String[]{id},
                        null,
                        null,
                        null);
                break;
            }
            default:
                // if any other uri was called throw exception
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Return cursor with all favorite movies
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;

    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {

        // get a writable database
        final SQLiteDatabase writableDatabase = getDatabaseManager().getWritableDatabase();

        // check the uri that was called
        int match = sUriMatcher.match(uri);

        // number of favorite movies deleted
        int numberOfFavoriteMoviesDeleted = 0;

        switch (match) {
            case FAVORITE_MOVIES_PATH: {
                // delete all favorite movies
                writableDatabase.delete(
                        FavoriteMoviesContract.FavoriteMovie.TABLE_NAME,
                        selection,
                        selectionArgs);
                break;
            }
            case FAVORITE_MOVIES_ITEM_PATH: {
                // delete a specific favorite movie
                String id = uri.getPathSegments().get(1);
                numberOfFavoriteMoviesDeleted = writableDatabase.delete(
                        FavoriteMoviesContract.FavoriteMovie.TABLE_NAME,
                        "_id=?",
                        new String[]{id});
                break;
            }
            default:
                // if any other URI was called throw exception
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // notify registered observers if any favorite movie was deleted
        if (numberOfFavoriteMoviesDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // return number of favorite movies deleted
        return numberOfFavoriteMoviesDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        // get a writable database
        final SQLiteDatabase writableDatabase = getDatabaseManager().getWritableDatabase();

        // number of favorite movies updated
        int numberOfFavoriteMoviesUpdated = 0;

        // check which uri was called
        int match = sUriMatcher.match(uri);

        switch (match) {
            case FAVORITE_MOVIES_PATH: {
                // update all favorite movies
                numberOfFavoriteMoviesUpdated = writableDatabase.update(
                        FavoriteMoviesContract.FavoriteMovie.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;
            }

            case FAVORITE_MOVIES_ITEM_PATH: {
                // update a specific favorite movie
                String id = uri.getPathSegments().get(1);
                numberOfFavoriteMoviesUpdated = writableDatabase.update(
                        FavoriteMoviesContract.FavoriteMovie.TABLE_NAME,
                        values,
                        "_id=?",
                        new String[]{id});
                break;
            }
            default:
                // if any other uri was called throw an exception
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // notify any registered observer in case any favorite movie was updated
        if (numberOfFavoriteMoviesUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // return number of favorite movies updated
        return numberOfFavoriteMoviesUpdated;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {

        // get a writable database
        final SQLiteDatabase writableDatabase = getDatabaseManager().getWritableDatabase();

        // check which uri was called
        int match = sUriMatcher.match(uri);

        switch (match) {
            case FAVORITE_MOVIES_PATH: {
                // insert ifthe uri called is the list of favorite movies

                // begin transaction
                writableDatabase.beginTransaction();

                int numberOfFavoriteMoviesInserted = 0;
                try {

                    // iterate over list of favorite movies and insert each individually
                    for (ContentValues value : values) {
                        long _id = writableDatabase.insert(
                                FavoriteMoviesContract.FavoriteMovie.TABLE_NAME
                                , null, value);
                        if (_id != -1) {
                            numberOfFavoriteMoviesInserted++;
                        }
                    }
                    writableDatabase.setTransactionSuccessful();
                } finally {
                    writableDatabase.endTransaction();
                }

                // notify any registered observer in case any favorite movie was inserted
                if (numberOfFavoriteMoviesInserted > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }

                // return the number of favorite movies inserted
                return numberOfFavoriteMoviesInserted;
            }
            default:
                return super.bulkInsert(uri, values);
        }
    }

    @Override
    public String getType(@NonNull Uri uri) {

        // check the URI that was passed
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

    // build the URI matcher
    private static UriMatcher buildUriMatcher() {

        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        // add the pattern for the list of all favorites
        uriMatcher.addURI(
                FavoriteMoviesContract.AUTHORIRY,
                FavoriteMoviesContract.FavoriteMovie.PATH,
                FAVORITE_MOVIES_PATH);

        // add the pattern for an favorite movie item
        uriMatcher.addURI(
                FavoriteMoviesContract.AUTHORIRY,
                FavoriteMoviesContract.FavoriteMovie.PATH + "/#",
                FAVORITE_MOVIES_ITEM_PATH);

        return uriMatcher;
    }
}
