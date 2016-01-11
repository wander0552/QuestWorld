package com.wander.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by wander on 2015/5/24.
 * time 23:21
 * Email 18955260352@163.com
 */


public class AttractionPhoto implements Parcelable {
    private String id;
    private int image_width;
    private int image_height;
    private String note_id;
    private String likes_count;
    private String image_url;
    private String description;
    private String trip_id;
    private String trip_name;

    public AttractionPhoto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getImage_width() {
        return image_width;
    }

    public AttractionPhoto(String id, String image_url, String description) {
        this.id = id;
        this.image_url = image_url;
        this.description = description;
    }

    public void setImage_width(int image_width) {
        this.image_width = image_width;
    }

    public int getImage_height() {
        return image_height;
    }

    public void setImage_height(int image_height) {
        this.image_height = image_height;
    }

    public String getNote_id() {
        return note_id;
    }

    public void setNote_id(String note_id) {
        this.note_id = note_id;
    }

    public String getLikes_count() {
        return likes_count;
    }

    public void setLikes_count(String likes_count) {
        this.likes_count = likes_count;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTrip_id() {
        return trip_id;
    }

    public void setTrip_id(String trip_id) {
        this.trip_id = trip_id;
    }

    public String getTrip_name() {
        return trip_name;
    }

    public void setTrip_name(String trip_name) {
        this.trip_name = trip_name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(image_url);
        dest.writeString(description);
    }

    public static final Parcelable.Creator<AttractionPhoto> CREATOR=new Creator<AttractionPhoto>() {
        @Override
        public AttractionPhoto createFromParcel(Parcel source) {
            //根据write的序顺依次取出
            return new AttractionPhoto(source.readString(),source.readString(),source.readString());
        }

        @Override
        public AttractionPhoto[] newArray(int size) {
            return new AttractionPhoto[size];
        }
    };
}
