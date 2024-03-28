package com.example.quickcash;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
public class jobDetails extends AppCompatActivity {
    // Define TextViews to show job details
    private TextView jobTitleTextView;
    private TextView jobDescriptionTextView;
    private TextView jobPaymentTextView;
    private TextView jobLocationTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_details); // Ensure this matches your XML file name

        // Initialize TextViews by finding them in the layout
        jobTitleTextView = findViewById(R.id.jobTitleTextView);
        jobDescriptionTextView = findViewById(R.id.jobDescriptionTextView);
        jobPaymentTextView = findViewById(R.id.jobPaymentTextView);
        jobLocationTextView = findViewById(R.id.jobLocationTextView);

        // Get the job details passed from the previous activity
        Intent intent = getIntent();
        String jobTitle = intent.getStringExtra("jobTitle");
        String jobDescription = intent.getStringExtra("jobDescription");
        String jobPayment = intent.getStringExtra("jobPayment");
        String jobLocation = intent.getStringExtra("jobLocation");

        // Set the TextViews to show the job details
        jobTitleTextView.setText(jobTitle);
        jobDescriptionTextView.setText(jobDescription);
        jobPaymentTextView.setText(jobPayment);
        jobLocationTextView.setText(jobLocation);
    }

}
