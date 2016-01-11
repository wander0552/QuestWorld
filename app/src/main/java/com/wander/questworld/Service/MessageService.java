package com.wander.questworld.Service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.wander.MyUtils.urlUtils;
import com.wander.questworld.AppCreate;
import com.wander.questworld.Message.MessageShow;
import com.wander.questworld.R;
import com.wander.xutils.BtUtils;
import com.wander.xutils.MyHttp;

import java.net.URLEncoder;
import java.util.Timer;
import java.util.TimerTask;

public class MessageService extends Service {
    public static final String TAG = "MyService";
    public Timer timer = null;
    private TimerTask task = null;

    public MessageService() {
    }

    private final IBinder binder = new MyBinder();

    public class MyBinder extends Binder {
        public MessageService getService() {
            return MessageService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate() executed");
        startTimer();
    }

    public void getData() {
        final NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.logo80);
        MyHttp.getInstance().send(HttpRequest.HttpMethod.GET, urlUtils.INFOR_COUNT + AppCreate.id, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                if (!responseInfo.result.equals("0")) {
                    RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.message_notify);
                    remoteViews.setTextViewText(R.id.message_notify_text, "你有" + responseInfo.result + "条新的动态点击查看");
                    Bitmap bitmap=null;
                    try {
                         bitmap= BtUtils.getIconBitmapUtils(MessageService.this).getBitmapFromMemCache(AppCreate.user_header, null);
                    }catch (Exception e){

                    }
                    if (bitmap != null) {
                        remoteViews.setImageViewBitmap(R.id.message_header, bitmap);
                    }
                    builder.setContent(remoteViews);
                    builder.setAutoCancel(true);
                    PendingIntent pendingIntent = PendingIntent.getActivity(MessageService.this, 200, new Intent(MessageService.this, MessageShow.class), PendingIntent.FLAG_UPDATE_CURRENT);
                    builder.setContentIntent(pendingIntent);
                    manager.notify(100, builder.build());
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }

    public void startTimer() {
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                getData();
            }
        };
        timer.schedule(task, 500, 1000 * 60 * 5);
    }

    public void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand() executed");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() executed");
        stopTimer();
    }
}
