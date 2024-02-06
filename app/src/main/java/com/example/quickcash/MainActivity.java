package com.example.quickcash;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textView;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setupRegistrationButton();

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
    protected void setStatusMessage(String option,String message) {

        if(option.equals("email")){
            TextView statusLabel2 = findViewById(R.id.invalidEmailMessage);
            statusLabel2.setText(message.trim());
        } else if(option.equals("passValid")){
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
      String confirmPassword = getConfirmPassword();
      String errorMessageForEmail = "";
      String errorMessageForStrongPass = "";
      String errorMessageForBothPass = "";
      Boolean registrationValid=true;
      Register register = new Register();

      // Check if the email is valid or not
      if(!register.isValidEmailAddress(email)){
            errorMessageForEmail= getResources().getString(R.string.INVALID_EMAIL_ADDRESS).trim();
          registrationValid=false;
      }else{
          errorMessageForEmail=getResources().getString(R.string.VALID_EMAIL_ADDRESS).trim();
      }
        setStatusMessage("email",errorMessageForEmail);

    //      Check if password is strong enough
      if(!register.isValidPassword(password)){
          errorMessageForStrongPass= getResources().getString(R.string.INVALID_PASSWORD).trim();
          registrationValid=false;
      } else{
          errorMessageForStrongPass= getResources().getString(R.string.VALID_PASSWORD).trim();
      }
        setStatusMessage("passValid",errorMessageForStrongPass);

      //      Check if both password matches
      if(!register.ifBothPasswordMatches(password,confirmPassword)){
          errorMessageForBothPass= getResources().getString(R.string.PASSWORD_NOT_MATCHING).trim();
          registrationValid=false;
      } else{
          errorMessageForBothPass= getResources().getString(R.string.PASSWORD_MATCHING).trim();
      }
        setStatusMessage("passMatch",errorMessageForBothPass);

      if(registrationValid){
          Toast.makeText(this,"Registration Successfull!",Toast.LENGTH_SHORT).show();
      }

    }

}
