package com.example.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.popularmovies.control.PopularMoviesController;
import com.example.popularmovies.model.MovieInfo;
import com.example.popularmovies.task.FavoriteTasks;

import java.io.IOException;
import java.util.List;

public class MainActivity
        extends AppCompatActivity
        implements PopularMoviesAdapter.MovieOnClickHandler {

    public static final String MOVIE_DB_API_KEY = BuildConfig.API_KEY;

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.movie_collections);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(
                getSupportFragmentManager());


        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }


    @Override
    public void onClick(MovieInfo movieInfo) {
        Intent movieDetailsIntent = new Intent(this, MovieDetailsActivity.class);
        movieDetailsIntent.putExtra(MovieDetailsActivity.MOVIE_INFO_INTENT_PARAM, movieInfo);
        startActivity(movieDetailsIntent);
    }

    @Override
    public void onClick(String movieId) {
        Intent movieDetailsIntent = new Intent(this, MovieDetailsActivity.class);
        movieDetailsIntent.putExtra(MovieDetailsActivity.MOVIE_ID_PARAM, movieId);
        startActivity(movieDetailsIntent);
    }
}

class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        MovieGridFragment movieGridFragment = new MovieGridFragment();
        Bundle args = new Bundle();
        switch (position) {
            case 0: {
                args.putInt(MovieGridFragment.PARAM_QUERY_TYPE, MovieGridFragment.POPULAR_MOVIE_LOADER);
                break;
            }
            case 1: {
                args.putInt(MovieGridFragment.PARAM_QUERY_TYPE, MovieGridFragment.TOP_RATED_MOVIE_LOADER);
                break;
            }
            case 2: {
                args.putInt(MovieGridFragment.PARAM_QUERY_TYPE, MovieGridFragment.FAVORITE_MOVIE_LOADER);
                break;
            }
        }
        movieGridFragment.setArguments(args);
        return movieGridFragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Popular";
            case 1:
                return "Top rated";
            case 2:
                return "Favorite";
        }
        return null;
    }
}

