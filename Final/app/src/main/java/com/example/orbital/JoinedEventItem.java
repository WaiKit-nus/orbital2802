package com.example.orbital;

import android.net.Uri;

public class JoinedEventItem {
    private Uri profile;
    private String uid;
    private String eventName, Day, Month;

    //Empty Constructor for Firebase
    private JoinedEventItem(){}

    public JoinedEventItem(Uri profile, String eventName, String Day, String Month, String uid){
        this.profile = profile;
        this.eventName = eventName;
        this.Day = Day;
        this.Month = Month;
        this.uid = uid;
    }

    public Uri getProfile() {
        return profile;
    }

    public void setProfile(Uri profile) {
        this.profile = profile;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getDay() {
        return Day;
    }

    public void setDay(String day) {
        Day = day;
    }

    public String getMonth() {
        return Month;
    }

    public void setMonth(String month) {
        Month = month;
    }

    public String getUid() {return uid;}

    public void setUid(String uid1) {uid = uid1;}
}
