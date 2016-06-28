package com.crlesage.facebookapitest.dataModels;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Chris on 2/19/2016.
 * Likes POJO for Retrofit to hold action data about a post's likes.
 */
public class Likes implements Parcelable {
    /* Attributes */
    @SerializedName("data")
    public ArrayList<LikeData> data;

    @SerializedName("paging")
    public LikePaging paging;

    /* Getters and Setters */
    public ArrayList<LikeData> getData() {
        return data;
    }

    public void setData(ArrayList<LikeData> data) {
        this.data = data;
    }

    public LikePaging getPaging() {
        return paging;
    }

    public void setPaging(LikePaging paging) {
        this.paging = paging;
    }

    /* To String method */
    @Override
    public String toString() {
        return "Likes{" +
                "data=" + data +
                ", paging=" + paging +
                '}';
    }

    /* Parcelable methods */
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(data);
        dest.writeParcelable(paging, flags);
    }

    public static final Parcelable.Creator<Likes> CREATOR = new Parcelable.Creator<Likes>() {
        public Likes createFromParcel(Parcel in) {
            return new Likes(in);
        }

        public Likes[] newArray(int size) {
            return new Likes[size];
        }
    };

    public Likes (Parcel in) {
        data = new ArrayList<LikeData>();
        in.readList(data, LikeData.class.getClassLoader());
        paging = in.readParcelable(LikePaging.class.getClassLoader());
    }

    /* LikeData inner class */
    public static class LikeData implements Parcelable{
        /* Attributes */
        @SerializedName("id")
        public String id;

        @SerializedName("name")
        public String name;

        /* Getters and Setters */
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        /* To string method */
        @Override
        public String toString() {
            return "LikeData{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }

        /* Parcelable methods */
        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(name);
        }

        public static final Parcelable.Creator<LikeData> CREATOR = new Parcelable.Creator<LikeData>() {
            public LikeData createFromParcel(Parcel in) {
                return new LikeData(in);
            }

            public LikeData[] newArray(int size) {
                return new LikeData[size];
            }
        };

        public LikeData (Parcel in) {
            id = in.readString();
            name = in.readString();
        }
    }

    /* LikePaging inner class */
    public static class LikePaging implements Parcelable {
        /* Attributes */
        @SerializedName("cursors")
        public LikeCursors cursors;

        /* Getters and Setters */
        public LikeCursors getCursors() {
            return cursors;
        }

        public void setCursors(LikeCursors cursors) {
            this.cursors = cursors;
        }

        /* To String method */
        @Override
        public String toString() {
            return "LikePaging{" +
                    "cursors=" + cursors +
                    '}';
        }

        /* Parcelable methods */
        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(cursors, flags);
        }

        public static final Parcelable.Creator<LikePaging> CREATOR = new Parcelable.Creator<LikePaging>() {
            public LikePaging createFromParcel(Parcel in) {
                return new LikePaging(in);
            }

            public LikePaging[] newArray(int size) {
                return new LikePaging[size];
            }
        };

        public LikePaging (Parcel in) {
            cursors = in.readParcelable(LikeCursors.class.getClassLoader());
        }

        /* LikeCursors inner class */
        public static class LikeCursors implements Parcelable {
            /* Attributes */
            @SerializedName("before")
            public String before;

            @SerializedName("after")
            public String after;

            /* Getters and Setters */
            public String getAfter() {
                return after;
            }

            public void setAfter(String after) {
                this.after = after;
            }

            public String getBefore() {
                return before;
            }

            public void setBefore(String before) {
                this.before = before;
            }

            /* To String method */
            @Override
            public String toString() {
                return "LikeCursors{" +
                        "after='" + after + '\'' +
                        ", before='" + before + '\'' +
                        '}';
            }

            /* Parcelable methods */
            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(before);
                dest.writeString(after);
            }

            public static final Parcelable.Creator<LikeCursors> CREATOR = new Parcelable.Creator<LikeCursors>() {
                public LikeCursors createFromParcel(Parcel in) {
                    return new LikeCursors(in);
                }

                public LikeCursors[] newArray(int size) {
                    return new LikeCursors[size];
                }
            };

            public LikeCursors (Parcel in) {
                before = in.readString();
                after = in.readString();
            }
        }
    }
}
