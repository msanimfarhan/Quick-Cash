package com.example.quickcash;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginPersistenceTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testUserRemainsLoggedInAfterAppReopen() {
        // Input Credentials
        String validUsername = "ahasan";
        String validPassword = "qwertwert";

        // Login attempt
        onView(withId(R.id.username)).perform(typeText(validUsername), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText(validPassword), closeSoftKeyboard());
        onView(withId(R.id.login_button)).perform(click());

        // need dashboard_view ID in UI from Soumya's Team
        onView(withId(R.id.dashboard_view)).check(matches(isDisplayed()));

        // should i use UI automator? this just emulates pressing back button and launching the app again
        pressBack();
        ActivityScenario.launch(MainActivity.class);

        // dashboard_view checker 100% Will fail because dashboard_view does not exist yet
        onView(withId(R.id.dashboard_view)).check(matches(isDisplayed()));
    }
}

