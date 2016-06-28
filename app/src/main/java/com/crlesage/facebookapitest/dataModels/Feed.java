package com.crlesage.facebookapitest.dataModels;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Chris on 2/19/2016.
 * Feed POJO for Retrofit to pull and hold multiple "post"s
 */
public class Feed {

    /* Attributes */
    @SerializedName("data")
    public ArrayList<Post> data;

    @SerializedName("paging")
    public Paging paging;

    /* Getters and Setters */
    public ArrayList<Post> getData() {
        return data;
    }

    public void setData(ArrayList<Post> data) {
        this.data = data;
    }

    public Paging getPaging() {
        return paging;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }

    /* To String method */
    @Override
    public String toString() {
        return "Feed{" +
                "data=" + data +
                ", paging=" + paging +
                '}';
    }
}
