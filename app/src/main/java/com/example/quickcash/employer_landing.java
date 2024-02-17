package com.example.quickcash;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class employer_landing extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emplyer_landing);
        Toolbar employeeToolbar = (Toolbar) findViewById(R.id.employee_toolbar);
        setSupportActionBar(employeeToolbar);
    }
}
