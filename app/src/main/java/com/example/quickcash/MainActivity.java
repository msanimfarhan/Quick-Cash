package com.example.quickcash;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    public static String WELCOME_MESSAGE = "ca.dal.csci3130.Qick Cash";
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://quick-cash-c28b9-default-rtdb.firebaseio.com/");
    FirebaseCrud crud = null;
    private DatabaseReference firebaseDBRef;

    private TextView textView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        this.setupRegistrationButton();
        this.initializeDatabaseAccess();

    }
    private void connectFirebase(){
        firebaseDBRef = database.getReference("message");
    }

    protected void initializeDatabaseAccess() {
        database = FirebaseDatabase.getInstance(getResources().getString(R.string.FIREBASE_DB_URL));
        crud = new FirebaseCrud(database);
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
        EditText emailBox = findViewById(R.id.signupEmail);
        return emailBox.getText().toString();
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


    protected void saveInfoToFirebase(String name, String emailAddress, String role, String pass) {
        if (crud != null) {

            crud.setName(name);
            crud.setEmail(emailAddress);
            crud.setRole(role);
            crud.setPass(pass);
        } else {

            Toast.makeText(this, "Database connection not initialized", Toast.LENGTH_SHORT).show();
        }
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


        // Resetting Every error message
        setStatusMessage("email", "");
        setStatusMessage("passValid", "");
        setStatusMessage("passMatch", "");


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

        // Allow user to register if everything is valid
        if (registrationValid) {
            Toast.makeText(this, "Registration Successfully!", Toast.LENGTH_SHORT).show();
        }
        this.saveInfoToFirebase( name, email, role ,password);
    }

}
