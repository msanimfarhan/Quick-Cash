package com.example.quickcash;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class job_Posting extends AppCompatActivity {
    private EditText editJobTitle;
    private EditText editJobDescription;
    private EditText editPayments;
    private EditText editLocation;
    private FirebaseCrud firebaseCrud;
    private FirebaseDatabase database;

    private String userEmail;
    // FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    // Initializing the FirebaseCrud object for CRUD operations.
    FirebaseCrud crud = null;


    private void setupUI() {
        editJobTitle = findViewById(R.id.jobTitle);
        editJobDescription = findViewById(R.id.description);
        editPayments = findViewById(R.id.payment);
        editLocation = findViewById(R.id.location);
    }

    private void setUser(){
        SharedPreferences sharedPref = getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        userEmail = sharedPref.getString("userEmail", null);



    }
  // save to firebase
  private void postJob() {
        // Get the job details from the EditText fields
        String title = editJobTitle.getText().toString().trim();
        String description = editJobDescription.getText().toString().trim();
        String paymentDetail = editPayments.getText().toString().trim();
        String jobLocation = editLocation.getText().toString().trim();



        JobPosting newJob = new JobPosting(title, description, paymentDetail, jobLocation);

        // Post the job to Firebase
        crud.addJobPosting(newJob, userEmail, new FirebaseCrud.JobPostingResultCallback() {
            @Override
            public void onSuccess(String result) {
                // Job was added successfully, handle this case
                Toast.makeText(job_Posting.this, "Job posted successfully!", Toast.LENGTH_LONG).show();
                // Optionally finish the activity
                job_Posting.this.finish();
            }

            @Override
            public void onFailure(String errorMessage) {
                // There was an error posting the job, handle this case
                Toast.makeText(job_Posting.this, "Failed to post job: " + errorMessage, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_posting);

        // Initialize Firebase
        FirebaseApp.initializeApp(this);


        // Initialize database access and Firebase CRUD operations
        initializeDatabaseAccess();

         setupUI();

         setUser();
        // Setup the post job button
        Button postJobButton = findViewById(R.id.post); // Replace with your actual button's ID
        postJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postJob();
            }
        });

    }

    protected void initializeDatabaseAccess() {
        // Assuming you have a string resource named FIREBASE_DB_URL with the Firebase database URL.
        FirebaseDatabase database = FirebaseDatabase.getInstance(getResources().getString(R.string.FIREBASE_DB_URL));
        crud = new FirebaseCrud(database);
    }

}
