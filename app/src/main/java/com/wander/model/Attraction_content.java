package com.wander.model;

import java.util.List;

/**
 * Created by wander on 2015/5/23.
 * time 23:01
 * Email 18955260352@163.com
 */

/*"id": 6730,
        "description": "#浅草寺标志—雷门# 表参道入口之门，切妻构造八脚门，左面的是风神像，右面的是雷神像，正式名称为“风雷神门”，通称为“雷门”。",
        "updated_at": 1402544273,
        "node_id": 604410,
        "node_comments_count": 0,*/
public class Attraction_content {
    private String id;
    private String description;
    private String updated_at;
    private String node_id;
    private String node_comments_count;
    private List<Note> notes;

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

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getNode_id() {
        return node_id;
    }

    public void setNode_id(String node_id) {
        this.node_id = node_id;
    }

    public String getNode_comments_count() {
        return node_comments_count;
    }

    public void setNode_comments_count(String node_comments_count) {
        this.node_comments_count = node_comments_count;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

}
