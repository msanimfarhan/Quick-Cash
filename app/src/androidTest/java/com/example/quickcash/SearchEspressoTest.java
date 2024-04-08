package com.example.quickcash;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.contrib.RecyclerViewActions;

import com.example.quickcash.R;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SearchEspressoTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testSearchForBugFixingJob() {
        // Type "employee@gmail.com" into the username field
        onView(withId(R.id.username)).perform(typeText("employee@gmail.com"), closeSoftKeyboard());

        // Type "12345678" into the password field and close the keyboard
        onView(withId(R.id.password)).perform(typeText("12345678"), closeSoftKeyboard());

        // Press the login button
        onView(withId(R.id.login_button)).perform(click());

        // Wait for the employee screen to appear by checking for a unique view on that screen
//        onView(withId(R.id.searchBox)).check(matches(isDisplayed()));

        // Now that we're on the employee screen, continue with the test
        onView(withId(R.id.searchBox)).perform(click());
        onView(withId(R.id.searchBox)).perform(typeText("bug"), closeSoftKeyboard());

        // Click on the "searchBtn"
        onView(withId(R.id.searchBtn)).perform(click());


        // Assert that an item with the title "Bug Fixing" is found
        onView(withId(R.id.jobRecycler2))
                .check(matches(hasDescendant(withText(Matchers.containsString("Bug Fixing")))));
    }
}
