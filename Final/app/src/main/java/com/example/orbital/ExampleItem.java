package com.example.orbital;

import android.net.Uri;

public class ExampleItem {

    private Uri profile;
    private String eventName, eventLocation, Day, Month, Count;

    //Empty Constructor for Firebase
    private ExampleItem(){}

    public ExampleItem(Uri profile, String eventName, String eventLocation, String Day, String Month, String Count){
        this.profile = profile;
        this.eventLocation = eventLocation;
        this.eventName = eventName;
        this.Day = Day;
        this.Month = Month;
        this.Count = Count;
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

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
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

    public String getCount() {
        return Count;
    }

    public void setCount(String count) {
        Count = count;
    }
}
