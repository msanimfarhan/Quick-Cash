package com.example.quickcash;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register {

    protected boolean isValidEmailAddress(String emailAddress) {
        if(emailAddress == null){
            return false;
        }
        String regexEmail = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(regexEmail);
        Matcher matcher = pattern.matcher(emailAddress);
        return matcher.matches();
    }

}
