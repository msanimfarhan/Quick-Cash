package com.example.quickcash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class employee_landing extends AppCompatActivity{
    private RecyclerView jobsRecyclerView;
    private JobAdapter adapter;
    private FirebaseCrud firebaseCrud;
    FirebaseCrud crud = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_landing);
        jobsRecyclerView = findViewById(R.id.jobsRecycler);
        jobsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseApp.initializeApp(this);

        // Initialize database access and Firebase CRUD operations
        initializeDatabaseAccess();
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
                Toast.makeText(employee_landing.this, "Error fetching jobs: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }




    protected void initializeDatabaseAccess() {
        // Assuming you have a string resource named FIREBASE_DB_URL with the Firebase database URL.
        FirebaseDatabase database = FirebaseDatabase.getInstance(getResources().getString(R.string.FIREBASE_DB_URL));
        crud = new FirebaseCrud(database);
    }



}