package com.example.quickcash;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.JobViewHolder>{
    private List<JobPosting> jobList;

    public JobAdapter(List<JobPosting> jobList) {
        this.jobList = jobList;
    }

    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_item_layout, parent, false);
        return new JobViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position) {
        JobPosting job = jobList.get(position);
        holder.jobTitle.setText(job.getTitle());
        // Set other job details in the holder


        // Set other fields as needed
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Inside here, you can reference 'job', which is the item at this position.
                Intent intent = new Intent(view.getContext(), job_Posting.class);
                intent.putExtra("jobTitle", job.getTitle());
                intent.putExtra("jobDescription", job.getDescription());
                intent.putExtra("jobLocation", job.getLocation());
                intent.putExtra("jobPayment", job.getPayment());
                // Add other details as needed
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
