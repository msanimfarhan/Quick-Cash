package com.example.quickcash;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class LandingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get user role
        String userRole = getIntent().getStringExtra("userRole");

        if (userRole != null) {
            if (userRole.equals("Employee")) {
                setContentView(R.layout.employee_landing);
            } else if (userRole.equals("Employer")) {
                setContentView(R.layout.emplyer_landing);
            }
        }
    }
}

