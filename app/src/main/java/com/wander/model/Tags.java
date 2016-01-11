package com.wander.model;

import java.util.List;

/**
 * Created by wander on 2015/5/23.
 * time 22:18
 * Email 18955260352@163.com
 */

/*"id": 1642,
        "name": "浅草寺印象",
        "display_count": 99,*/
public class Tags {
    private String id;
    private String name;
    private String display_count;
    private List<Attraction_content> attraction_contents;

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

    public String getDisplay_count() {
        return display_count;
    }

    public void setDisplay_count(String display_count) {
        this.display_count = display_count;
    }

    public List<Attraction_content> getAttraction_contents() {
        return attraction_contents;
    }

    public void setAttraction_contents(List<Attraction_content> attraction_contents) {
        this.attraction_contents = attraction_contents;
    }
}
