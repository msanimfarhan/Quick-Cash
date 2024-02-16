package com.example.quickcash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText passwordEditText;
    private FirebaseCrud firebaseCrud;

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
        setupUI();
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
                attemptLogin();

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
                Toast.makeText(MainActivity.this, "Register Clicked", Toast.LENGTH_SHORT).show();
                move2RegisterPage();
            }
        });
    }


    // Code FOr Login
    private void setupUI() {
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
    }

    private void attemptLogin() {
        String email = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Ensure that the email and password fields are not empty before attempting to login
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(MainActivity.this, "Email and password must not be empty", Toast.LENGTH_LONG).show();
            return;
        } else {

            verifyUserLogin(email, password);
        }


    }

    public void verifyUserLogin(String email, String pass) {

        //incomplete method, add your implementation
        Task<DataSnapshot> reference = FirebaseDatabase.getInstance().getReference().child("Users").child("jahid").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                DataSnapshot dataSnapshot = task.getResult();
                String DBemail = String.valueOf(dataSnapshot.child("email").getValue());
                String DBpassword = String.valueOf(dataSnapshot.child("password").getValue());


                if (email.equals(DBemail) && pass.equals(DBpassword)) {

                    Toast.makeText(MainActivity.this, "Login successfull", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();

                }

            }


        });
    }


}
