package com.example.popularmovies;

import com.example.popularmovies.control.PopularMoviesController;
import com.example.popularmovies.model.MovieInfo;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class PopularMoviesControllerTest {

    @Test
    public void getPopularMoviesTest() throws IOException {
        PopularMoviesController controller = new PopularMoviesController(MovieGridFragment.MOVIE_DB_API_KEY);
        List<MovieInfo> mMovieList = controller.getPopularMovies();
        assertTrue(mMovieList.size() > 0);
    }


}