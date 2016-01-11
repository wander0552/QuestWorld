package com.wander.model;

/**
 * Created by wander on 2015/5/23.
 * time 23:28
 * Email 18955260352@163.com
 */


/*"id": 3230422,
        "description": "沙竭罗龙王像，需五个步骤取水饮用，可以净身净心，带来好运。",
        "width": 1067,
        "height": 1600,
        "photo_url": "http://p.chanyouji.cn/75012/1381907254132p1870041ulemm7151vbbge7jffh.jpg",
        "video_url": null*/
public class Note {
    private String id;
    private String description;
    private String width;
    private String height;
    private String photo_url;
    private String video_url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }
}
