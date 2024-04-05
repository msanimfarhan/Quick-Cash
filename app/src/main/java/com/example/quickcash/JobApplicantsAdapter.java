//package com.example.quickcash;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//
//import java.util.List;
//
//public class JobApplicantsAdapter extends RecyclerView.Adapter<JobApplicantsAdapter.ViewHolder> {
//
//    private List<JobApplicants> applicantList;
//
//    public JobApplicantsAdapter(List<JobApplicants> applicantList) {
//        this.applicantList = applicantList;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_applicant, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        JobApplicants applicant = applicantList.get(position);
//        holder.textViewName.setText(applicant.getName());
//        holder.textViewSkills.setText(applicant.getSkills());
//        holder.textViewExperience.setText(applicant.getExperience() + "years");
//    }
//
//    @Override
//    public int getItemCount() {
//        return applicantList.size();
//    }
//
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        public TextView textViewName;
//        public TextView textViewSkills;
//        public TextView textViewExperience;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            textViewName = itemView.findViewById(R.id.textViewName);
//            textViewSkills = itemView.findViewById(R.id.textViewSkills);
//            textViewExperience = itemView.findViewById(R.id.textViewExperience);
//        }
//    }
//}
//
