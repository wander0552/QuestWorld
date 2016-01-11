package com.wander.model;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Table;

/**
 * Created by wander on 2015/5/21.
 * time 9:47
 * Email 18955260352@163.com
 */

@Table(name = "user",execAfterTableCreated = "CREATE UNIQUE INDEX USER_INDEX ON USER(phone)")
public class User {
    @Column(column = "id")
    private String id;
    @Column(column = "phone")
    private String phone;
    @Column(column = "username")
    private String username;
    @Column(column = "token")
    private String token;
    @Column(column = "header")
    private String header;
    @Column(column = "sex")
    private String sex;
    @Column(column = "city")
    private String city;
    @Column(column = "message")
    private String message;
    @Column(column = "code")
    private int code;
    @Column(column = "sign")
    private String sign;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
