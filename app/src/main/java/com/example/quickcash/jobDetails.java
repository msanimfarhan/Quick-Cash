package com.example.quickcash;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class jobDetails extends AppCompatActivity {
    // Define TextViews to show job details
    private TextView jobTitleTextView;
    private TextView jobDescriptionTextView;
    private TextView jobPaymentTextView;
    private TextView jobLocationTextView;
    private Button applyButton; // Define a Button for the apply action

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_details); // Ensure this matches your XML file name

        // Initialize TextViews by finding them in the layout
        jobTitleTextView = findViewById(R.id.jobTitleTextView);
        jobDescriptionTextView = findViewById(R.id.jobDescriptionTextView);
        jobPaymentTextView = findViewById(R.id.jobPaymentTextView);
        jobLocationTextView = findViewById(R.id.jobLocationTextView);
        applyButton = findViewById(R.id.button2); // Initialize the Apply Button using its ID

        // Get the job details passed from the previous activity
        Intent intent = getIntent();
        final String jobTitle = intent.getStringExtra("jobTitle");
        final String jobDescription = intent.getStringExtra("jobDescription");
        final String jobPayment = intent.getStringExtra("jobPayment");
        final String jobLocation = intent.getStringExtra("jobLocation");

        // Set the TextViews to show the job details
        jobTitleTextView.setText(jobTitle);
        jobDescriptionTextView.setText(jobDescription);
        jobPaymentTextView.setText(jobPayment);
        jobLocationTextView.setText(jobLocation);

        // Set the OnClickListener for the Apply button
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to start ApplyJobActivity
                Intent applyIntent = new Intent(jobDetails.this, ApplyJobActivity.class);

                // You can also put extras if needed, for example the job ID
                applyIntent.putExtra("jobTitle", jobTitle); // Optional, only if you need to pass data

                // Start the activity
                startActivity(applyIntent);
            }
        });
    }
}
