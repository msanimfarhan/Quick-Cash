package com.example.quickcash;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

public class employee_landing extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_landing);
        Toolbar employeeToolbar = (Toolbar) findViewById(R.id.employee_toolbar);
        setSupportActionBar(employeeToolbar);
    }



}