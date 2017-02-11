package com.example.popularmovies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.popularmovies.model.MovieInfo;

import java.util.List;

/**
 * Created by carvalhorr on 2/11/17.
 */

public class MovieGridFragment extends Fragment {

    private RecyclerView mMovieGridRecyclerView;
    private TextView mErrorMessageView;
    private TextView mCurrentlyDisplayingTextView;
    private ProgressBar mLoadingIndicator;

    private PopularMoviesAdapter mPopularMoviesAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_movie_grid, container, false);
        mMovieGridRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_movies_grid);
        setupMovieGridRecyclerView();

        //mLoadingIndicator = (ProgressBar) rootView.findViewById(R.id.pb_loading_indicator);
        //mErrorMessageView = (TextView) rootView.findViewById(R.id.tv_error_message);
        //mCurrentlyDisplayingTextView = (TextView) rootView.findViewById(R.id.tv_currently_displaying);

        return rootView;
    }

    private void setupMovieGridRecyclerView() {
        int spanCount = getResources().getInteger(R.integer.column_count);
        boolean reverseLayout = false;

        GridLayoutManager movieGridLayoutManager =
                new GridLayoutManager(getContext(), spanCount, GridLayoutManager.VERTICAL, reverseLayout);
        mMovieGridRecyclerView.setLayoutManager(movieGridLayoutManager);
        mMovieGridRecyclerView.setAdapter(mPopularMoviesAdapter);
    }

    public void setPopularMovieAdapter(PopularMoviesAdapter adapter) {
        mPopularMoviesAdapter = adapter;
    }

    public void setMovieList(List<MovieInfo> data) {
        mPopularMoviesAdapter.setMovieInfoData(data);
    }
}
