package com.wander.questworld;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import android.webkit.URLUtil;
import android.widget.Toast;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.wander.Json.LoginJson;
import com.wander.MyUtils.SystemInfo;
import com.wander.MyUtils.urlUtils;
import com.wander.model.User;
import com.wander.questworld.Service.MessageService;
import com.wander.xutils.MyDB;
import com.wander.xutils.MyHttp;
import com.wander.xutils.XutilsInit;

import cn.smssdk.SMSSDK;

/**
 * Created by wander on 2015/5/12.
 * Email:18955260352@163.com
 */
public class AppCreate extends Application {

    private static Application AppCreate;
    public static String id;
    public static String username;
    public static String user_header;
    private static final String TAG = "message";

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    public static Application getInstance() {
        if (AppCreate == null) {
            AppCreate = new Application();
        }
        return AppCreate;
    }

    void init() {
        new SystemInfo(this);
        initXutils();
        checkUser();
        initService();
    }

    void initService() {
        bindService(new Intent(this, MessageService.class), sc, Context.BIND_AUTO_CREATE);
    }

    private MessageService messageService;
    private ServiceConnection sc = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "in onServiceDisconnected");
            messageService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "in onServiceConnected");
            messageService = ((MessageService.MyBinder) service).getService();
        }
    };


    void checkUser() {
        try {
            final DbUtils dbUtil = MyDB.getDbUtil();
            final User user = dbUtil.findFirst(User.class);
            if (user != null) {
                id = user.getId();
                username = user.getUsername();
                user_header = user.getHeader();
                Log.d("userlogin", id + "-----" + user.getToken());
                RequestParams params = new RequestParams();
                params.addBodyParameter("id", user.getId());
                params.addBodyParameter("token", user.getToken());
                MyHttp.getInstance().send(HttpRequest.HttpMethod.POST, urlUtils.CheckUser, params, new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        User user1 = LoginJson.getUser(responseInfo.result);
                        if (user1.getCode() == 200) {
                            try {
                                dbUtil.deleteAll(User.class);
                                dbUtil.save(user1);
                                SystemInfo.saveLogin(true);
                            } catch (DbException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(AppCreate.this, user1.getMessage(), Toast.LENGTH_SHORT).show();
                            SystemInfo.saveLogin(false);
                        }
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {

                    }
                });
            }
        } catch (DbException e) {
            e.printStackTrace();
        }

    }


    private void initXutils() {
        XutilsInit xutilsInit = new XutilsInit(this);
    }

}
