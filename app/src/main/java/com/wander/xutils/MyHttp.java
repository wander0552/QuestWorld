package com.wander.xutils;

import com.lidroid.xutils.HttpUtils;

/**
 * Created by wander on 2015/5/13.
 * Email:18955260352@163.com
 */
public class MyHttp {
    private static HttpUtils utils = new HttpUtils();

    public static HttpUtils getInstance() {
        if (utils == null) {
            utils = new HttpUtils();
        }
        return utils;
    }

}
