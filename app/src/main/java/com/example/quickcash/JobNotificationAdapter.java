package com.example.quickcash;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class JobNotificationAdapter extends RecyclerView.Adapter<JobNotificationAdapter.ViewHolder> {

    private List<JobNotificationData> jobNotifications;

    public JobNotificationAdapter(List<JobNotificationData> jobNotifications) {
        this.jobNotifications = jobNotifications;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_notification_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JobNotificationData jobNotification = jobNotifications.get(position);
        holder.jobTitle.setText("Job Title: "+jobNotification.getJobTitle());
        holder.jobID.setText("Job ID: "+jobNotification.getJobID());
        holder.jobPayment.setText("Hourly Payment: "+jobNotification.getPayment()+" $");
        holder.jobLocation.setText("Location: "+jobNotification.getLocation());
        holder.jobType.setText("Job Type: "+jobNotification.getJobType());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ApplyJobActivity.class);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return jobNotifications.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView jobTitle, jobID, jobPayment, jobLocation, jobType;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            jobTitle = itemView.findViewById(R.id.jobTitle);
            jobID = itemView.findViewById(R.id.JobID);
            jobPayment = itemView.findViewById(R.id.jobPayment);
            jobLocation = itemView.findViewById(R.id.jobLocation);
            jobType = itemView.findViewById(R.id.jobType);
        }
    }
}
