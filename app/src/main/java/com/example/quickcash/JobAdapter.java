package com.example.quickcash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.JobViewHolder> {
    private List<JobPosting> jobList;
    private Context context;

    public JobAdapter(List<JobPosting> jobList, Context context) {
        this.jobList = jobList;
        this.context = context;
    }

    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SharedPreferences sharedPref = context.getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        View itemView;
        if (sharedPref.getString("userRole", "").equals("Employee")) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_item_layout, parent, false);
        } else {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_item_layout_for_employer, parent, false);
        }
        return new JobViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position) {
        JobPosting job = jobList.get(position);
        holder.jobTitle.setText(job.getTitle());
        holder.jobDescription.setText(job.getDescription());
        holder.payments.setText(job.getPayment());
        holder.location.setText(job.getLocation());

        holder.itemView.setOnClickListener(view -> {
            SharedPreferences sharedPref = context.getSharedPreferences("userDetails", Context.MODE_PRIVATE);
            String role = sharedPref.getString("userRole", "");
            if (role.equals("Employee")) {
                // Navigate to Job Details Activity
                Intent intent = new Intent(view.getContext(), JobDetails.class); // Corrected class name
                intent.putExtra("jobTitle", job.getTitle());
                intent.putExtra("jobDescription", job.getDescription());
                intent.putExtra("jobPayment", job.getPayment());
                intent.putExtra("jobLocation", job.getLocation());
                intent.putExtra("jobType", job.getJobType());
                intent.putExtra("jobId", job.getJobId());
                view.getContext().startActivity(intent);
            } else if (context instanceof EmployerLanding) { // Corrected class name
                // This is a placeholder for your existing logic to show applicants
                // Assuming EmployerLandingActivity has a method showApplicants()
                ((EmployerLanding) context).showApplicants(job.getJobId(), job.getPayment());
            }
        });
    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    static class JobViewHolder extends RecyclerView.ViewHolder {
        TextView jobTitle, jobDescription, payments, location; // Add other TextViews for job details

        JobViewHolder(View view) {
            super(view);
            jobTitle = view.findViewById(R.id.title_jobDetails);
            jobDescription = view.findViewById(R.id.jobDescriptionTextView);
            payments = view.findViewById(R.id.jobPayment_jobDetails);
            location = view.findViewById(R.id.jobType_jobDetails);
        }
    }
}
