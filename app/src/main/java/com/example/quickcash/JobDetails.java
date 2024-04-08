package com.example.quickcash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class JobDetails extends AppCompatActivity {
    // Define TextViews to show job details
    private TextView jobTitleTextView;
    private TextView jobDescriptionTextView;
    private TextView jobPaymentTextView;
    private TextView jobLocationTextView;
    private TextView jobTypeTextView;
    private String jobId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_details); // Ensure this matches your XML file name

        Button applyBtn = findViewById(R.id.applyBtn);
        Intent intent = getIntent();

        applyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent applyIntent = new Intent(JobDetails.this, ApplyJobActivity.class);
                applyIntent.putExtra("jobId", intent.getStringExtra("jobId"));
                startActivity(applyIntent);
                Toast.makeText(JobDetails.this, "Job ID: " + intent.getStringExtra("jobId"), Toast.LENGTH_SHORT).show();
            }
        });
        // Initialize TextViews by finding them in the layout
        jobTitleTextView = findViewById(R.id.title_jobDetails);
        jobDescriptionTextView = findViewById(R.id.jobDescription_jobDetails);
        jobPaymentTextView = findViewById(R.id.jobPayment_jobDetails);
        jobLocationTextView = findViewById(R.id.jobLocation_jobDetails);
        jobTypeTextView = findViewById(R.id.jobType_jobDetails);

        // Get the job details passed from the previous activity
        String jobTitle = intent.getStringExtra("jobTitle");
        String jobDescription = intent.getStringExtra("jobDescription");
        String jobPayment = intent.getStringExtra("jobPayment");
        String jobLocation = intent.getStringExtra("jobLocation");
        String jobType = intent.getStringExtra("jobType");

        // Set the TextViews to show the job details
        jobTitleTextView.setText(jobTitle);
        jobDescriptionTextView.setText(jobDescription);
        jobPaymentTextView.setText(jobPayment);
        jobLocationTextView.setText(jobLocation);
        jobTypeTextView.setText(jobType);

        Button jobBoardBtn = findViewById(R.id.job_board_btn);
        Button notificationButton = findViewById(R.id.notification_btn);
        Button profileBtn = findViewById(R.id.profile_btn);
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the new activity
                Intent intent = new Intent(JobDetails.this, EmployeeProfile.class);
                startActivity(intent);
            }
        });
        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the new activity
                Intent intent = new Intent(JobDetails.this, JobNotification.class);
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
                    intent = new Intent(JobDetails.this, EmployeeLanding.class);
                } else {
                    intent = new Intent(JobDetails.this, EmployerLanding.class);
                }
                startActivity(intent);
            }
        });
    }

}
