package com.crlesage.facebookapitest.dataModels;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Chris on 2/19/2016.
 * From POJO for Retrofit to hold who the data about whom the post is from.
 */
public class From implements Parcelable {

    /* Attributes */
    @SerializedName("name")
    public String name;

    @SerializedName("category")
    public String category;

    @SerializedName("id")
    public String id;

    /* Getters and Setters */
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String  id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /* To String method */
    @Override
    public String toString() {
        return "From{" +
                "category='" + category + '\'' +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }

    /* Parcelable methods */
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(category);
        dest.writeString(id);
    }

    public static final Parcelable.Creator<From> CREATOR = new Parcelable.Creator<From>() {
        public From createFromParcel(Parcel in) {
            return new From(in);
        }

        public From[] newArray(int size) {
            return new From[size];
        }
    };

    public From (Parcel in) {
        name = in.readString();
        category = in.readString();
        id = in.readString();
    }
}