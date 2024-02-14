package com.example.quickcash;
import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseCrud {

    private FirebaseDatabase database;
    private DatabaseReference userNameRef= null;
    private DatabaseReference passwordRef = null;

    private String extractedUserName;
    private String extractedPassword;

    public FirebaseCrud(FirebaseDatabase database){
        this.database =database;
        this.initializeDatabaseRefs();
        this.initializeDatabaseRefListeners();
    }

    protected void initializeDatabaseRefs(){
        this.userNameRef= getUserNameRef();
        this.passwordRef=getPasswordRef();

    }

    protected  void initializeDatabaseRefListeners(){
        this.setUserNameListener();
        this.setPasswordListener();
    }

    protected  DatabaseReference getUserNameRef(){
        return this.database.getReference("UserName");
    }

    protected  DatabaseReference getPasswordRef(){
        return this.database.getReference("Password");
    }

    protected  void setUserName(String name){
        if(userNameRef!=null){
            userNameRef.setValue(name);
        }
    }

    protected  void setPassword(String password){
        if(passwordRef!=null){
            passwordRef.setValue(password);
        }
    }

    protected void setUserNameListener() {
        this.userNameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //incomplete method, add your implementation
                if (snapshot.exists()) {
                    extractedUserName = snapshot.getValue(String.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    protected void setPasswordListener() {
        this.getPasswordRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //incomplete method, add your implementation
                if (snapshot.exists()) {
                    extractedPassword = snapshot.getValue(String.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}
