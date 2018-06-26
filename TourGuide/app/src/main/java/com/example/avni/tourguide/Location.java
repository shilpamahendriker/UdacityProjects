package com.example.avni.tourguide;

public class Location {
    private String mName;
    private String mAddress;
    private String mDescription;
    private String mHours;
    private int mShortDescResId;
    private  int mFullDescResId;
    private int mImageResourceId;
    private String mPhoneNo;

    /*public Location(String name, String address, String description){
        mName = name;
        mAddress = address;
        mDescription = description;
    }
    public Location(String name, String address, String description, int imageResourceId){
        mName = name;
        mAddress = address;
        mDescription = description;
        mImageResourceId = imageResourceId;
    }*/
    public Location(String name, String address,String hours,String phoneno, int shortDesc,int fullDesc, int imageResourceId){
        mName = name;
        mAddress = address;
        mHours = hours;
        mShortDescResId = shortDesc;
        mFullDescResId = fullDesc;
        mImageResourceId = imageResourceId;
        mPhoneNo = phoneno;
    }


    public Location(String name, String address,String hours,String phoneno, int shortDesc,int fullDesc){
        mName = name;
        mAddress = address;
        mHours = hours;
        mShortDescResId = shortDesc;
        mFullDescResId = fullDesc;
        mPhoneNo = phoneno;

    }
    public String getLocationName(){ return mName;}
    public String getLocationAddress(){ return mAddress;}
    public String getLocationDescription(){ return mDescription;}
    public String getHours(){return mHours;}
    public int getShortDescResId(){return mShortDescResId;}
    public int getmFullDescResId(){return mFullDescResId;}
    public int getLocationImageId(){ return mImageResourceId;}
    public String getPhoneNo(){ return mPhoneNo;}
}
