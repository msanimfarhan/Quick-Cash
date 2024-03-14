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
import com.google.firebase.database.FirebaseDatabase;

import android.location.Location;
import android.widget.TextView;


public class employee_landing extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationClient;
    private TextView locationTextView;
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://quick-cash-c28b9-default-rtdb.firebaseio.com/");
    FirebaseCrud crud = new FirebaseCrud(database);

    private FirebaseCrud firebaseCrud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_landing);
        Toolbar employeeToolbar = (Toolbar) findViewById(R.id.employee_toolbar);
        setSupportActionBar(employeeToolbar);

        // Initialize FusedLocationProviderClient
        locationTextView = findViewById(R.id.location_text_view);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        checkLocationPermissionAndGetLocation();
        FirebaseCrud firebaseCrud = new FirebaseCrud(FirebaseDatabase.getInstance());
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
                            //String locationStr = "Latitude: " + location.getLatitude() + "\nLongitude: " + location.getLongitude();
                            String locationStr = "Latitude: " + "44.6357" + "\nLongitude: " + "-63.5952";
                            crud.setLocation(locationStr);
                            locationTextView.setText(locationStr);
                        } else {
                            locationTextView.setText("Location not available");
                        }
                    }
                });
    }



}