package com.example.quickcash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    // Initializing the FirebaseCrud object for CRUD operations.
    FirebaseCrud crud = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase
        FirebaseApp.initializeApp(this);

        // Initialize database access and Firebase CRUD operations
        initializeDatabaseAccess();

        // Setup login button listener
        setupLoginButtonListener();
        setupRegisterButton();
    }

    // Method to initialize Firebase database access and CRUD operations.
    protected void initializeDatabaseAccess() {
        // Assuming you have a string resource named FIREBASE_DB_URL with the Firebase database URL.
        FirebaseDatabase database = FirebaseDatabase.getInstance(getResources().getString(R.string.FIREBASE_DB_URL));
        crud = new FirebaseCrud(database);
    }

    // Method to setup listener for the login button.
    private void setupLoginButtonListener() {
        Button loginButton = findViewById(R.id.login_button);

        // Set onClick listener for the login button.
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Login info will be matched here",Toast.LENGTH_LONG);
            }
        });
    }



    // Method to get username from EditText.
    protected String getUsername() {
        EditText username = findViewById(R.id.username);
        return username.getText().toString();
    }

    // Method to get password from EditText.
    protected String getPassword() {
        EditText password = findViewById(R.id.password);
        return password.getText().toString();
    }

    //// work of moving to register page when register button is clicked
    protected void move2RegisterPage() {

        //Incomplete method, add your implementation
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);

        startActivity(intent);

    }


    private void setupRegisterButton() {
        Button loginButton = findViewById(R.id.register_button);

        // Set onClick listener for the login button.
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Register Clicked",Toast.LENGTH_SHORT).show();
                move2RegisterPage();
            }
        });
    }
}
