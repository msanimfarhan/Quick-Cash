package com.example.quickcash;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ApplicantAdapter extends RecyclerView.Adapter<ApplicantAdapter.ApplicantViewHolder> {
    private List<Applicant> applicantsList;
    private final Context mContext;

    public ApplicantAdapter(List<Applicant> applicantsList, Context mContext) {
        this.applicantsList = applicantsList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ApplicantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.applicant_item, parent, false);
        return new ApplicantViewHolder(itemView);
    }
    public void setApplicantsList(List<Applicant> newApplicantsList) {
        this.applicantsList.clear(); // Clear the existing data
        this.applicantsList.addAll(newApplicantsList); // Add the new data
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

    public static class ApplicantViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView emailTextView;
        TextView phoneTextView;

        public ApplicantViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.applicantName);
            emailTextView = itemView.findViewById(R.id.applicantEmail);
            phoneTextView = itemView.findViewById(R.id.applicantPhone);
        }
    }
}
