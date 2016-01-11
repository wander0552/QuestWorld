package com.wander.MyUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wander on 2015/6/1.
 * time 18:06
 * Email 18955260352@163.com
 */
public class JsonUtils {

    public static final String code = "code";

    //判断是否成功
    public static boolean checkSuccess(String result) {
        try {
            JSONObject object = new JSONObject(result);
            if (object.getInt(code) == 200) {
                return true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }
}
