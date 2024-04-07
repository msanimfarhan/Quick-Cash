package com.example.quickcash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class EmployeeProfile extends AppCompatActivity {
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
                } else{
                    intent = new Intent(EmployeeProfile.this, EmployerLanding.class);
                }
                startActivity(intent);
            }
        });
    }

}
