package com.example.quickcash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;


import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import android.location.Location;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
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



public class EmployeeLanding extends AppCompatActivity {
    private TextView locationTextView;
    private FusedLocationProviderClient fusedLocationClient;
    private RecyclerView jobsRecyclerView;
    private JobAdapter adapter;
    private Button Notificaion;

    private Button JobBoard;
    private FirebaseCrud firebaseCrud;
    FirebaseCrud crud = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_landing);

//        Toolbar employeeToolbar = (Toolbar) findViewById(R.id.employee_toolbar);
//        setSupportActionBar(employeeToolbar);

        // Initialize FusedLocationProviderClient
        locationTextView = findViewById(R.id.location_text_view);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        checkLocationPermissionAndGetLocation();
        // Removed the unnecessary Object declaration and re-declaration of firebaseCrud
        crud = new FirebaseCrud(FirebaseDatabase.getInstance());
    }

    //
    private void checkLocationPermissionAndGetLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            getLastLocation();
        }

        jobsRecyclerView = findViewById(R.id.jobRecycler2);
        jobsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Notificaion = findViewById(R.id.notification_btn);
        JobBoard = findViewById(R.id.job_board_btn);

        Notificaion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmployeeLanding.this, JobNotification.class);
                startActivity(intent);
            }
        });

        JobBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmployeeLanding.this, EmployeeLanding.class);
                startActivity(intent);
            }
        });

        FirebaseApp.initializeApp(this);

        // Initialize database access and Firebase CRUD operations
        initializeDatabaseAccess();

        fetchJobsAndUpdateUI("");
        Button jobBoardBtn = findViewById(R.id.job_board_btn);
        Button notificationButton = findViewById(R.id.notification_btn);
        Button profileBtn = findViewById(R.id.profile_btn);

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the new activity
                Intent intent = new Intent(EmployeeLanding.this, EmployeeProfile.class);
                startActivity(intent);
            }
        });
        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the new activity
                Intent intent = new Intent(EmployeeLanding.this, JobNotification.class);
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
                    intent = new Intent(EmployeeLanding.this, EmployeeLanding.class);
                } else{
                    intent = new Intent(EmployeeLanding.this, EmployerLanding.class);
                }
                startActivity(intent);
            }
        });

        Button searchBtn=findViewById(R.id.searchBtn);
        EditText searchBox=findViewById(R.id.searchBox);
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    EditText searchBox=findViewById(R.id.searchBox);
                    String searchText=searchBox.getText().toString();
                    fetchJobsAndUpdateUI(searchText);
                } else if(s.toString().contains(" ")){
                    EditText searchBox=findViewById(R.id.searchBox);
                    String searchText=searchBox.getText().toString().trim();
                    fetchJobsAndUpdateUI(searchText);
                }
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText searchBox=findViewById(R.id.searchBox);
                String searchText=searchBox.getText().toString();

                fetchJobsAndUpdateUI(searchText);
            }
        });
    }

    private void fetchJobsAndUpdateUI(String searchText) {

        crud.fetchAllJobs(searchText,new FirebaseCrud.JobPostingsResultCallback() {
            @Override
            public void onJobPostingsRetrieved(List<JobPosting> jobPostings) {
                // Update the RecyclerView with the list of jobs
                adapter = new JobAdapter(jobPostings, EmployeeLanding.this);
                jobsRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onError(Exception e) {
                // Handle any errors
                Toast.makeText(EmployeeLanding.this, "Error fetching jobs: " + e.getMessage(), Toast.LENGTH_LONG).show();
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getLastLocation();
        }
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            String locationStr = "Latitude: " + location.getLatitude() + "\nLongitude: " + location.getLongitude();
                            //String locationStr = "Latitude: " + "44.6357" + "\nLongitude: " + "-63.5952";
                            crud.setLocation(locationStr);
                            locationTextView.setText(locationStr);
                        } else {
                            locationTextView.setText("Location not available");
                        }
                    }
                });
    }
}
