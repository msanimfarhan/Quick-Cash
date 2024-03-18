package com.example.quickcash;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiSelector;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.anyOf;



@RunWith(AndroidJUnit4.class)
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LoginEspressoTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void Test1_LoggedInTest() throws Exception {
        onView(withId(R.id.username))
                .perform(typeText("ahasan@dal.ca"), closeSoftKeyboard());
        onView(withId(R.id.password))
                .perform(typeText("qwertwert"), closeSoftKeyboard());
        onView(withId(R.id.login_button)).perform(click());

        onView(anyOf(
                withText("Employee Landing"),
                withText("Employer Landing")
        )).check(matches(isDisplayed()));

        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        device.pressHome();


        Thread.sleep(1000);

        device.pressRecentApps();


        Thread.sleep(1000);

        device.findObject(new UiSelector().descriptionContains("QuickCash")).click();

        onView(anyOf(
                withText("Employee Landing"),
                withText("Employer Landing")
        )).check(matches(isDisplayed()));
    }


    @Test
    public void Test2_LoginSuccessful() {

        onView(withId(R.id.username))
                .perform(typeText("ahasan@dal.ca"), closeSoftKeyboard());
        onView(withId(R.id.password))
                .perform(typeText("qwertwert"), closeSoftKeyboard());
        onView(withId(R.id.login_button)).perform(click());



        // Check that the login button is not displayed, which indicates we have navigated away from the login screen

        onView(withId(R.id.login_button)).check(doesNotExist());
    }

    @Test

    public void Test3_LoginFailed() {

        // Use known incorrect credentials
        onView(withId(R.id.username))
                .perform(typeText("wrong.email@dal.ca"), closeSoftKeyboard());
        onView(withId(R.id.password))
                .perform(typeText("not the password"), closeSoftKeyboard());
        onView(withId(R.id.login_button)).perform(click());

        onView(withId(R.id.login_button)).check(matches(isDisplayed()));
    }


    @Test
    public void testLocationDisplayedOnEmployeeLandingPage() {
        onView(withId(R.id.username)).perform(typeText("a@dal.ca"), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("12345678"), closeSoftKeyboard());
        onView(withId(R.id.login_button)).perform(click());
        onView(withId(R.id.location_text_view))
                .check(matches(withText(containsString("Latitude:"))))
                .check(matches(withText(containsString("Longitude:"))));
    }

    @Test
    public void testLocationDisplayedOnEmployerLandingPage() {
        onView(withId(R.id.username)).perform(typeText("karim@dal.ca"), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("abcabc123"), closeSoftKeyboard());
        onView(withId(R.id.login_button)).perform(click());
        onView(withId(R.id.location_text_view))
                .check(matches(withText(containsString("Latitude:"))))
                .check(matches(withText(containsString("Longitude:"))));
    }

}

