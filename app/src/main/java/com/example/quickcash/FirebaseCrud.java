package com.example.quickcash;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseCrud {
    private FirebaseDatabase database;

    public FirebaseCrud(FirebaseDatabase database) {
        this.database = database;
        this.initializeDatabaseRefs();
        this.initializeDatabaseRefListeners();
    }

    protected void initializeDatabaseRefs() {
        //Incomplete method, add your implementation
        this.nameRef = getNameRef();
        this.emailRef = getEmailRef();
        this.roleRef = getRoleRef();
        this.passRef = getPassRef();

    }

    protected void initializeDatabaseRefListeners() {
        //Incomplete method, add your implementation
        this.setNameListener();
        this.setEmailListener();
        this.setRoleListener();
        this.setPassListener();

    }

    private DatabaseReference nameRef = null;

    private DatabaseReference emailRef = null;
    private DatabaseReference roleRef = null;

    private DatabaseReference passRef = null;

    private String extractedName;
    private String extractedEmailAddress;
    private String extractedRole;
    private String extractedPass;

    protected DatabaseReference getNameRef() {
        return this.database.getReference("Name");
    }

    protected void setName(String name) {
        //incomplete method, add your implementation
        if (nameRef != null) {
            nameRef.setValue(name);
        }

    }


    protected DatabaseReference getEmailRef() {
        return this.database.getReference("email");
    }

    protected void setEmail(String email) {

        if (emailRef != null) {
            emailRef.setValue(email);
        }

    }


    protected DatabaseReference getRoleRef() {
        return this.database.getReference("role");
    }

    protected void setRole(String role) {
        //incomplete method, add your implementation
        if (roleRef != null) {
            roleRef.setValue(role);
        }

    }


    protected DatabaseReference getPassRef() {
        return this.database.getReference("password");
    }

    protected void setPass(String pass) {
        //incomplete method, add your implementation
        if (passRef != null) {
            passRef.setValue(pass);
        }

    }

    protected void setNameListener() {
        this.nameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //incomplete method, add your implementation
                if (snapshot.exists()) {
                    extractedName = snapshot.getValue(String.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    protected void setEmailListener() {
        this.emailRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    extractedEmailAddress = snapshot.getValue(String.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle potential errors
            }
        });
    }


    protected void setRoleListener() {
        this.roleRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    extractedRole = snapshot.getValue(String.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    protected void setPassListener() {
        this.passRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    extractedPass = snapshot.getValue(String.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }





}

