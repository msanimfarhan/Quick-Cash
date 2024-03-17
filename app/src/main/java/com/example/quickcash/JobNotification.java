package com.example.quickcash;

import static androidx.constraintlayout.widget.Constraints.TAG;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class JobNotification extends AppCompatActivity {

    private RecyclerView jobNotificationRecycler;
    private FirebaseCrud firebaseCrud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_notification);

        jobNotificationRecycler = findViewById(R.id.jobNotificationRecycler);
        jobNotificationRecycler.setLayoutManager(new LinearLayoutManager(this));

        firebaseCrud = new FirebaseCrud(FirebaseDatabase.getInstance());

        FirebaseApp.initializeApp(this);

        fetchJobNotifications();
    }

    private void fetchJobNotifications() {
        firebaseCrud.fetchAllJobNotifications(new FirebaseCrud.JobNotificationsResultCallback() {
            @Override
            public void onJobNotificationsRetrieved(List<JobNotificationData> jobNotifications) {
                JobNotificationAdapter adapter = new JobNotificationAdapter(jobNotifications);
                jobNotificationRecycler.setAdapter(adapter);
            }

            @Override
            public void onError(Exception e) {
                Log.e(TAG, "Error fetching job notifications: " + e.getMessage());
            }
        });
    }

    private List<JobNotificationData> convertToJobNotifications(List<JobPosting> jobPostings) {
        List<JobNotificationData> jobNotifications = new ArrayList<>();
        for (JobPosting jobPosting : jobPostings) {
            JobNotificationData jobNotification = new JobNotificationData();
            jobNotifications.add(jobNotification);
        }
        return jobNotifications;
    }
}
