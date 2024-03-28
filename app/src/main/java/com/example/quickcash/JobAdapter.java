package com.example.quickcash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.JobViewHolder> {
    private List<JobPosting> jobList;
    private Context context;

    public JobAdapter(List<JobPosting> jobList, Context context) {
        this.jobList = jobList;
        this.context=context;
    }

    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SharedPreferences sharedPref = context.getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        if (sharedPref.getString("userRole", "").equals("Employee")) {
            View itemViewForEmployee = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_item_layout, parent, false);
            return new JobViewHolder(itemViewForEmployee);

        } else {

            View itemViewForEmployer = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_item_layout_for_employer, parent, false);
            return new JobViewHolder(itemViewForEmployer);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position) {
        JobPosting job = jobList.get(position);
        holder.jobTitle.setText(job.getTitle());
        holder.jobDescription.setText(job.getDescription());
        holder.payments.setText(job.getPayment());
        holder.location.setText(job.getLocation());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Make sure the context used here is from the view which is attached to the window
                Intent intent = new Intent(view.getContext(), jobDetails.class);
                // Assuming 'ApplyJobActivity' is expecting extras, send them
                intent.putExtra("jobTitle", job.getTitle());
                intent.putExtra("jobDescription", job.getDescription());
                intent.putExtra("jobPayment", job.getPayment());
                intent.putExtra("jobLocation", job.getLocation());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    static class JobViewHolder extends RecyclerView.ViewHolder {
        TextView jobTitle, jobDescription, payments, location; // add other TextViews for job details

        JobViewHolder(View view) {
            super(view);
            jobTitle = view.findViewById(R.id.jobTitleTextView); // Make sure this ID matches your layout
            jobDescription = view.findViewById(R.id.jobDescriptionTextView);
            payments = view.findViewById(R.id.jobPaymentTextView);
            location = view.findViewById(R.id.jobLocationTextView);

        }
    }

}
