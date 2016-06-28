package com.crlesage.facebookapitest.dataModels;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Chris on 2/19/2016.
 * Paging POJO for Retrofit to implement paging of feed requests.
 */
public class Paging implements Parcelable{

    /* Attributes */
    @SerializedName("previous")
    public String previous;

    @SerializedName("next")
    public String next;

    /* Getters and Setters */
    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    /* To String method */
    @Override
    public String toString() {
        return "Paging{" +
                "next='" + next + '\'' +
                ", previous='" + previous + '\'' +
                '}';
    }

    /* Parcelable methods */
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(previous);
        dest.writeString(next);
    }

    public static final Parcelable.Creator<Paging> CREATOR = new Parcelable.Creator<Paging>() {
        public Paging createFromParcel(Parcel in) {
            return new Paging(in);
        }

        public Paging[] newArray(int size) {
            return new Paging[size];
        }
    };

    public Paging (Parcel in) {
        previous = in.readString();
        next = in.readString();
    }
}
