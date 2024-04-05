package com.example.quickcash;

import static androidx.constraintlayout.widget.Constraints.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcash.JobPosting.JobPosting;
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
        Button jobBoardBtn = findViewById(R.id.job_board_btn);
        Button notificationButton = findViewById(R.id.notification_btn);
        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the new activity
                Intent intent = new Intent(JobNotification.this, JobNotification.class);
                startActivity(intent);
            }
        });
        jobBoardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the new activity
                SharedPreferences sharedPref = getSharedPreferences("userDetails", Context.MODE_PRIVATE);
                String userRole = sharedPref.getString("userRole", "");
                Intent intent;
                if (userRole.equals("Employee")) {
                    intent = new Intent(JobNotification.this, employee_landing.class);
                } else{
                    intent = new Intent(JobNotification.this, employer_landing.class);
                }
                startActivity(intent);
            }
        });
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
