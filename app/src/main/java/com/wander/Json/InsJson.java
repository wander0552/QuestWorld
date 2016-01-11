package com.wander.Json;

import com.wander.model.Inspiration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wander on 2015/5/30.
 * time 18:27
 * Email 18955260352@163.com
 */
public class InsJson {

    public static List<Inspiration> getHot(String json) {
        List<Inspiration> list = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(json);
            int length = array.length();
            if (length > 0) {
                for (int i = 0; i < length; i++) {
                    JSONObject object = array.getJSONObject(i);
                    Inspiration inspiration = new Inspiration();
                    inspiration.setAttention(object.optString("attention"));
                    inspiration.setId(object.optString("ins_id"));
                    inspiration.setUsername(object.optString("username"));
                    inspiration.setCreate_time(object.optString("create_time"));
                    inspiration.setContent(object.optString("content"));
                    String pic_list = object.optString("pic_list");
                    inspiration.setPic_list(pic_list.substring(1, pic_list.length() - 1));
                    inspiration.setVideo(object.optString("video"));
                    String tag = object.optString("tag");
                    inspiration.setTag(tag.substring(1,tag.length()-1));
                    inspiration.setUser_id(object.optString("user_id"));
                    inspiration.setHot(object.optString("ishot"));
                    inspiration.setComment_count(object.optString("comment_count"));
                    inspiration.setLike(object.optString("like"));
                    inspiration.setHeader(object.optString("header"));
                    inspiration.setAddress(object.optString("address"));
                    inspiration.setHot_count(object.optString("hot_count"));
                    list.add(inspiration);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
