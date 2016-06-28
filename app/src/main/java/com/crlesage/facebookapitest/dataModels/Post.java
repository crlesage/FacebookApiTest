package com.crlesage.facebookapitest.dataModels;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Chris on 2/19/2016.
 * Post POJO for Retrofit to use in conjunction with Gson.
 * As well as easily to use for keeping track and displaying posts.
 */
public class Post implements Parcelable {

    /* Attributes */
    @SerializedName("id")
    public String id;

    @SerializedName("created_time")
    public String created_time;

    @SerializedName("from")
    public From from;

    @SerializedName("height")
    public int height;

    @SerializedName("icon")
    public String icon;

//    @SerializedName("images")
//    public Images images;

    @SerializedName("link")
    public String link;

    @SerializedName("name")
    public String name;

    @SerializedName("picture")
    public String picture;

    @SerializedName("source")
    public String source;

    @SerializedName("updated_time")
    public String updated_time;

    @SerializedName("width")
    public int width;

    @SerializedName("likes")
    public Likes likes;

    public transient int position;

    /* Constructor */
    public Post (){
    }

    /* Getters and Setters */
    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public From getFrom() {
        return from;
    }

    public void setFrom(From from) {
        this.from = from;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

//    public Images getImages() {
//        return images;
//    }
//
//    public void setImages(Images images) {
//        this.images = images;
//    }

    public Likes getLikes() {
        return likes;
    }

    public void setLikes(Likes likes) {
        this.likes = likes;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUpdated_time() {
        return updated_time;
    }

    public void setUpdated_time(String updated_time) {
        this.updated_time = updated_time;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    /* Parcelable methods */
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(created_time);
        dest.writeParcelable(from, flags);
        dest.writeInt(height);
        dest.writeString(icon);
        //dest.writeString(images);
        dest.writeString(link);
        dest.writeString(name);
        dest.writeString(picture);
        dest.writeString(source);
        dest.writeString(updated_time);
        dest.writeInt(width);
        dest.writeParcelable(likes, flags);
    }

    public static final Parcelable.Creator<Post> CREATOR = new Parcelable.Creator<Post>() {
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    public Post (Parcel in) {
        id = in.readString();
        created_time = in.readString();
        from = in.readParcelable(From.class.getClassLoader());
        height = in.readInt();
        icon = in.readString();
        //images = in.readString();
        link = in.readString();
        name = in.readString();
        picture = in.readString();
        source = in.readString();
        updated_time = in.readString();
        width = in.readInt();
        likes = in.readParcelable(Likes.class.getClassLoader());
    }

    /* To String method */

    @Override
    public String toString() {
        return "Post{" +
                "created_time='" + created_time + '\'' +
                ", id='" + id + '\'' +
                ", from=" + from +
                ", height=" + height +
                ", icon='" + icon + '\'' +
                ", link='" + link + '\'' +
                ", name='" + name + '\'' +
                ", picture='" + picture + '\'' +
                ", source='" + source + '\'' +
                ", updated_time='" + updated_time + '\'' +
                ", width=" + width +
                ", likes=" + likes +
                ", position=" + position +
                '}';
    }
}
