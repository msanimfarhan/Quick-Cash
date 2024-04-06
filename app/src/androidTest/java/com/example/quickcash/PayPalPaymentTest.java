package com.example.quickcash;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class PayPalPaymentTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testPaymentProcess() throws InterruptedException, UiObjectNotFoundException {
        // Log in as an employer
        onView(withId(R.id.username)).perform(typeText("a@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("11111111"), closeSoftKeyboard());
        onView(withId(R.id.login_button)).perform(click());

        // Handle the location permission dialog
        UiDevice mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        mDevice.findObject(new UiSelector().text("While using the app")).click();

        // Assuming login redirects to the employer landing page where the RecyclerView is
        // Navigate to the "Teaching Assistant" job posting
        onView(withId(R.id.jobsRecycler))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Assume the job posting detail page includes a pay button for initiating payment
        onView(withId(R.id.Pay)).perform(click());
//
//        // Fill in the payment details
//        onView(withId(R.id.amount)).perform(typeText("100"), closeSoftKeyboard());
//        onView(withId(R.id.paypalpaybtn)).perform(click());
//
//        // Here, you would mock the PayPal interaction, but for the purpose of this example, we'll simulate a successful payment by checking for a success message
//        // Check for success message
//        onView(withId(R.id.PaymentStatus)).check(matches(withText("Payment successful")));
//
//        // Add additional steps as necessary for your app's flow
    }
}
