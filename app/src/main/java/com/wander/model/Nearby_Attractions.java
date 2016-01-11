package com.wander.model;

/**
 * Created by wander on 2015/5/23.
 * time 22:24
 * Email 18955260352@163.com
 */


/*"id": 164613,
        "name_zh_cn": "浅草 花月堂",
        "user_score": null,
        "distance": 0.133826068037021,
        "image_url": "http://m.chanyouji.cn/attractions/164613.jpg"*/
public class Nearby_Attractions {
    private String id;
    private String name_zh_cn;
    private String user_score;
    private String distance;
    private String image_url;

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

    public String getUser_score() {
        return user_score;
    }

    public void setUser_score(String user_score) {
        this.user_score = user_score;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
