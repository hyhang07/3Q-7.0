package com.example.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

public class Voucher implements Parcelable {
    private String name;
    private int quantity;

    public Voucher(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    protected Voucher(Parcel in) {
        name = in.readString();
        quantity = in.readInt();
    }

    public static final Creator<Voucher> CREATOR = new Creator<Voucher>() {
        @Override
        public Voucher createFromParcel(Parcel in) {
            return new Voucher(in);
        }

        @Override
        public Voucher[] newArray(int size) {
            return new Voucher[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(quantity);
    }
}
