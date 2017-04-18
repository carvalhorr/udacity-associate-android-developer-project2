package com.example.popularmovies;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.TextView;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.atomic.AtomicReference;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Created by carvalhorr on 3/27/17.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class FavoritesTest {

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
