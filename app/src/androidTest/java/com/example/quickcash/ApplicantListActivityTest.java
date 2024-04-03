package com.example.quickcash;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class ApplicantListActivityTest {

    @Rule
    public ActivityScenarioRule<ApplicantListActivity> activityRule =
            new ActivityScenarioRule<>(ApplicantListActivity.class);

    @Test
    public void checkRecyclerViewDisplay() {
        Espresso.onView(withId(R.id.recyclerViewApplicants))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void checkApplicantNameInRecyclerView() {
        // Scroll to the item to make sure it's visible and check if it contains the expected text.
        // Note: This test assumes you have an applicant with the name "John Doe" in your data set.
        Espresso.onView(withId(R.id.recyclerViewApplicants))
                .perform(scrollTo())
                .check(ViewAssertions.matches(ViewMatchers.hasDescendant(withText("John Doe"))));
    }
}