package com.example.quickcash;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {


    public static String WELCOME_MESSAGE = "ca.dal.csci3130.Qick Cash";
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://quick-cash-c28b9-default-rtdb.firebaseio.com/");
    FirebaseCrud crud = null;
    private DatabaseReference firebaseDBRef;

    private TextView textView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        FirebaseApp.initializeApp(this);
        this.setupRegistrationButton();
        Spinner roleSelectionSpinner = findViewById(R.id.signupRoleSelection);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.roleSelection, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleSelectionSpinner.setAdapter(adapter);
        roleSelectionSpinner.setOnItemSelectedListener(this);
        setupLoginButton();

    }

    private void connectFirebase() {
        firebaseDBRef = database.getReference("message");
    }

    protected void initializeDatabaseAccess() {
        database = FirebaseDatabase.getInstance(getResources().getString(R.string.FIREBASE_DB_URL));
        crud = new FirebaseCrud(database);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    protected void setupRegistrationButton() {
        Button registerButton = findViewById(R.id.signupBtn);
        registerButton.setOnClickListener(this);
    }


    protected String getEmail() {
        EditText emailBox = findViewById(R.id.signupEmail);
        return emailBox.getText().toString();
    }

    protected String getRole() {
        Spinner roleSpinner = findViewById(R.id.signupRoleSelection);
        return roleSpinner.getSelectedItem().toString();
    }

    protected String getName() {
        EditText emailBox = findViewById(R.id.signupName);
        return emailBox.getText().toString();
    }

    protected String getPassword() {
        EditText passwordBox = findViewById(R.id.signupPass);
        return passwordBox.getText().toString();
    }

    protected String getConfirmPassword() {
        EditText passwordBox = findViewById(R.id.signupConfirmPass);
        return passwordBox.getText().toString();
    }

    protected void move2employer(){
        Intent emplyerIntent = new Intent(this, EmployerLanding.class);
        startActivity(emplyerIntent);
    }

    protected void move2employee(){
        Intent employeeIntent = new Intent(this, EmployeeLanding.class);
        startActivity(employeeIntent);
    }

    //    protected void saveInfoToFirebase(String name, String emailAddress, String role, String pass) {
//        if (crud != null) {
//
//            crud.setName(name);
//            crud.setEmail(emailAddress);
//            crud.setRole(role);
//            crud.setPass(pass);
//        } else {
//
//            Toast.makeText(this, "Database connection not initialized", Toast.LENGTH_SHORT).show();
//        }
//    }
    protected void saveInfoToFirebase(String name, String email, String role, String pass, String locationString) {

        //Incomplete method, add your implementation
        Map<String, String> user = new HashMap<>();
        user.put("email", email);
        user.put("name", name);
        user.put("role", role);
        user.put("password", pass);
        user.put("location", locationString);
        String sanitizedEmail=email.replace(".", ",");
        firebaseDBRef = FirebaseDatabase.getInstance().getReference("Users");
        firebaseDBRef.child(sanitizedEmail).setValue(user).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Data save was successful!
                // Handle the success (e.g., notify the user or update the UI)
                Toast.makeText(RegisterActivity.this, "Successful", Toast.LENGTH_SHORT).show();
            } else {
                // Data save failed.
                // Handle the failure (e.g., notify the user or update the UI)
                Toast.makeText(RegisterActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }


    protected void setStatusMessage(String option, String message) {

        if (option.equals("email")) {
            TextView statusLabel2 = findViewById(R.id.invalidEmailMessage);
            statusLabel2.setText(message.trim());

        } else if (option.equals("passValid")) {
            TextView statusLabel2 = findViewById(R.id.invalidPassMsg);
            statusLabel2.setText(message.trim());

        } else if (option.equals("passMatch")) {
            TextView statusLabel2 = findViewById(R.id.passMatchMsg);
            statusLabel2.setText(message.trim());
            // commit
        } else if (option.equals("role")) {
            TextView statusLabel2 = findViewById(R.id.invalidRoleMsg);
            statusLabel2.setText(message.trim());

        }
    }


    @Override
    public void onClick(View v) {
        String email = getEmail();
        String name = getName();
        String password = getPassword();
        String role = getRole();
        String confirmPassword = getConfirmPassword();
        Boolean registrationValid = true;
        Register register = new Register();

        // Error Messages
        String errorMessageForEmail = "";
        String errorMessageForStrongPass = "";
        String errorMessageForBothPass = "";
        String errorMessageForRole = "";


        // Resetting Every error message
        setStatusMessage("email", "");
        setStatusMessage("passValid", "");
        setStatusMessage("passMatch", "");
        setStatusMessage("role","");


        // Check if the email is valid or not
        if (!register.isValidEmailAddress(email)) {
            errorMessageForEmail = getResources().getString(R.string.INVALID_EMAIL_ADDRESS).trim();
            registrationValid = false;  // If false registration wont be allowed
            setStatusMessage("email", errorMessageForEmail);
        }

        //Check if password is strong
        if (!register.isValidPassword(password)) {
            errorMessageForStrongPass = getResources().getString(R.string.INVALID_PASSWORD).trim();
            registrationValid = false;  // If false registration wont be allowed
            setStatusMessage("passValid", errorMessageForStrongPass);
        }

        // Check if both password matches
        if (!register.ifBothPasswordMatches(password, confirmPassword)) {
            errorMessageForBothPass = getResources().getString(R.string.PASSWORD_NOT_MATCHING).trim();
            registrationValid = false;  // If false registration wont be allowed
            setStatusMessage("passMatch", errorMessageForBothPass);
        }

        if(!register.validRole(role)) {
            errorMessageForRole = getResources().getString(R.string.INVALID_ROLE).trim();
            registrationValid = false;
            setStatusMessage("role", errorMessageForRole);
        }

        // Allow user to register if everything is valid
        if (registrationValid) {
            String locationString = "Latitude: 44.6357, Longitude: -63.5952";
            this.saveInfoToFirebase(name, email, role, password, locationString);
            if(role.equals("Employee")) {
                move2employee();
            } else if (role.equals("Employer")) {
                move2employer();
            }
        }
    }

    private void setupLoginButton() {
        Button loginButton = findViewById(R.id.login_onRegister);

        // Set onClick listener for the login button.
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RegisterActivity.this, "Register Clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);

                startActivity(intent);
            }
        });
    }


}

