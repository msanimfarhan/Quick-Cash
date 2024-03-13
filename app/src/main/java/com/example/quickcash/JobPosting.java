package com.example.quickcash;

public class JobPosting {
    public String jobTitle;
    public String jobDescription;
    public String payments;
    public String location;

    public JobPosting() {
        // Default constructor required for calls to DataSnapshot.getValue(JobPosting.class)
    }

    public JobPosting(String jobTitle, String jobDescription, String payments, String location) {
        this.jobTitle = jobTitle;
        this.jobDescription = jobDescription;
        this.payments = payments;
        this.location = location;
    }

    public String getTitle() { return jobTitle; }
    public void setTitle(String title) { this.jobTitle = title; }

    public String getDescription() { return jobDescription; }
    public void setDescription(String description) { this.jobDescription = description; }

    public String getPayment() { return payments; }
    public void setPayment(String payment) { this.payments = payment; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
}
