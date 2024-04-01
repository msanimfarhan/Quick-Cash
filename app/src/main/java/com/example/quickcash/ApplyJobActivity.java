package com.example.quickcash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

public class ApplyJobActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextName;
    private EditText editTextPhoneNumber;
    private Button buttonApply;
    private FirebaseCrud firebaseCrud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_job);
        Intent intent=getIntent();

        // Initialize Firebase
        FirebaseApp.initializeApp(this);

        // Initialize FirebaseCrud instance
        firebaseCrud = new FirebaseCrud(FirebaseDatabase.getInstance());

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextName = findViewById(R.id.editTextName);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        buttonApply = findViewById(R.id.buttonApply);

        buttonApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitApplication(); // Call your method when button is clicked
            }
        });

        // If you're expecting to receive a jobId, make sure to handle it here
        // For example:
        // String jobId = getIntent().getStringExtra("jobId");
        Button jobBoardBtn = findViewById(R.id.job_board_btn);
        Button notificationButton = findViewById(R.id.notification_btn);
        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the new activity
                Intent intent = new Intent(ApplyJobActivity.this, JobNotification.class);
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
                    intent = new Intent(ApplyJobActivity.this, employee_landing.class);
                } else{
                    intent = new Intent(ApplyJobActivity.this, employer_landing.class);
                }
                startActivity(intent);
            }
        });
    }

    private void submitApplication() {
        String email = editTextEmail.getText().toString().trim();
        String name = editTextName.getText().toString().trim();
        String phoneNumber = editTextPhoneNumber.getText().toString().trim();

        // Replace "jobId" with the actual job ID you're applying for
        String jobId = getIntent().getStringExtra("jobId"); // This should be retrieved from the Intent or other sources

        if (email.isEmpty() || name.isEmpty() || phoneNumber.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        // Use the FirebaseCrud instance to submit the job application
        firebaseCrud.applyForJob(jobId, email, name, phoneNumber, new FirebaseCrud.JobApplicationResultCallback() {
            @Override
            public void onApplicationSuccess(String result) {
                Toast.makeText(ApplyJobActivity.this, result, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onApplicationFailure(String errorMessage) {
                Toast.makeText(ApplyJobActivity.this, "Failed to apply: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
