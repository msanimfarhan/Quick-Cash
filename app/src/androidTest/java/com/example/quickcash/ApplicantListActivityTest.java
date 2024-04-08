package com.example.quickcash;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class ApplicantListActivityTest {

    @Test
    public void activityNotNull() {
        try (ActivityScenario<ApplicantListActivity> scenario = ActivityScenario.launch(ApplicantListActivity.class)) {
            scenario.onActivity(activity -> {
                assertNotNull(activity);
            });
        }
    }

    @Test
    public void recyclerViewNotNullAfterOnCreate() {
        try (ActivityScenario<ApplicantListActivity> scenario = ActivityScenario.launch(ApplicantListActivity.class)) {
            scenario.onActivity(activity -> {
                RecyclerView recyclerView = activity.findViewById(R.id.recyclerViewApplicants);
                assertNotNull(recyclerView);
                assertTrue(recyclerView.getLayoutManager() instanceof LinearLayoutManager);
            });
        }
    }
    @Test
    public void recyclerViewAdapterNotNullAfterOnCreate() {
        try (ActivityScenario<ApplicantListActivity> scenario = ActivityScenario.launch(ApplicantListActivity.class)) {
            scenario.onActivity(activity -> {
                RecyclerView recyclerView = activity.findViewById(R.id.recyclerViewApplicants);
                assertNotNull("RecyclerView adapter should not be null", recyclerView.getAdapter());
            });
        }
    }

    @Test
    public void recyclerViewUsesLinearLayoutManager() {
        try (ActivityScenario<ApplicantListActivity> scenario = ActivityScenario.launch(ApplicantListActivity.class)) {
            scenario.onActivity(activity -> {
                RecyclerView recyclerView = activity.findViewById(R.id.recyclerViewApplicants);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                assertTrue("LayoutManager should be a LinearLayoutManager", layoutManager instanceof LinearLayoutManager);
            });
        }
    }
}
