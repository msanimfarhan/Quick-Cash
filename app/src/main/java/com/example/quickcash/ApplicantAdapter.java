package com.example.quickcash;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ApplicantAdapter extends RecyclerView.Adapter<ApplicantAdapter.ApplicantViewHolder> {
    private List<Applicant> applicantsList;
    private final Context mContext;
    private String jobId;
    private String payment;

    public ApplicantAdapter(List<Applicant> applicantsList, Context mContext) {
        this.applicantsList = applicantsList;
        this.mContext = mContext;
    }
    public String getJobId(){
        return this.jobId;
    }public String getPayment(){
        return this.payment;
    }

    @NonNull
    @Override
    public ApplicantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.applicant_item, parent, false);
        return new ApplicantViewHolder(itemView);
    }
    public void setApplicantsList(List<Applicant> newApplicantsList,String jobId,String payment) {
        this.applicantsList.clear(); // Clear the existing data
        this.applicantsList.addAll(newApplicantsList); // Add the new data
        this.jobId=jobId;
        this.payment=payment;
        notifyDataSetChanged(); // Notify the adapter that the data has changed

    }

    @Override
    public void onBindViewHolder(@NonNull ApplicantViewHolder holder, int position) {
        Applicant applicant = applicantsList.get(position);
        holder.nameTextView.setText(applicant.getName());
        holder.emailTextView.setText(applicant.getEmail());
        holder.phoneTextView.setText(applicant.getPhoneNumber());
    }

    @Override
    public int getItemCount() {
        return applicantsList.size();
    }

    public class ApplicantViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView emailTextView;
        TextView phoneTextView;
        Button Paybtn;
        public ApplicantViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.applicantName);
            emailTextView = itemView.findViewById(R.id.applicantEmail);
            phoneTextView = itemView.findViewById(R.id.applicantPhone);
            Paybtn = itemView.findViewById(R.id.Pay);

            Paybtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, paypal.class);
                    intent.putExtra("applicantsEmail",emailTextView.getText());
                    intent.putExtra("jobId",getJobId());
                    intent.putExtra("payment",getPayment());
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
