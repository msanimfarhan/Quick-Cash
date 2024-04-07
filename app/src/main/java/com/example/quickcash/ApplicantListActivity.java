package com.example.quickcash;

import android.os.Bundle;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ApplicantListActivity extends AppCompatActivity {

    private RecyclerView recyclerViewApplicants;
    private ApplicantAdapter applicantAdapter;
    private FirebaseCrud firebaseCrud;
    private List<Applicant> applicantsList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.applicant_list); // Replace with your layout filename if different

        recyclerViewApplicants = findViewById(R.id.recyclerViewApplicants);
        recyclerViewApplicants.setLayoutManager(new LinearLayoutManager(this));
        // The adapter will be set once the data is fetched
        // Initialize the adapter with an empty list
         applicantAdapter = new ApplicantAdapter(new ArrayList<>(), this);
        recyclerViewApplicants.setAdapter(applicantAdapter);

        // Now, fetch the applicants
        firebaseCrud = new FirebaseCrud(FirebaseDatabase.getInstance());

        // Now, fetch the applicants
        String jobId = getIntent().getStringExtra("jobId");
        String payment = getIntent().getStringExtra("payment");
        if (jobId != null) {
            firebaseCrud.fetchApplicantsForJob(jobId, new FirebaseCrud.ApplicantsResultCallback() {
                @Override
                public void onApplicantsRetrieved(List<Applicant> applicants) {
                    // Update the adapter with the list of applicants
                    applicantAdapter.setApplicantsList(applicants,jobId,payment);
                    applicantAdapter.notifyDataSetChanged();
                }

                @Override
                public void onError(Exception e) {
                    Toast.makeText(ApplicantListActivity.this, "Error fetching applicants", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

}
