package com.wander.model;

import com.amap.api.maps2d.model.LatLng;
import com.lidroid.xutils.db.annotation.Table;

/**
 * Created by wander on 2015/5/13.
 * Email:18955260352@163.com
 */

@Table(name = "attractions",execAfterTableCreated = "CREATE UNIQUE INDEX ATTRACTIONS_INDEX ON ATTRACTIONS(id)")
public class Attractions {
    private String category;
    private String id;
    private String name;
    private String name_en;
    private double Lat;
    private double Lng;
    private String attraction_type;
    private String user_score;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

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

    public String getName_en() {
        return name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public double getLat() {
        return Lat;
    }

    public void setLat(double lat) {
        Lat = lat;
    }

    public double getLng() {
        return Lng;
    }

    public void setLng(double lng) {
        Lng = lng;
    }

    public String getAttraction_type() {
        return attraction_type;
    }

    public void setAttraction_type(String attraction_type) {
        this.attraction_type = attraction_type;
    }

    public String getUser_score() {
        return user_score;
    }

    public void setUser_score(String user_score) {
        this.user_score = user_score;
    }

    @Override
    public String toString() {
        return "Attractions{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", latLng=" + Lat+","+Lng +
                '}';
    }
}
