package com.example.quickcash;

public class JobPosting {
    public String employer; //email
    public String jobTitle;
    public String jobDescription;
    public String payments;
    public String location;
    public String jobType; // Added field for job type

    public JobPosting() {
        // Default constructor required for Firebase
    }

    public JobPosting(String jobTitle, String jobDescription, String payments, String location, String jobType,  String employer) {
        this.employer = employer;
        this.jobTitle = jobTitle;
        this.jobDescription = jobDescription;
        this.payments = payments;
        this.location = location;
        this.jobType = jobType;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }


    // Getters and Setters
    public String getTitle() { return jobTitle; }
    public void setTitle(String title) { this.jobTitle = title; }

    public String getDescription() { return jobDescription; }
    public void setDescription(String description) { this.jobDescription = description; }

    public String getPayment() { return payments; }
    public void setPayment(String payment) { this.payments = payment; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getJobType() { return jobType; }
    public void setJobType(String jobType) { this.jobType = jobType; }
}
