package com.example.orbital;

public class ExampleItem {

    private int mImageResouce;
    private String mEventname, mEventlocation, mDay, mMonth, mCount;

    public ExampleItem(int ImageResouce, String Eventname, String Eventlocation, String Day, String Month, String Count){
        mImageResouce = ImageResouce;
        mEventlocation = Eventlocation;
        mEventname = Eventname;
        mDay = Day;
        mMonth = Month;
        mCount = Count;

    }

    public int getImageResouce() {
        return mImageResouce;
    }

    public String getEventname(){
        return mEventname;
    }


    public String getEventlocation(){
        return mEventlocation;

    }

    public String getDay(){
        return mDay;

    }

    public String getMonth(){
        return mMonth;

    }

    public String getCount(){
        return mCount;

    }

}
