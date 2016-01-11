package com.wander.model;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Table;

/**
 * Created by wander on 2015/6/13.
 * time 15:59
 * Email 18955260352@163.com
 */
@Table(name = "comments")
public class Comments {
    @Column(column = "id")
    private String ins_id;
    @Column(column = "user_id")
    private String user_id;
    @Column(column = "from_id")
    private String from_id;
    @Column(column = "create_time")
    private String create_time;
    @Column(column = "content")
    private String content;
    @Column(column = "from_header")
    private String from_header;
    @Column(column = "from_username")
    private String from_username;
    @Column(column = "to_header")
    private String to_header;
    @Column(column = "to_username")
    private String to_username;

    @Override
    public String toString() {
        return "Comments{" +
                "ins_id='" + ins_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", from_id='" + from_id + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public String getIns_id() {
        return ins_id;
    }

    public void setIns_id(String ins_id) {
        this.ins_id = ins_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getFrom_id() {
        return from_id;
    }

    public void setFrom_id(String from_id) {
        this.from_id = from_id;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFrom_header() {
        return from_header;
    }

    public void setFrom_header(String from_header) {
        this.from_header = from_header;
    }

    public String getFrom_username() {
        return from_username;
    }

    public void setFrom_username(String from_username) {
        this.from_username = from_username;
    }

    public String getTo_header() {
        return to_header;
    }

    public void setTo_header(String to_header) {
        this.to_header = to_header;
    }

    public String getTo_username() {
        return to_username;
    }

    public void setTo_username(String to_username) {
        this.to_username = to_username;
    }
}
