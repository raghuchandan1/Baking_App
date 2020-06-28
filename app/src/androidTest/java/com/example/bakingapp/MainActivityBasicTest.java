package com.example.bakingapp;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
public class MainActivityBasicTest {
    private MainActivity mActivity;
    private boolean mIsScreenSw600dp;
    /*@Rule
    public ActivityTestRule<MainActivity> mActivityRule
            = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void onRecipeClick_LaunchNewActivity(){
        // Finding view and performing action
        onView(withId(R.id.recipe_recyclerview)).perform(click());

        // View Assertion
    }*/
    /*@Rule
    public IntentsTestRule<MainActivity> mActivityRule = new IntentsTestRule<>(MainActivity.class);*/
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule
            = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void clickRecipeItem_OpensDetailFragment() {
        // Find the view & Perform action on view
        onView((withId(R.id.recipe_recyclerview)))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Check if the view does as expected
        onView(withId(R.id.recipe_detail_holder)).check(matches(isDisplayed()));
    }
}
