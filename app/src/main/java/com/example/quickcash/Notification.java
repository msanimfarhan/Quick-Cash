package com.example.quickcash;

public class Notification {
    private String title;
    private String location;
    private String payrate;
    private String type;

    public Notification(String t,String l, String p,String ty){
        this.title=t;
        this.location=l;
        this.payrate=p;
        this.type=ty;
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public String getPayrate() {
        return payrate;
    }

    public String getType() {
        return type;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPayrate(String payrate) {
        this.payrate = payrate;
    }

    public void setType(String type) {
        this.type = type;
    }
}
