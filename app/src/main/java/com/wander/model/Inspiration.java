package com.wander.model;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Table;

/**
 * Created by wander on 2015/5/30.
 * time 18:07
 * Email 18955260352@163.com
 */
@Table(name = "inspiration", execAfterTableCreated = "CREATE UNIQUE INDEX INS_INDEX ON inspiration(id)")
public class Inspiration {
    @Column(column = "id")
    private String id;
    //关注为1  未关注为0
    @Column(column = "attention")
    private String attention;
    @Column(column = "username")
    private String username;
    @Column(column = "create_time")
    private String create_time;
    @Column(column = "content")
    private String content;
    @Column(column = "pic_list")
    private String pic_list;
    @Column(column = "video")
    private String video;
    @Column(column = "tag")
    private String tag;
    @Column(column = "user_id")
    private String user_id;
    @Column(column = "hot")
    private String hot;
    @Column(column = "comment_count")
    private String comment_count;
    //喜欢为1  不喜欢为0
    @Column(column = "like")
    private String like;
    @Column(column = "header")
    private String header;
    @Column(column = "address")
    private String address;
    @Column(column = "hot_count")
    private String hot_count;

    @Override
    public String toString() {
        return "Inspiration{" +
                "id='" + id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", hot='" + hot + '\'' +
                ", like='" + like + '\'' +
                ", tag='" + tag + '\'' +
                ", pic_list='" + pic_list + '\'' +
                ", create_time='" + create_time + '\'' +
                ", username='" + username + '\'' +
                ", attention='" + attention + '\'' +
                '}';
    }

    public String getHot_count() {
        return hot_count;
    }

    public void setHot_count(String hot_count) {
        this.hot_count = hot_count;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAttention() {
        return attention;
    }

    public void setAttention(String attention) {
        this.attention = attention;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getPic_list() {
        return pic_list;
    }

    public void setPic_list(String pic_list) {
        this.pic_list = pic_list;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getHot() {
        return hot;
    }

    public void setHot(String hot) {
        this.hot = hot;
    }

    public String getComment_count() {
        return comment_count;
    }

    public void setComment_count(String comment_count) {
        this.comment_count = comment_count;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

}
