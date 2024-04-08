package com.example.quickcash;

public class EmployeeInfo {
    private String name;
    private String email;
    private String role;
    private int accountBalance;
    private int totalJobsCompleted;

    public EmployeeInfo(String name, String email, String role, int accountBalance, int totalJobsCompleted) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.accountBalance = accountBalance;
        this.totalJobsCompleted = totalJobsCompleted;
    }

    public EmployeeInfo() {
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public int getAccountBalance() {
        return accountBalance;
    }

    public int getTotalJobsCompleted() {
        return totalJobsCompleted;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setAccountBalance(int accountBalance) {
        this.accountBalance = accountBalance;

    }

    public void setTotalJobsCompleted(int totalJobsCompleted) {
        this.totalJobsCompleted = totalJobsCompleted;
    }
}
