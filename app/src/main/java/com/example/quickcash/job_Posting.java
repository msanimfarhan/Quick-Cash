package com.example.quickcash;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

public class job_Posting extends AppCompatActivity {
    private EditText editJobTitle;
    private EditText editJobDescription;
    private EditText editPayments;
    private EditText editLocation;
    private EditText jobTags;
    private Spinner spinnerJobType; // Spinner for job type
    private String userEmail;
    private FirebaseCrud crud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_posting);
        FirebaseApp.initializeApp(this);

        initializeDatabaseAccess();
        setupUI();
        setUser();
        setupPostJobButton();
    }

    private void setupUI() {
        editJobTitle = findViewById(R.id.jobTitle);
        editJobDescription = findViewById(R.id.description);
        editPayments = findViewById(R.id.payment);
        editLocation = findViewById(R.id.location);
        jobTags = findViewById(R.id.jobTags);
        spinnerJobType = findViewById(R.id.jobType); // Initialize Spinner

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(
                this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.job_types_array)) {
            @Override
            public boolean isEnabled(int position) {
                // Disable the first 'hint' item
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                TextView view = (TextView) super.getDropDownView(position, convertView, parent);
                if (position == 0) {
                    // Set the hint text color
                    view.setTextColor(Color.GRAY);
                } else {
                    // Set the dropdown items text color
                    view.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerJobType.setAdapter(adapter);
    }

    private void setUser() {
        SharedPreferences sharedPref = getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        userEmail = sharedPref.getString("userEmail", "");
    }

    private void setupPostJobButton() {
        Button postJobButton = findViewById(R.id.post);
        postJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postJob();
            }
        });
    }

    private void postJob() {
        String jobTitle = editJobTitle.getText().toString().trim();
        String jobDescription = editJobDescription.getText().toString().trim();
        String paymentDetail = editPayments.getText().toString().trim();
        String jobLocation = editLocation.getText().toString().trim();
        String jobType = spinnerJobType.getSelectedItem().toString();
        String tags= jobTags.getText().toString().trim();
        if (spinnerJobType.getSelectedItemPosition() == 0) {
            // User did not select a job type, show an error message
            Toast.makeText(job_Posting.this, "Please select a job type.", Toast.LENGTH_LONG).show();
            return; // Do not proceed further
        }

        // Now you can use the jobType value in your JobPosting object
        JobPosting newJob = new JobPosting(jobTitle, jobDescription, paymentDetail, jobLocation, jobType, userEmail,tags);

        crud.addJobPosting(newJob, userEmail, new FirebaseCrud.JobPostingResultCallback() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(job_Posting.this, "Job posted successfully!", Toast.LENGTH_LONG).show();
                finish(); // Close the activity if you want to
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(job_Posting.this, "Failed to post job: " + errorMessage, Toast.LENGTH_LONG).show();
            }
        });
    }

    protected void initializeDatabaseAccess() {
        // Assuming you have a string resource for your database URL
        FirebaseDatabase database = FirebaseDatabase.getInstance(getResources().getString(R.string.FIREBASE_DB_URL));
        crud = new FirebaseCrud(database);
    }
}
