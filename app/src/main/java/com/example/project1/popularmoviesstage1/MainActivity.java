package com.example.project1.popularmoviesstage1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.project1.popularmoviesstage1.model.MovieInfo;

import java.util.ArrayList;
import java.util.List;

public class MainActivity
        extends AppCompatActivity
        implements PopularMoviesAdapter.MovieOnClickHandler {

    private RecyclerView mMovieGridRecyclerView;

    private PopularMoviesAdapter mPopularMoviesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_grid);

        mMovieGridRecyclerView = (RecyclerView) findViewById(R.id.rv_movies_grid);
        setupMovieGridRecyclerView();

        startFetchingMovieData();
    }

    private void setupMovieGridRecyclerView() {

        int spanCount = 2;
        MainActivity context = this;
        boolean reverseLayout = false;

        GridLayoutManager movieGridLayoutManager =
                new GridLayoutManager(context, spanCount, LinearLayoutManager.VERTICAL, reverseLayout);
        mMovieGridRecyclerView.setLayoutManager(movieGridLayoutManager);

        mPopularMoviesAdapter = new PopularMoviesAdapter(this);
        mMovieGridRecyclerView.setAdapter(mPopularMoviesAdapter);
    }

    private void startFetchingMovieData() {
        //TODO replace with code to retrieve info from internet
        List<MovieInfo> movieInfoList = new ArrayList<MovieInfo>();
        movieInfoList.add(new MovieInfo("1", "A title", "http://image.tmdb.org/t/p/original//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg"));
        movieInfoList.add(new MovieInfo("2", "A title", "http://image.tmdb.org/t/p/original//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg"));
        movieInfoList.add(new MovieInfo("1", "A title", "http://image.tmdb.org/t/p/original//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg"));
        movieInfoList.add(new MovieInfo("2", "A title", "http://image.tmdb.org/t/p/original//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg"));
        movieInfoList.add(new MovieInfo("1", "A title", "http://image.tmdb.org/t/p/original//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg"));
        movieInfoList.add(new MovieInfo("2", "A title", "http://image.tmdb.org/t/p/original//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg"));
        movieInfoList.add(new MovieInfo("1", "A title", "http://image.tmdb.org/t/p/original//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg"));
        movieInfoList.add(new MovieInfo("2", "A title", "http://image.tmdb.org/t/p/original//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg"));
        mPopularMoviesAdapter.setMovieInfoData(movieInfoList);
    }

    @Override
    public void onClick(String movieId) {
        Toast.makeText(this, "Movie " + movieId + " clicked.", Toast.LENGTH_LONG).show();
    }
}
