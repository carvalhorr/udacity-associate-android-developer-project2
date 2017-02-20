package com.example.popularmovies.database;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by carvalhorr on 2/4/17.
 */

public class FavoriteMoviesContentProviderTest {

    private static Context context = null;

    @BeforeClass
    public static void setupContext() {
        context = InstrumentationRegistry.getTargetContext();
    }

    @AfterClass
    public static void deleteAll() {
        context.getContentResolver().delete(
                FavoriteMoviesContract.FavoriteMovie.CONTENT_URI,
                null,
                null);
    }

    @Test
    public void queryFavoriteMovieTest() {
        long id = 212;
        String title = "title";
        String poster = "poster.jpg";

        ContentValues contentValues = createContentValues(id, title, poster);

        context.getContentResolver().insert(
                FavoriteMoviesContract.FavoriteMovie.CONTENT_URI,
                contentValues);

        Cursor cursor = context.getContentResolver().query(
                FavoriteMoviesContract.FavoriteMovie.CONTENT_URI,
                null,
                null,
                null,
                null);
        assertTrue(cursor.getCount() > 0);
    }

    @Test
    public void insertFavoriteMovieTest() {

        long id = 213;
        String title = "title";
        String poster = "poster.jpg";

        ContentValues contentValues = createContentValues(id, title, poster);

        context.getContentResolver().insert(
                FavoriteMoviesContract.FavoriteMovie.CONTENT_URI,
                contentValues);

        verifyFavoriteValues(id, title, poster);

    }

    @Test
    public void deleteFavoriteMovieTest() {
        long id = 214;
        String title = "title";
        String poster = "poster.jpg";

        ContentValues contentValues = createContentValues(id, title, poster);

        context.getContentResolver().insert(
                FavoriteMoviesContract.FavoriteMovie.CONTENT_URI,
                contentValues);

        verifyFavoriteValues(id, title, poster);

        context.getContentResolver().delete(
                FavoriteMoviesContract.FavoriteMovie.CONTENT_URI.buildUpon().appendEncodedPath(String.valueOf(id)).build(),
                null,
                null);

        Cursor cursor = context.getContentResolver().query(
                FavoriteMoviesContract.FavoriteMovie.CONTENT_URI.buildUpon().appendEncodedPath(String.valueOf(id)).build(),
                null,
                null,
                null,
                null);
        assertEquals(0, cursor.getCount());
    }

    @Test
    public void updateFavoriteMovieTest() {
        long id = 215;
        String title = "title";
        String poster = "poster.jpg";

        ContentValues contentValues = createContentValues(id, title, poster);

        context.getContentResolver().insert(
                FavoriteMoviesContract.FavoriteMovie.CONTENT_URI,
                contentValues);
        verifyFavoriteValues(id, title, poster);

        String newTitle = "another title";
        String newPoster = "another poster";

        contentValues = createContentValues(id, newTitle, newPoster);
        context.getContentResolver().update(
                FavoriteMoviesContract.FavoriteMovie.CONTENT_URI.buildUpon().appendEncodedPath(String.valueOf(id)).build(),
                contentValues,
                null,
                null);
        verifyFavoriteValues(id, newTitle, newPoster);
    }

    private void verifyFavoriteValues(long id, String title, String poster) {
        Cursor cursor = context.getContentResolver().query(
                FavoriteMoviesContract.FavoriteMovie.CONTENT_URI.buildUpon().appendPath(String.valueOf(id)).build(),
                null,
                null,
                null,
                null);
        assertEquals(1, cursor.getCount());
        cursor.moveToFirst();
        assertEquals("Wrong id for favorite movie", id, cursor.getLong(0));
        assertEquals("Wrong title for favorite movie", title, cursor.getString(1));
        assertEquals("Wrong poster for favorite movie", poster, cursor.getString(2));
    }


    private ContentValues createContentValues(long id, String title, String poster) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(FavoriteMoviesContract.FavoriteMovie._ID, id);
        contentValues.put(FavoriteMoviesContract.FavoriteMovie.COLUMN_MOVIE_TITLE, title);
        contentValues.put(FavoriteMoviesContract.FavoriteMovie.COLUMN_MOVIE_POSTER, poster);
        return contentValues;
    }


    public void readDataFromcontentProvider() {
        Uri mUri = Uri.parse("content://authority/table");

        ContentResolver resolver = context.getContentResolver();

        // query data - parameters are like the ones from database
        Cursor cursor = resolver.query(mUri, null, null, null, null);
    }

    public void insertDataUsingContentProvider() {
        Uri mUri = Uri.parse("content://authority/table");

        Uri mNewUri;

        ContentValues mNewValues = new ContentValues();
        mNewValues.put("column1", "value1");
        mNewValues.put("column2", "value2");
        mNewValues.put("column3", "value3");

        mNewUri = context.getContentResolver().insert(
                mUri,   // the user dictionary content URI
                mNewValues                          // the values to insert
        );

        // should return something like:
        // content://authority/table/<new_id>
    }

    public void deleteDataUsingContentProvider() {
        Uri mUri = Uri.parse("content://authority/table");
        String mSelectionClause = "column1 LIKE ?";
        String[] mSelectionArgs = {"criteria value"};

        int mRowsDeleted = 0;

        mRowsDeleted = context.getContentResolver().delete(
                mUri,                               // the user dictionary content URI
                mSelectionClause,                   // the column to select on
                mSelectionArgs                      // the value to compare to
        );
    }

    public void updateDataUsingContentProvider() {
        Uri mUri = Uri.parse("content://authority/table");


        String mSelectionClause = "column1 LIKE ?";
        String[] mSelectionArgs = {"criteria value"};

        int mRowsUpdated = 0;

        ContentValues mUpdateValues = new ContentValues();
        mUpdateValues.putNull("column1");
        mUpdateValues.put("column2", "value");

        mRowsUpdated = context.getContentResolver().update(
                mUri,                               // the user dictionary content URI
                mUpdateValues,                       // the columns to update
                mSelectionClause,                    // the column to select on
                mSelectionArgs                      // the value to compare to
        );
    }
}
