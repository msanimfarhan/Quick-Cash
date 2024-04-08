package com.example.quickcash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EmployeeProfile extends AppCompatActivity {
    EmployeeInfo employee = new EmployeeInfo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_profile);
        Button jobBoardBtn = findViewById(R.id.job_board_btn);
        Button notificationButton = findViewById(R.id.notification_btn);
        Button profileBtn = findViewById(R.id.profile_btn);
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the new activity
                Intent intent = new Intent(EmployeeProfile.this, EmployeeProfile.class);
                startActivity(intent);
            }
        });
        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the new activity
                Intent intent = new Intent(EmployeeProfile.this, JobNotification.class);
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
                    intent = new Intent(EmployeeProfile.this, EmployeeLanding.class);
                } else {
                    intent = new Intent(EmployeeProfile.this, EmployerLanding.class);
                }
                startActivity(intent);
            }
        });
        SharedPreferences sharedPref = getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        String email=sharedPref.getString("userEmail","");
        Log.i("email",email);
        fetchUserInfo(email);
    }

    public void fetchUserInfo(String email) {

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(email);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i("snapshot", String.valueOf(snapshot));
                if (snapshot.exists()) {
                    employee.setName(snapshot.child("name").getValue(String.class));
                    employee.setEmail(snapshot.child("email").getValue(String.class));
                    employee.setRole(snapshot.child("role").getValue(String.class));
                    if (snapshot.child("accountBalance").exists()) {
                        employee.setAccountBalance(snapshot.child("accountBalance").getValue(Integer.class));
                    } else {
                        employee.setAccountBalance(0);
                    }
                    if (snapshot.child("totalJobsCompleted").exists()) {

                        employee.setTotalJobsCompleted(snapshot.child("totalJobsCompleted").getValue(Integer.class));
                    } else {
                        employee.setTotalJobsCompleted(0);
                    }
                    updateUi(employee);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void updateUi(EmployeeInfo employee) {
        // Set values for TextViews
        TextView nameTextView = findViewById(R.id.name_employeeInfo);
        nameTextView.setText(employee.getName());

        TextView emailTextView = findViewById(R.id.email_employeeInfo);
        emailTextView.setText(employee.getEmail());

        TextView roleTextView = findViewById(R.id.role_employeeInfo);
        roleTextView.setText(employee.getRole());

        TextView totalJobsCompletedTextView = findViewById(R.id.totalJobsCompleted_employeeInfo);
        totalJobsCompletedTextView.setText(String.valueOf(employee.getTotalJobsCompleted()));

        TextView accountBalanceTextView = findViewById(R.id.accountBalance_employeeInfo);
        accountBalanceTextView.setText("$" + String.valueOf(employee.getAccountBalance()) + " CAD");
    }


}
