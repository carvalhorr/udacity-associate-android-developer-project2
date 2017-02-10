package com.example.popularmovies.task;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import com.example.popularmovies.MainActivity;
import com.example.popularmovies.control.PopularMoviesController;
import com.example.popularmovies.data.database.FavoriteMoviesContract;
import com.example.popularmovies.model.MovieInfo;

import java.io.IOException;

/**
 * Created by carvalhorr on 2/9/17.
 */

public class MovieInfoTasks {

    public static void retrieveMovieInfo(final Context context, final String movieId, final MovieInfoCallbacks callbacks) {
        new AsyncTask<String, Void, MovieInfo>() {

            @Override
            protected MovieInfo doInBackground(String... params) {
                PopularMoviesController controller = new PopularMoviesController(MainActivity.MOVIE_DB_API_KEY);
                try {
                    return controller.getMovieInfo(movieId);
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(MovieInfo movieInfo) {
                super.onPostExecute(movieInfo);
                callbacks.movieInfoLoaded(movieInfo);
            }

        }.execute(movieId);
    }

    public interface MovieInfoCallbacks {
        void movieInfoLoaded(MovieInfo movieInfo);
    }
}
