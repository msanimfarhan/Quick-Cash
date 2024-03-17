package com.example.quickcash;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class employer_landing extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationClient;
    private TextView locationTextView;
    //
    private DatabaseReference databaseReference;
    private String userEmail = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emplyer_landing);
        Toolbar employeeToolbar = (Toolbar) findViewById(R.id.employee_toolbar);
        setSupportActionBar(employeeToolbar);

        // Initialize Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String sanitizedEmail = userEmail.replace(".", ",");
        databaseReference = database.getReference("Users").child(sanitizedEmail);

        // Initialize FusedLocationProviderClient
        locationTextView = findViewById(R.id.location_text_view);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        checkLocationPermissionAndGetLocation();
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
    }
}
