package com.example.popularmovies;

import com.example.popularmovies.control.PopularMoviesController;
import com.example.popularmovies.model.MovieInfo;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class PopularMoviesControllerTest {

    private PopularMoviesController controller;

    private PopularMoviesController.ListMoviesResponseCallback mCallback =
            new PopularMoviesController.ListMoviesResponseCallback()
            {

                private boolean finished = false;
                private boolean failure = false;
                private List<MovieInfo> mMovieList = null;

                @Override
                public synchronized void movieListRetrieved(List<MovieInfo> movieList) {
                    finished = true;
                    mMovieList = movieList;
                }

                @Override
                public synchronized void movieListRetrievalFailure() {
                    failure = true;
                    finished = true;
                }

                @Override
                public synchronized boolean isFinished(){
                    return finished;
                }

                @Override
                public synchronized boolean isFailure() {
                    return failure;
                }

                @Override
                public synchronized List<MovieInfo> getMovieList() {
                    return mMovieList;
                }

            };


    @Test
    public void getPopularMoviesTest() {
        controller = new PopularMoviesController(MovieGridFragment.MOVIE_DB_API_KEY, mCallback);
        controller.getPopularMoviesAsync();
        // wait for the assyncronous call to finish
        while (!mCallback.isFinished()) {

        }
        System.out.println(mCallback.getMovieList().toString());
        assertEquals(mCallback.isFailure(), false);
    }


}