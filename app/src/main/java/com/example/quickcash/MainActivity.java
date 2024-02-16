package com.example.quickcash;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

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
                saveUserInfo();
            }
        });
    }

    // Method to save user information to Firebase.
    protected void saveUserInfo() {
        String userName = getUsername();
        String password = getPassword();

        // Validate if the username is an email
        if (!isValidEmail(userName)) {
            Toast.makeText(this, "Please enter a valid email address as username.", Toast.LENGTH_SHORT).show();
        } else if (password.length() < 8) {
            Toast.makeText(this, "Password must be at least 8 characters long.", Toast.LENGTH_SHORT).show();
        } else {
            // Assuming CRUD operations are successful
            if (crud != null) {
                crud.setUserName(userName);
                crud.setPassword(password);
                // Show a toast message for successful operation
                Toast.makeText(this, "Bazinga! You have entered.", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
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
}
