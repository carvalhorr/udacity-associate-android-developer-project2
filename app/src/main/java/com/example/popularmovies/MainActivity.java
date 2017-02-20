package com.example.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.popularmovies.model.MovieInfo;

/**
 * Show three tabs with a grid of movies: popular, best rated and favorites.
 *
 * Created by carvalhorr.
 */

public class MainActivity
        extends AppCompatActivity
        implements MovieListAdapter.MovieOnClickHandler {

    // Declare variables for the view elements.
    private ViewPager mViewPager;
    private TabLayout tabLayout;

    // Declare variable for the pager adapter.
    private SectionsPagerAdapter mSectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load activity layout
        setContentView(R.layout.activity_main);

        // Get reference to toolbar view and set it up.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create instance of the pager adapter.
        mSectionsPagerAdapter = new SectionsPagerAdapter(
                getSupportFragmentManager());

        // Ge reference to the view pager and hook the pager adapter to it
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // Get a reference to the tabs section and set it up
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }


    /**
     * Handle the click on a movie that contains all its data loaded (popular and top rated).
     * @param movieInfo
     */
    @Override
    public void onClick(MovieInfo movieInfo) {

        // Create intent and pass the movieInfo instance to it
        Intent movieDetailsIntent = new Intent(this, MovieDetailsActivity.class);
        movieDetailsIntent.putExtra(MovieDetailsActivity.MOVIE_INFO_INTENT_PARAM, movieInfo);

        // Start movie details activity passing the movieInfo
        startActivity(movieDetailsIntent);

    }

    /**
     * Handle the click on a movie that does not contain all its data loaded (favorites)
     * @param movieId
     */
    @Override
    public void onClick(String movieId) {

        // Create intent and pass the movieId
        Intent movieDetailsIntent = new Intent(this, MovieDetailsActivity.class);
        movieDetailsIntent.putExtra(MovieDetailsActivity.MOVIE_ID_PARAM, movieId);

        // Start movie details activity passing the movieId
        startActivity(movieDetailsIntent);

    }
}

/**
 * Pager adapter responsible for creating the Movie grid fragment for the three different lists of movies
 */
class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        // Create a new fragment
        MovieGridFragment movieGridFragment = new MovieGridFragment();

        // Set the parameter for the fragment to show the correct list of movies: popular, top rated and favorites
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

        // Set the title of each page
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

