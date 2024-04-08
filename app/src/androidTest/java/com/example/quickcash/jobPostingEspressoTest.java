package com.example.quickcash;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.anything;

@RunWith(AndroidJUnit4.class)
public class jobPostingEspressoTest {

    @Rule
    public ActivityScenarioRule<JobPostingActivity> activityRule =
            new ActivityScenarioRule<>(JobPostingActivity.class);

    @Test
    public void testJobPosting() {
        // Type in the job title
        onView(withId(R.id.jobTitle)).perform(typeText("Software Engineer"));

        // Type in the job description
        onView(withId(R.id.description)).perform(typeText("Develop and maintain software."));

        // Type in the payment
        onView(withId(R.id.payment)).perform(typeText("5000"));

        // Type in the location
        onView(withId(R.id.location)).perform(typeText("New York, NY"));

        // Open the spinner and select the job type
        onView(withId(R.id.jobType)).perform(click());
        onData(anything()).atPosition(1).perform(click());

        // Click the post button
        onView(withId(R.id.post)).perform(click());


    }
}