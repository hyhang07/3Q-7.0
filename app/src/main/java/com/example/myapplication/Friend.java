package com.example.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

public class Friend implements Parcelable {
    private String id;
    private String name;
    private int sleepHours; // Example metric
    private int bmi; // Example metric
    private int calorieConsumption; // Example metric

    // Constructor, getters, setters, and Parcelable implementation
    public Friend(String id, String name, int sleepHours, int bmi, int calorieConsumption) {
        this.id = id;
        this.name = name;
        this.sleepHours = sleepHours;
        this.bmi = bmi;
        this.calorieConsumption = calorieConsumption;
    }

    protected Friend(Parcel in) {
        id = in.readString();
        name = in.readString();
        sleepHours = in.readInt();
        bmi = in.readInt();
        calorieConsumption = in.readInt();
    }

    public static final Creator<Friend> CREATOR = new Creator<Friend>() {
        @Override
        public Friend createFromParcel(Parcel in) {
            return new Friend(in);
        }

        @Override
        public Friend[] newArray(int size) {
            return new Friend[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeInt(sleepHours);
        dest.writeInt(bmi);
        dest.writeInt(calorieConsumption);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSleepHours() {
        return sleepHours;
    }

    public void setSleepHours(int sleepHours) {
        this.sleepHours = sleepHours;
    }

    public int getBmi() {
        return bmi;
    }

    public void setBmi(int bmi) {
        this.bmi = bmi;
    }

    public int getCalorieConsumption() {
        return calorieConsumption;
    }

    public void setCalorieConsumption(int calorieConsumption) {
        this.calorieConsumption = calorieConsumption;
    }
}
