package com.example.project1.popularmoviesstage1.data.database;

import android.content.Context;
import android.database.Cursor;
import android.support.test.InstrumentationRegistry;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by carvalhorr on 2/3/17.
 */

public class FavoriteovieDBHelperTest {


    private static Context context = null;
    private static FavoriteMoviesDatabaseManager database = null;

    @BeforeClass
    public static void setupContext() {
        context = InstrumentationRegistry.getTargetContext();
        database = FavoriteMoviesDatabaseManager.getInstance(context);
    }

    @Test
    public void testDatabaseCreation() {
        assertEquals(1, database.getWritableDatabase().getVersion());
    }

    @Test
    public void testFavoriteMovieInsertion() {

        long id = 123;
        String title = "A movie";
        String poster = "poster_file_name.jpg";

        database.insertFavorite(id, title, poster);

        verifyFavoriteValues(id, title, poster);
    }

    @Test
    public void testFavoriteMovieDeletion() {
        long id = 124;
        String title = "A movie";
        String poster = "poster_file_name.jpg";

        database.insertFavorite(id, title, poster);
        verifyFavoriteValues(id, title, poster);

        database.deleteFavorite(id);

        Cursor cursor = database.queryFavoriteById(id);
        assertEquals("Delete did not work", 0, cursor.getCount());
    }

    @Test
    public void testFavoriteMovieUpdate() {
        long id = 125;
        database.insertFavorite(id, "title", "poster");

        String newTitle = "new title";
        String newPoster = "new poster";

        database.updateFavorite(id, newTitle, newPoster);

        verifyFavoriteValues(id, newTitle, newPoster);
    }

    private void verifyFavoriteValues(long id, String title, String poster) {
        Cursor cursor = database.queryFavoriteById(id);
        assertEquals(1, cursor.getCount());
        cursor.moveToFirst();
        assertEquals("Wrong id for favorite movie", id, cursor.getLong(0));
        assertEquals("Wrong title for favorite movie", title, cursor.getString(1));
        assertEquals("Wrong poster for favorite movie", poster, cursor.getString(2));
    }


    @AfterClass
    public static void deleteAll() {
        database.getWritableDatabase().delete(FavoriteMoviesContract.FavoriteMovie.TABLE_NAME, null, null);
    }

}
