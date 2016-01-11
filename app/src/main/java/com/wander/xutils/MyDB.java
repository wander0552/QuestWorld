package com.wander.xutils;

import android.content.Context;
import android.widget.Toast;

import com.lidroid.xutils.DbUtils;

/**
 * Created by wander on 2015/5/13.
 * Email:18955260352@163.com
 */
public class MyDB {
    private static DbUtils dbUtil;

    public MyDB(Context context) {
        dbUtil = DbUtils.create(context, "world", 1, new DbUtils.DbUpgradeListener() {
            @Override
            public void onUpgrade(DbUtils dbUtils, int i, int i1) {

            }
        });
    }

    public static DbUtils getDbUtil() {
        if (dbUtil == null) {
        }
        return dbUtil;
    }


}
