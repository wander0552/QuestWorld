package com.wander.Json;

import android.media.JetPlayer;

import com.wander.model.Comments;
import com.wander.model.Info;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by wander on 2015/6/13.
 * time 15:59
 * Email 18955260352@163.com
 */
public class CommentJson {
    public static List<Info> getInfo(String json){
        List<Info> list = new ArrayList<Info>();
        try {
            JSONArray array = new JSONArray(json);
            int length = array.length();
            if (length > 0) {
                for (int i = 0; i < length; i++) {
                    JSONObject object = array.getJSONObject(i);
                    Info info=new Info();
                    info.setIns_id(object.optString("ins_id"));
                    info.setContent(object.optString("content"));
                    info.setCreate_time(object.optString("create_time"));
                    info.setIs_read(object.optString("is_read"));
                    info.setMessage(object.optString("message"));
                    String pic_list = object.optString("pics");
                    info.setPics(pic_list.substring(1, pic_list.length() - 1));
                    info.setType(object.optString("type"));
                    info.setUser_header(object.optString("user_header"));
                    info.setUsername(object.optString("username"));
                    info.setUser_id(object.optString("user_id"));
                    list.add(info);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Collections.sort(list, new Comparator<Info>() {
            @Override
            public int compare(Info lhs, Info rhs) {
                return rhs.getCreate_time().compareTo(lhs.getCreate_time());
            }
        });
        return list;
    }

    public static List<Comments> getComments(String json) {
        List<Comments> list = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(json);
            int length = array.length();
            if (length > 0) {
                for (int i = 0; i < length; i++) {
                    JSONObject object = array.getJSONObject(i);
                    Comments comments = new Comments();
                    comments.setUser_id(object.optString("user_id"));
                    comments.setIns_id(object.optString("ins_id"));
                    comments.setFrom_id(object.optString("from_id"));
                    comments.setContent(object.optString("content"));
                    comments.setCreate_time(object.optString("create_time"));
                    comments.setFrom_header(object.optString("from_header"));
                    comments.setFrom_username(object.optString("from_username"));
                    comments.setTo_header(object.optString("to_header"));
                    comments.setTo_username(object.optString("to_username"));
                    list.add(comments);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
