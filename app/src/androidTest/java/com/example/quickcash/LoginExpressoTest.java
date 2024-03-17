package com.example.quickcash;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiSelector;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anyOf;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginExpressoTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void LoginSuccessful() {

        onView(withId(R.id.username))
                .perform(typeText("ahasan@dal.ca"), closeSoftKeyboard());
        onView(withId(R.id.password))
                .perform(typeText("qwertwert"), closeSoftKeyboard());
        onView(withId(R.id.login_button)).perform(click());

        // Check that the login button is not displayed, which indicates we have navigated away from the login screen
        onView(withId(R.id.login_button)).check(doesNotExist());
    }

    @Test
    public void LoginFailed() {
        // Use known incorrect credentials
        onView(withId(R.id.username))
                .perform(typeText("wrong.email@dal.ca"), closeSoftKeyboard());
        onView(withId(R.id.password))
                .perform(typeText("not the password"), closeSoftKeyboard());
        onView(withId(R.id.login_button)).perform(click());

        onView(withId(R.id.login_button)).check(matches(isDisplayed()));
    }


}
