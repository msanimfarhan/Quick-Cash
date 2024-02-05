package com.example.quickcash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textView;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setupRegistrationButton();
        textView = findViewById(R.id.textView);

    }
    protected void setupRegistrationButton() {
        Button registerButton = findViewById(R.id.signupBtn);
        registerButton.setOnClickListener(this);

    }



    protected String getEmail(){
      EditText emailBox = findViewById(R.id.signupEmail);
      return emailBox.getText().toString();
    }

    protected String getName(){
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
    protected void setStatusMessage(String message) {
        TextView statusLabel = findViewById(R.id.statusLabel);
        statusLabel.setText(message.trim());
    }




    @Override
    public void onClick(View v) {
      String email = getEmail();
      String name = getName();
      String password = getPassword();
      String confirmPassword = getConfirmPassword();
      String errorMessage = null;
      Register register = new Register();

      if(!register.isValidEmailAddress(email)){
            errorMessage= getResources().getString(R.string.INVALID_EMAIL_ADDRESS).trim();
      }
        setStatusMessage(errorMessage);




    }

}
