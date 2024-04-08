package com.example.quickcash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class JobApplicantsDetails extends AppCompatActivity {
    private TextView name;
    private TextView applicantEmail;
    private TextView phone;
    private String jobId;
    private boolean isHired;

    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_applicants_details);
        Intent getIntent = getIntent();

        String applicantsEmail = getIntent.getStringExtra("applicantsEmail");
        String jobId = getIntent.getStringExtra("jobId");
        this.jobId = jobId;
        String payment = getIntent.getStringExtra("payment");

        Button hireBtn = findViewById(R.id.hireBtn_applicantDetails);
        Button payBtn = findViewById(R.id.paybtn_applicantDetails);
        Intent intent = getIntent();

        name = findViewById(R.id.applicantsName);
        applicantEmail = findViewById(R.id.applicantsEmail);
        phone = findViewById(R.id.applicantsPhone);

//        TextView phone = findViewById(R.id.applicantPhone);
        String emailString = intent.getStringExtra("applicantsEmail");
        String nameString = intent.getStringExtra("name");
        String phoneStr = intent.getStringExtra("phone");
        name.setText(nameString);
        applicantEmail.setText(emailString);
        phone.setText(phoneStr);

        fetchHiringInfo(jobId, new HiringInfoCallback() {
            @Override
            public void onHiringInfoFetched(boolean isHired) {
                // Handle the fetched hiring info here
                if (isHired) {
                    hireBtn.setVisibility(View.GONE);
                    payBtn.setVisibility(View.VISIBLE);

                } else {
                    hireBtn.setVisibility(View.VISIBLE);
                    payBtn.setVisibility(View.GONE);

                }
            }
        });

        hireBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(JobApplicantsDetails.this, "Applicant Hired Successfully!", Toast.LENGTH_SHORT).show();
                hireBtn.setVisibility(View.GONE);
                payBtn.setVisibility(View.VISIBLE);
            }
        });
        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobApplicantsDetails.this, Paypal.class);
                intent.putExtra("applicantsEmail", applicantsEmail);
                intent.putExtra("jobId", jobId);
                intent.putExtra("payment", payment);
                startActivity(intent);
            }
        });
        Button jobBoardBtn = findViewById(R.id.job_board_btn);
        Button notificationButton = findViewById(R.id.notification_btn);
        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the new activity
                Intent intent = new Intent(JobApplicantsDetails.this, JobNotification.class);
                startActivity(intent);
            }
        });
        jobBoardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the new activity
                SharedPreferences sharedPref = getSharedPreferences("userDetails", Context.MODE_PRIVATE);
                String userRole = sharedPref.getString("userRole", "");
                Intent intent;
                if (userRole.equals("Employee")) {
                    intent = new Intent(JobApplicantsDetails.this, EmployeeLanding.class);
                } else{
                    intent = new Intent(JobApplicantsDetails.this, EmployerLanding.class);
                }
                startActivity(intent);
            }
        });
    }


    public String getApplicantEmail() {
        return applicantEmail.getText().toString();
    }

    public void fetchHiringInfo(String jobId, HiringInfoCallback callback) {
        Query databaseReference = FirebaseDatabase.getInstance().getReference("AllJobs").child(jobId).child("applicants").orderByChild("email").equalTo(getApplicantEmail());

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean isHiredRet = false; // Default value
                if (dataSnapshot.exists()) {
                    for (DataSnapshot applicantSnapshot : dataSnapshot.getChildren()) {
                        Boolean isHired = applicantSnapshot.child("isHired").getValue(Boolean.class);
                        if (isHired != null && isHired) {
                            isHiredRet = true;
                            break;
                        }
                    }
                }
                callback.onHiringInfoFetched(isHiredRet);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled event if needed
                callback.onHiringInfoFetched(false);
            }
        });
    }

    public interface HiringInfoCallback {
        void onHiringInfoFetched(boolean isHired);
    }


}
