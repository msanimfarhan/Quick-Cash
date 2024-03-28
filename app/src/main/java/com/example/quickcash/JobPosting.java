package com.example.quickcash;

public class JobPosting {
    public String employer; //email
    public String jobTitle;
    public String jobDescription;
    public String payment;
    public String location;
    public String jobTags;
    public String jobType; // Added field for job type

    public JobPosting() {
        // Default constructor required for Firebase
    }

    public JobPosting(String jobTitle, String jobDescription, String payments, String location, String jobType,  String employer,String jobTags) {
        this.employer = employer;
        this.jobTitle = jobTitle;
        this.jobDescription = jobDescription;
        this.payment = payments;
        this.location = location;
        this.jobType = jobType;
        this.jobTags = jobTags;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }


    // Getters and Setters
    public String getTitle() { return this.jobTitle; }
    public void setTitle(String jobTitle) { this.jobTitle = jobTitle; }

    public String getDescription() { return this.jobDescription; }
    public void setDescription(String jobDescription) { this.jobDescription = jobDescription; }

    public String getPayment() { return this.payment; }
    public void setPayment(String payment) { this.payment = payment; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getJobType() { return jobType; }
    public void setJobType(String jobType) { this.jobType = jobType; }
}
