package com.example.quickcash;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static org.hamcrest.Matchers.not;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testUserCanLoginWithCorrectCredentials() {
        // Input Credentials
        String validUsername = "ahasan";
        String validPassword = "qwertwert";


        onView(withId(R.id.username)).perform(typeText(validUsername));


        closeSoftKeyboard();


        onView(withId(R.id.password)).perform(typeText(validPassword));


        closeSoftKeyboard();


        onView(withId(R.id.login_button)).perform(click());


        onView(withId(R.id.login_button)).check(matches(not(isDisplayed())));
    }
}
