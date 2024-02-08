package com.example.quickcash;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.allOf;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class EspressoTest {

    public ActivityScenario<MainActivity> scenario;

    @Before
    public void setup() {
        scenario = ActivityScenario.launch(MainActivity.class);
        scenario.onActivity(activity -> {

            activity.setupRegistrationButton();

        });
    }

    @Test
    public void checkIfEmailValid() {
        onView(withId(R.id.signupName)).perform(typeText("Jahid Hasan"));
        onView(withId(R.id.signupEmail)).perform(typeText("abc123@gmail.com"));
        onView(withId(R.id.signupPass)).perform(typeText("jahid123"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.signupConfirmPass)).perform(typeText("jahid123"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.signupBtn)).perform(click());
        onView(withId(R.id.invalidEmailMessage)).check(matches(withText(R.string.VALID_EMAIL_ADDRESS)));
    }

    @Test
    public void checkIfPasswordIsValid() {
        onView(withId(R.id.signupName)).perform(typeText("Jahid Hasan"));
        onView(withId(R.id.signupEmail)).perform(typeText("abc.123@gmail.com"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.signupPass)).perform(typeText("jahid1"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.signupConfirmPass)).perform(typeText("jahid1"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.signupBtn)).perform(click());
        onView(withId(R.id.invalidPassMsg)).check(matches(withText(R.string.INVALID_PASSWORD)));
    }
    @Test
    public void checkIfBothPasswordMatches() {
        onView(withId(R.id.signupName)).perform(typeText("Jahid Hasan"));
        onView(withId(R.id.signupEmail)).perform(typeText("abc.123@gmail.com"));
        onView(withId(R.id.signupPass)).perform(typeText("jahid123"));
        onView(withId(R.id.signupConfirmPass)).perform(typeText("jahid123"));
        onView(withId(R.id.signupBtn)).perform(click());
        onView(withId(R.id.passMatchMsg)).check(matches(withText(R.string.PASSWORD_MATCHING)));
    }


}
