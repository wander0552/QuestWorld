package com.wander.model;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Table;

import java.util.Comparator;

/**
 * Created by wander on 2015/6/15.
 * time 16:07
 * Email 18955260352@163.com
 */
@Table(name = "info",execAfterTableCreated = "CREATE UNIQUE INDEX INFO_INDEX ON info(create_time)")
public class Info extends EntityBase{
    @Column(column = "ins_id")
    private String ins_id;
    @Column(column = "user_id")
    private String user_id;
    @Column(column = "create_time")
    private String create_time;
    @Column(column = "user_header")
    private String user_header;
    @Column(column = "message")
    private String message;
    @Column(column = "type")
    private String type;
    @Column(column = "pics")
    private String pics;
    @Column(column = "content")
    private String content;
    @Column(column = "username")
    private String username;
    @Column(column = "is_reader")
    private String is_read;

    @Override
    public String toString() {
        return "Info{" +
                "ins_id='" + ins_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", create_time='" + create_time + '\'' +
                ", user_header='" + user_header + '\'' +
                ", message='" + message + '\'' +
                ", type='" + type + '\'' +
                ", pics='" + pics + '\'' +
                ", content='" + content + '\'' +
                ", username='" + username + '\'' +
                ", is_read='" + is_read + '\'' +
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

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUser_header() {
        return user_header;
    }

    public void setUser_header(String user_header) {
        this.user_header = user_header;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPics() {
        return pics;
    }

    public void setPics(String pics) {
        this.pics = pics;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIs_read() {
        return is_read;
    }

    public void setIs_read(String is_read) {
        this.is_read = is_read;
    }

}
