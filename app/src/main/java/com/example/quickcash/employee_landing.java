package com.example.quickcash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

//
import android.Manifest;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import android.location.Location;



public class employee_landing extends AppCompatActivity{

    private FusedLocationProviderClient fusedLocationClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_landing);
        Toolbar employeeToolbar = (Toolbar) findViewById(R.id.employee_toolbar);
        setSupportActionBar(employeeToolbar);

        // Initialize FusedLocationProviderClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        checkLocationPermissionAndGetLocation();
    }

    //
    private void checkLocationPermissionAndGetLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
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
        /*else {
            //

        }*/
    }
    private void getLastLocation() {
        /*fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {

                        }
                    }
                });*/
    }



}