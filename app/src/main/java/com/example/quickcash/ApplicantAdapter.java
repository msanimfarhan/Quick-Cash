package com.example.quickcash;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ApplicantAdapter extends RecyclerView.Adapter<ApplicantAdapter.ApplicantViewHolder> {
    private List<Applicant> applicantsList;
    private final Context mContext;
    private String jobId;
    private String payment;
    private String applicantsEmail;

    public ApplicantAdapter(List<Applicant> applicantsList, Context mContext) {
        this.applicantsList = applicantsList;
        this.mContext = mContext;
    }

    public String getJobId() {
        return this.jobId;
    }

    public String getApplicantsEmail() {
        return applicantsEmail;
    }

    public void setApplicantsEmail(String applicantsEmail) {
        this.applicantsEmail = applicantsEmail;
    }

    public String getPayment() {
        return this.payment;
    }

    @NonNull
    @Override
    public ApplicantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.applicant_item, parent, false);
        return new ApplicantViewHolder(itemView);
    }

    public void setApplicantsList(List<Applicant> newApplicantsList, String jobId, String payment) {
        this.applicantsList.clear(); // Clear the existing data
        this.applicantsList.addAll(newApplicantsList); // Add the new data
        this.jobId = jobId;
        this.payment = payment;
        notifyDataSetChanged(); // Notify the adapter that the data has changed

    }

    @Override
    public void onBindViewHolder(@NonNull ApplicantViewHolder holder, int position) {
        Applicant applicant = applicantsList.get(position);
        holder.nameTextView.setText(applicant.getName());
        holder.emailTextView.setText(applicant.getEmail());
        holder.phoneTextView.setText(applicant.getPhoneNumber());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, JobApplicantsDetails.class);
                intent.putExtra("name", holder.nameTextView.getText());
                intent.putExtra("applicantsEmail", holder.emailTextView.getText());
                intent.putExtra("phone", holder.phoneTextView.getText());
                intent.putExtra("jobId", getJobId());
                intent.putExtra("payment", getPayment());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return applicantsList.size();
    }

    public class ApplicantViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView emailTextView;
        TextView phoneTextView;
        Button hireBtn;
        Button paybtn;
        LinearLayout singleApplicant;
        FirebaseCrud crud;

        public ApplicantViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.applicantName);
            emailTextView = itemView.findViewById(R.id.applicantEmail);
            phoneTextView = itemView.findViewById(R.id.applicantPhone);
            setApplicantsEmail(emailTextView.getText().toString());

            singleApplicant = itemView.findViewById(R.id.singleApplicant);


        }



    }
}
