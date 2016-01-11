package com.wander.Json;

import com.wander.model.User;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wander on 2015/5/21.
 * time 10:03
 * Email 18955260352@163.com
 */
public class LoginJson {
    public static User getUser(String json) {
        User user = new User();
        try {
            JSONObject object = new JSONObject(json);
            user.setCode(object.getInt("code"));
            user.setMessage(object.getString("message"));
            JSONObject data = object.optJSONObject("data");
            if (data != null) {
                user.setId(data.getString("id"));
                user.setUsername(data.optString("username"));
                user.setToken(data.getString("token"));
                user.setSex(data.getString("sex"));
                user.setPhone(data.getString("phone_num"));
                user.setHeader(data.getString("header"));
                user.setCity(data.getString("city"));
                user.setSign(data.getString("sign"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }
}
