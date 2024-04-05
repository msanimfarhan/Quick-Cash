package com.example.quickcash;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
public class jobDetails extends AppCompatActivity {
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

        Button applyBtn= findViewById(R.id.applyBtn);
        Intent intent = getIntent();

        applyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent applyIntent=new Intent(jobDetails.this,ApplyJobActivity.class);
                applyIntent.putExtra("jobId",intent.getStringExtra("jobId"));
                startActivity(applyIntent);
                Toast.makeText(jobDetails.this,"Job ID: "+intent.getStringExtra("jobId"),Toast.LENGTH_SHORT).show();
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
    }

}
