package com.wander.MyUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;

/**
 * Created by wander on 2015/5/14.
 * Email 18955260352@163.com
 */
public class SystemInfo {
    public static final int version = Build.VERSION.SDK_INT;
    public static int heightPixels;
    public static int widthPixels;
    public static SharedPreferences sharedPreferences;

    public SystemInfo(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        heightPixels = metrics.heightPixels;
        widthPixels = metrics.widthPixels;
    }
    public static void saveLogin(boolean isLogin){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("login",isLogin);
        editor.commit();
    }

}
