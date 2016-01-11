package com.wander.model;

/**
 * Created by wander on 2015/5/23.
 * time 16:34
 * Email 18955260352@163.com
 */


public class Place {
    private String id;
    private String name;
    private String attraction_trips_count;
    private String user_score;
    private String description;
    private String name_en;
    private String attraction_type;
    private String description_summary;
    private String image_url;

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

    public String getAttraction_trips_count() {
        return attraction_trips_count;
    }

    public void setAttraction_trips_count(String attraction_trips_count) {
        this.attraction_trips_count = attraction_trips_count;
    }

    public String getUser_score() {
        return user_score;
    }

    public void setUser_score(String user_score) {
        this.user_score = user_score;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName_en() {
        return name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public String getAttraction_type() {
        return attraction_type;
    }

    public void setAttraction_type(String attraction_type) {
        this.attraction_type = attraction_type;
    }

    public String getDescription_summary() {
        return description_summary;
    }

    public void setDescription_summary(String description_summary) {
        this.description_summary = description_summary;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
