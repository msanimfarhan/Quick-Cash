package com.example.quickcash;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import java.util.List;

public class FirebaseCrud {
    private DatabaseReference userRef;
    private FirebaseDatabase database;
    String  result="";



    public FirebaseCrud(FirebaseDatabase database) {
        this.database = database;
        this.initializeDatabaseRefs();
        this.initializeDatabaseRefListeners();
    }

    protected void initializeDatabaseRefs() {
        //Incomplete method, add your implementation
        this.nameRef = getNameRef();
        this.emailRef = getEmailRef();
        this.roleRef = getRoleRef();
        this.passRef = getPassRef();
        this.locationRef = getLocationRef();

    }
    protected DatabaseReference getUserRef(String userMail) {
        return this.database.getReference(userMail);
    }


    public void fetchApplicantsForJob(String jobId, final ApplicantsResultCallback callback) {
        DatabaseReference jobApplicantsRef = database.getReference("AllJobs").child(jobId).child("applicants");

        jobApplicantsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Applicant> applicants = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Applicant applicant = snapshot.getValue(Applicant.class);
                    applicants.add(applicant);
                }
                callback.onApplicantsRetrieved(applicants);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onError(databaseError.toException());
            }
        });
    }
    public interface ApplicantsResultCallback {
        void onApplicantsRetrieved(List<Applicant> applicants);
        void onError(Exception e);
    }

    public void addJobPosting(JobPosting jobPosting, String userMail, final JobPostingResultCallback callback) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String sanitizedEmail = userMail.replace(".", ",");

//        DatabaseReference usersRef = database.getReference(sanitizedEmail);
        DatabaseReference Alljobsref = database.getReference("AllJobs");
        DatabaseReference userRef = database.getReference("Users").child(sanitizedEmail).child("jobs");

        DatabaseReference jobIdRef=Alljobsref.push();
        String key= jobIdRef.getKey();
        jobPosting.setJobId(key);

        jobIdRef.setValue(jobPosting).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    callback.onSuccess("Job posted successfully!");
                } else {
//                    Toast.makeText(this, "Job posted successfully!", Toast.LENGTH_LONG).show();
                    callback.onFailure(task.getException().getMessage());
                }
            }
        });
    }

    public void fetchAllJobs(final JobPostingsResultCallback callback) {
        DatabaseReference allJobsRef = database.getReference("AllJobs");

        allJobsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<JobPosting> jobPostings = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    JobPosting job = snapshot.getValue(JobPosting.class);
                    jobPostings.add(job);
                }
                callback.onJobPostingsRetrieved(jobPostings);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onError(databaseError.toException());
            }
        });
    }
//



    public void fetchUserJobs(String userEmail, final JobPostingsResultCallback callback) {
        String sanitizedEmail = userEmail.replace(".", ",");
        Query userJobsQuery = database.getReference("AllJobs").orderByChild("employer")
                .equalTo(sanitizedEmail);

        userJobsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<JobPosting> jobPostings = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    JobPosting job = snapshot.getValue(JobPosting.class);
                    jobPostings.add(job);
                }
                callback.onJobPostingsRetrieved(jobPostings);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onError(databaseError.toException());
            }
        });
    }

    public void fetchAllJobNotifications(final JobNotificationsResultCallback callback) {
        DatabaseReference allJobsRef = database.getReference("AllJobs");

        allJobsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<JobNotificationData> jobNotifications = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String jobID = snapshot.getKey();
                    JobNotificationData jobNotification = snapshot.getValue(JobNotificationData.class);

                    if (jobNotification != null) {
                        jobNotification.setJobID(jobID);
                        jobNotifications.add(jobNotification);
                    }
                }

                callback.onJobNotificationsRetrieved(jobNotifications);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onError(databaseError.toException());
            }
        });
    }


    // Callback interface for job posting results
    public interface JobPostingResultCallback {
        void onSuccess(String result);
        void onFailure(String errorMessage);
    }

    // Callback interface for retrieving multiple job postings
    public interface JobPostingsResultCallback {
        void onJobPostingsRetrieved(List<JobPosting> jobPostings);
        void onError(Exception e);
    }

    public interface JobNotificationsResultCallback {
        void onJobNotificationsRetrieved(List<JobNotificationData> jobNotifications);
        void onError(Exception e);
    }

    public interface NotificationResultCallback {
        void onNotificationRetrieved(List<JobPosting> jobPostings);
        void onError(Exception e);
    }
    protected void initializeDatabaseRefListeners() {
        //Incomplete method, add your implementation
        this.setNameListener();
        this.setEmailListener();
        this.setRoleListener();
        this.setPassListener();
        this.setLocationListener();
    }

    private DatabaseReference nameRef = null;

    private DatabaseReference emailRef = null;
    private DatabaseReference roleRef = null;

    private DatabaseReference passRef = null;

    private DatabaseReference locationRef = null;
    private String extractedLocation;

    private String extractedName;
    private String extractedEmailAddress;
    private String extractedRole;
    private String extractedPass;

    protected DatabaseReference getLocationRef() {
        return this.database.getReference("location");
    }

    protected void setLocation(String location) {
        if (locationRef != null) {
            locationRef.setValue(location);
        }
    }

    protected void setLocationListener() {
        this.locationRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    extractedLocation = snapshot.getValue(String.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    protected DatabaseReference getNameRef() {
        return this.database.getReference("Name");
    }

    protected void setName(String name) {
        //incomplete method, add your implementation
        if (nameRef != null) {
            nameRef.setValue(name);
        }

    }


    protected DatabaseReference getEmailRef() {
        return this.database.getReference("email");
    }

    protected void setEmail(String email) {

        if (emailRef != null) {
            emailRef.setValue(email);
        }

    }


    protected DatabaseReference getRoleRef() {
        return this.database.getReference("role");
    }

    protected void setRole(String role) {
        //incomplete method, add your implementation
        if (roleRef != null) {
            roleRef.setValue(role);
        }

    }


    protected DatabaseReference getPassRef() {
        return this.database.getReference("password");
    }

    protected void setPass(String pass) {
        //incomplete method, add your implementation
        if (passRef != null) {
            passRef.setValue(pass);
        }

    }




    protected void setNameListener() {
        this.nameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //incomplete method, add your implementation
                if (snapshot.exists()) {
                    extractedName = snapshot.getValue(String.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    protected void setEmailListener() {
        this.emailRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    extractedEmailAddress = snapshot.getValue(String.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle potential errors
            }
        });
    }


    protected void setRoleListener() {
        this.roleRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    extractedRole = snapshot.getValue(String.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    protected void setPassListener() {
        this.passRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    extractedPass = snapshot.getValue(String.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void applyForJob(String jobId, String applicantEmail, String applicantName, String applicantPhoneNumber, final JobApplicationResultCallback callback) {
        DatabaseReference jobApplicationRef = database.getReference("AllJobs").child(jobId).child("applicants");

        HashMap<String, Object> application = new HashMap<>();
        application.put("email", applicantEmail);
        application.put("name", applicantName);
        application.put("phoneNumber", applicantPhoneNumber);

        jobApplicationRef.push().setValue(application)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onApplicationSuccess("Application submitted successfully!");
                    } else {
                        if (task.getException() != null) {
                            callback.onApplicationFailure(task.getException().getMessage());
                        } else {
                            callback.onApplicationFailure("Unknown error occurred.");
                        }
                    }
                });
    }

    public interface JobApplicationResultCallback {
        void onApplicationSuccess(String result);
        void onApplicationFailure(String errorMessage);
    }




}

