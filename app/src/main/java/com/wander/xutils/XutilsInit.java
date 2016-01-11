package com.wander.xutils;

import android.content.Context;

/**
 * Created by wander on 2015/5/13.
 * Email:18955260352@163.com
 */
public class XutilsInit {
    public XutilsInit(Context context){
        MyHttp.getInstance();
        BtUtils.getBitmapUtils(context);
        MyDB db=new MyDB(context);
    }
    public static void init(){

    }
}
