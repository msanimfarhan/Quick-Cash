package com.example.quickcash;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

public class Paypal extends AppCompatActivity {
    private static final String TAG = Paypal.class.getName();

    private PayPalConfiguration payPalconfig;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private AppCompatEditText payamount;
    private AppCompatButton paypalpaybtn;
    private AppCompatTextView paymentStatus;
    private Button jobBoard;
    private Button notification;

    private String applicantsEmail;
    private String payment;
    private String jobId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paypal);
        init();
        configpaypal();
        initActivityLauncher();
        setListeners();
        setupjobBoardListener();
        setupNotificationListener();

        //Retrieving Data From intent
        String applicantsEmail = getIntent().getStringExtra("applicantsEmail");
        String payment = getIntent().getStringExtra("payment");
        String jobid = getIntent().getStringExtra("jobId");
        this.applicantsEmail = applicantsEmail;
        this.payment = payment;
        this.jobId = jobid;

        //Setting the payment
        EditText amountbox = findViewById(R.id.amount);
        amountbox.setText(payment);
        amountbox.setEnabled(false);
        amountbox.setFocusable(false);

        Button jobBoardBtn = findViewById(R.id.job_board_btn);
        Button notificationButton = findViewById(R.id.notification_btn);
        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the new activity
                Intent intent = new Intent(Paypal.this, JobNotification.class);
                startActivity(intent);
            }
        });
        jobBoardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the new activity
                SharedPreferences sharedPref = getSharedPreferences("userDetails", Context.MODE_PRIVATE);
                String userRole = sharedPref.getString("userRole", "");
                Intent intent;
                if (userRole.equals("Employee")) {
                    intent = new Intent(Paypal.this, EmployeeLanding.class);
                } else{
                    intent = new Intent(Paypal.this, EmployerLanding.class);
                }
                startActivity(intent);
            }
        });

    }

    public String getApplicantsEmail() {
        return applicantsEmail;
    }

    public String getPayment() {
        return payment;
    }

    public String getJobId() {
        return jobId;
    }

    private void init() {
        payamount = findViewById(R.id.amount);
        paypalpaybtn = findViewById(R.id.paypalpaybtn);
        paymentStatus = findViewById(R.id.PaymentStatus);
        jobBoard = findViewById(R.id.job_board_btn);
        notification = findViewById(R.id.notification_btn);
    }

    private void setupjobBoardListener() {
        jobBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                move2JobPosting();
            }
        });
    }

    private void setupNotificationListener() {
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                move2Notification();
            }
        });
    }

    private void configpaypal() {
        payPalconfig = new PayPalConfiguration()
                .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                .clientId("AQDwb7b7QZq7g9Hl0-pYDuQXrJfK35voTgnV9AMoEaQNgy5H2XQLY-ibl5U6t-jEKqcsUFuutsszf46x");

    }

    protected void move2JobPosting() {
        Intent paypalIntent = new Intent(this, JobPostingActivity.class);
        startActivity(paypalIntent);
    }

    protected void move2Notification() {
        Intent paypalIntent = new Intent(this, Notification.class);
        startActivity(paypalIntent);
    }

    private void initActivityLauncher() {
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        final PaymentConfirmation confirmation = result.getData().getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                        if (confirmation != null) {
                            try {
                                // Get the payment details
                                String paymentDetails = confirmation.toJSONObject().toString(4);
                                Log.i(TAG, paymentDetails);
                                // Extract json response and display it in a text view.
                                JSONObject payObj = new JSONObject(paymentDetails);
                                Log.i(TAG, "Complete Payment Object: " + payObj);

                                String payID = payObj.getJSONObject("response").getString("id");
                                String state = payObj.getJSONObject("response").getString("state");
                                paymentStatus.setText(String.format("Payment %s%n with payment id is %s", state, payID));
                                if(state.equals("approved")){
                                    updatePaymentInfoOnDB(getApplicantsEmail(),getPayment(),getJobId());
                                }
                            } catch (JSONException e) {
                                Log.e("Error", "an extremely unlikely failure occurred: ", e);
                            }
                        }
                    } else if (result.getResultCode() == PaymentActivity.RESULT_EXTRAS_INVALID) {
                        Log.d(TAG, "Launcher Result Invalid");
                    } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                        Log.d(TAG, "Launcher Result Cancelled");
                    }
                });
    }

    private void setListeners() {
        paypalpaybtn.setOnClickListener(v -> processPayment());
    }

    private void processPayment() {
        final String amount = payamount.getText().toString();
        final PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(
                amount), "CAD", "Purchase Goods", PayPalPayment.PAYMENT_INTENT_SALE);

        // Create Paypal Payment activity intent
        final Intent intent = new Intent(this, PaymentActivity.class);
        // Adding paypal configuration to the intent
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, payPalconfig);
        // Adding paypal payment to the intent
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
        // Starting Activity Request launcher
        activityResultLauncher.launch(intent);
    }

    public void updatePaymentInfoOnDB(String employeeEmail, String payment, String jobId) {
        // Replace '.' with ',' in the email for Firebase compatibility
        String sanitizedEmail = employeeEmail.replace(".", ",");

        // Update account balance and total jobs completed for the user
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(sanitizedEmail);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Update accountBalance
                    DatabaseReference accountBalanceRef = userRef.child("accountBalance");
                    accountBalanceRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                long currentBalance = dataSnapshot.getValue(Long.class);
                                accountBalanceRef.setValue(currentBalance + Integer.parseInt(payment));
                                Toast.makeText(Paypal.this, "Account Balance updated", Toast.LENGTH_SHORT).show();
                            } else {
                                accountBalanceRef.setValue(Integer.parseInt(payment));
                                Toast.makeText(Paypal.this, "Account Balance created and updated", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // Handle errors
                        }
                    });

                    // Update totalJobsCompleted
                    DatabaseReference totalJobsCompletedRef = userRef.child("totalJobsCompleted");
                    totalJobsCompletedRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                long currentTotalJobsCompleted = dataSnapshot.getValue(Long.class);
                                totalJobsCompletedRef.setValue(currentTotalJobsCompleted + 1);
                            } else {
                                totalJobsCompletedRef.setValue(1);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // Handle errors
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors
            }
        });

        // Update isCompleted field for the job
        DatabaseReference allJobsRef = FirebaseDatabase.getInstance().getReference("AllJobs").child(jobId);
        allJobsRef.child("isCompleted").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    allJobsRef.child("isCompleted").setValue(true);
                } else {
                    allJobsRef.child("isCompleted").setValue(true); // Create and set isCompleted to true
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors
            }
        });
    }


}
