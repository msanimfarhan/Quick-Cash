package com.example.quickcash;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.FirebaseApp;

import java.util.List;

public class EmployerLanding extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private RecyclerView jobsRecyclerView;
    private JobAdapter adapter;
    FirebaseCrud crud = null;
    private FirebaseDatabase database;

    private FusedLocationProviderClient fusedLocationClient;
    private TextView locationTextView;
    //
    private DatabaseReference databaseReference;
    private String userEmail = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emplyer_landing);

//        Toolbar employeeToolbar = (Toolbar) findViewById(R.id.employee_toolbar);
//        setSupportActionBar(employeeToolbar);

        // Initialize Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String sanitizedEmail = userEmail.replace(".", ",");
        databaseReference = database.getReference("Users").child(sanitizedEmail);

        // Initialize FusedLocationProviderClient
        locationTextView = findViewById(R.id.location_text_view);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        checkLocationPermissionAndGetLocation();
        Button jobBoardBtn = findViewById(R.id.job_board_btn);
        Button notificationButton = findViewById(R.id.notification_btn);
        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the new activity
                Intent intent = new Intent(EmployerLanding.this, JobNotification.class);
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
                    intent = new Intent(EmployerLanding.this, EmployeeLanding.class);
                } else{
                    intent = new Intent(EmployerLanding.this, EmployerLanding.class);
                }
                startActivity(intent);
            }
        });
    }

    private void checkLocationPermissionAndGetLocation() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            getLastLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getLastLocation();
        }

    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            String locationStr = "Latitude: " + location.getLatitude() + "\nLongitude: " + location.getLongitude();
                            //String locationStr = "Latitude: " + "44.6358" + "\nLongitude: " + "-63.5952";
                            locationTextView.setText(locationStr);

                            // Update location in Firebase
                            Map<String, Object> locationUpdate = new HashMap<>();
                            locationUpdate.put("location", locationStr);
                            databaseReference.updateChildren(locationUpdate);
                        } else {
                            locationTextView.setText("Location not available");
                        }
                    }
                });


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
                adapter = new JobAdapter(jobPostings, EmployerLanding.this);
                jobsRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(EmployerLanding.this, "Error fetching jobs: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    protected void initializeDatabaseAccess() {
        // Assuming you have a string resource named FIREBASE_DB_URL with the Firebase database URL.
        FirebaseDatabase database = FirebaseDatabase.getInstance(getResources().getString(R.string.FIREBASE_DB_URL));
        crud = new FirebaseCrud(database);
    }

    protected void move2JobPosting() {
        Intent employerIntent = new Intent(this, JobPostingActivity.class);
        startActivity(employerIntent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // If needed, implement item selection handling here
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // If needed, implement action for no selection here
    }

    private void setupAddJobListener() {
        Button addButton = findViewById(R.id.button); // Ensure the button ID matches XML

        // Set onClick listener for the add job button.
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                move2JobPosting();
            }
        });
    }
    public void showApplicants(String jobId,String payment) {
        Intent intent = new Intent(this, ApplicantListActivity.class);
        intent.putExtra("jobId", jobId);
        intent.putExtra("payment", payment);
        startActivity(intent);
        FirebaseCrud firebaseCrud = new FirebaseCrud(FirebaseDatabase.getInstance());

        firebaseCrud.fetchApplicantsForJob(jobId, new FirebaseCrud.ApplicantsResultCallback() {
            @Override
            public void onApplicantsRetrieved(List<Applicant> applicants) {
                // For simplicity, let's just log the applicants' names
                for (Applicant applicant : applicants) {
                    Log.d("Applicant Name", applicant.getName());
                }
                for (Applicant applicant : applicants) {
                    Log.d("Applicant email", applicant.getEmail());
                }
                for (Applicant applicant : applicants) {
                    Log.d("Applicant phonenumber", applicant.getPhoneNumber());
                }


                // Here you would normally update the RecyclerView with the list of applicants
                // For now, you can just check the log to confirm that applicant names are being printed
            }

            @Override
            public void onError(Exception e) {
                Log.e("Firebase Error", "Error fetching applicants", e);
            }
        });
    }

    @Override
    public void onClick(View v) {
        // This can be expanded if you have multiple clickable elements
        move2JobPosting();

    }
}
