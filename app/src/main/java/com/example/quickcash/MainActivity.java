package com.example.quickcash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDB;
    private DatabaseReference firebaseDBRef;
    private static final String FIREBASE_URL = "https://fir-demo-cce3c-default-rtdb.firebaseio.com/";
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        textView = findViewById(R.id.textView);
        connectFirebase();
        writeToFirebaseDB();
        listenToDataChanges();
    }

    private void listenToDataChanges(){
        // Add a ValueEventListener to listen for changes in the data at the ref
        firebaseDBRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Retrieve the value from the DataSnapshot
                final String readValue = snapshot.getValue(String.class);
                // Set the text of the TextView to the retrieved value
                textView.setText("Success:"+readValue);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle any errors that occur during data retrieval
                final String errorValue = error.getMessage();
                textView.setText("Error: "+errorValue);
            }
        });
    }
    private void connectFirebase(){
        // Initialize the Firebase database instance with the specified URL
        firebaseDB = FirebaseDatabase.getInstance(FIREBASE_URL);
        // Get a reference to the "message" node in the database
        firebaseDBRef = firebaseDB.getReference("message");
    }

    private void writeToFirebaseDB(){
        // Set the value of the "message" node to "Hello CSCI3130"
        firebaseDBRef.setValue("Hello CSCI3130");
    }
}
