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
    protected boolean isValidPassword(String password) {
        if(password == null){
            return false;
        }
        String regexEmail = "^.{8,}$";
        Pattern pattern = Pattern.compile(regexEmail);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    protected boolean ifBothPasswordMatches(String password1, String password2) {
        if(password1 == null || password2 == null){
            return false;
        }
        if(password1.equals(password2)){
            return true;
        }else{
            return false;
        }
    }



}
