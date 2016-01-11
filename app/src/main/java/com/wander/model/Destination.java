package com.wander.model;

import com.amap.api.maps2d.model.LatLng;

/**
 * Created by wander on 2015/5/13.
 * Email:18955260352@163.com
 */

public class Destination {
    private String id;
    private String name_zh_cn;
    private String name_en;
    private String poi_count;
    private String image_url;
    private String updated_at;
    private LatLng latLng;

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName_zh_cn() {
        return name_zh_cn;
    }

    public void setName_zh_cn(String name_zh_cn) {
        this.name_zh_cn = name_zh_cn;
    }

    public String getName_en() {
        return name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public String getPoi_count() {
        return poi_count;
    }

    public void setPoi_count(String poi_count) {
        this.poi_count = poi_count;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "Destination{" +
                "id='" + id + '\'' +
                ", name_zh_cn='" + name_zh_cn + '\'' +
                ", image_url='" + image_url + '\'' +
                '}';
    }
}
