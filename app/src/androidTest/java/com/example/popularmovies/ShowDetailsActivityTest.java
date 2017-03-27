package com.example.popularmovies;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.matcher.IntentMatchers;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static android.support.test.espresso.intent.Intents.*;
import static android.support.test.espresso.intent.matcher.IntentMatchers.*;
import static org.hamcrest.core.AllOf.*;

/**
 * Created by carvalhorr on 3/27/17.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ShowDetailsActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityTestRule = new ActivityTestRule<MainActivity>(
            MainActivity.class);

    @Test
    public void whenUserClickOnAPopularMovieThenDetailsAcitivityisDisplayed() throws InterruptedException {

        // click on first movie
        onView(allOf(
                withId(R.id.rv_movies_grid), isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));


        // make sure details acitivty is displayed
        onView(withId(R.id.floatingActionButton)).check(ViewAssertions.matches(isDisplayed()));
        //intended(IntentMatchers.hasComponent(MovieDetailsActivity.class.getName()));

    }
}
