package com.example.quickcash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class employer_landing extends AppCompatActivity  implements View.OnClickListener, AdapterView.OnItemSelectedListener{
    private RecyclerView jobsRecyclerView;
    private JobAdapter adapter;
    private FirebaseCrud firebaseCrud;
    FirebaseCrud crud = null;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emplyer_landing);
       jobsRecyclerView = findViewById(R.id.jobsRecycler);
       jobsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize Firebase
        FirebaseApp.initializeApp(this);

        // Initialize database access and Firebase CRUD operations
        initializeDatabaseAccess();


        setupAddJobListener();

        fetchJobsAndUpdateUI();

    }
    private void fetchJobsAndUpdateUI() {
        SharedPreferences sharedPref = getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        String userEmail = sharedPref.getString("userEmail", null);
        String sanitizedEmail = userEmail.replace(".", ",");

        crud.fetchUserJobs(sanitizedEmail, new FirebaseCrud.JobPostingsResultCallback() {
            @Override
            public void onJobPostingsRetrieved(List<JobPosting> jobPostings) {
                // Use the job postings list to update the RecyclerView
                adapter = new JobAdapter(jobPostings);
                jobsRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(employer_landing.this, "Error fetching jobs: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    protected void initializeDatabaseAccess() {
        // Assuming you have a string resource named FIREBASE_DB_URL with the Firebase database URL.
        FirebaseDatabase database = FirebaseDatabase.getInstance(getResources().getString(R.string.FIREBASE_DB_URL));
        crud = new FirebaseCrud(database);
    }


        // fetch job from user
        // show them in a list  in a recycle view >> give chatgpt your layout// employer layout
        //how to populate this list from backend



    protected void move2JobPosting(){
        Intent emplyerIntent = new Intent(this, job_Posting.class);
        startActivity(emplyerIntent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void setupAddJobListener() {
        Button loginButton = findViewById(R.id.button);

        // Set onClick listener for the login button.
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                move2JobPosting();
            }
        });
    }

    @Override
    public void onClick(View v){


        move2JobPosting();
    }



}
