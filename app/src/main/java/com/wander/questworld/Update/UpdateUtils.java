package com.wander.questworld.Update;

/**
 * Created by wander on 2015/6/14.
 * time 20:57
 * Email 18955260352@163.com
 */


import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.wander.MyUtils.SDCardHelper;
import com.wander.MyUtils.urlUtils;
import com.wander.questworld.MainActivity;
import com.wander.questworld.R;
import com.wander.xutils.MyHttp;

import java.io.File;

/**
 * 返回为1存在更新  0不用更新
 */
public class UpdateUtils {

    private static HttpUtils httpUtils;
    private static String fileName;

    public static void checkUpdate(final Context context) {
        PackageManager manager = context.getPackageManager();
        String version = "1.0";
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            version = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        httpUtils = MyHttp.getInstance();
        httpUtils.send(HttpRequest.HttpMethod.GET, urlUtils.CHECK_UPDATE + version, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {

                if (responseInfo.result.equals("1")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("有版本可更新");
                    builder.setIcon(R.drawable.logo28);
                    builder.setMessage("新版本新特性,解决bug");
                    builder.setNegativeButton("取消", null);
                    builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            downloadUpdate(context);
                        }
                    });
                    builder.show();
                } else {
                    Toast.makeText(context, "已经是最新版本", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });

    }

    public static void checkUpdate0(final Context context) {
        PackageManager manager = context.getPackageManager();
        String version = "1.0";
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            version = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        httpUtils = MyHttp.getInstance();
        httpUtils.send(HttpRequest.HttpMethod.GET, urlUtils.CHECK_UPDATE + version, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {

                if (responseInfo.result.equals("1")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("有版本可更新");
                    builder.setIcon(R.drawable.logo28);
                    builder.setMessage("新版本新特性,解决bug");
                    builder.setNegativeButton("取消", null);
                    builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            downloadUpdate(context);
                        }
                    });
                    builder.show();
                } else {
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });

    }


    public static void downloadUpdate(final Context context) {
        String apkName = "QuestWorld.apk";
        //安装文件apk路径
        fileName = SDCardHelper.getSDCardPrivateFilesDir(context, Environment.DIRECTORY_DOWNLOADS) + File.separator + apkName;
        final NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        builder.setSmallIcon(R.drawable.logo80);
        httpUtils.download(urlUtils.DOWNLOAD_UPDATE, fileName, new RequestCallBack<File>() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onLoading(long total, long current, boolean isUploading) {
                super.onLoading(total, current, isUploading);
                RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.down_notify);
//                views.setTextViewText(R.id.down_notify_text, "正在下载");
                views.setProgressBar(R.id.down_progress, 100, (int) (100 * current / total), false);
                builder.setContent(views);
                PendingIntent p = PendingIntent.getActivity(context, 0, new Intent(context,MainActivity.class),PendingIntent.FLAG_UPDATE_CURRENT);//这个非要不可。
                builder.setContentIntent(p);
                manager.notify(100, builder.build());
            }

            @Override
            public void onSuccess(ResponseInfo<File> responseInfo) {
                builder.setContentTitle("提示：");
                builder.setContentText("文件下载完毕！");
//                builder.setAutoCancel(true);
                install(context);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(context, "网络异常", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public static void install(Context context) {

        //创建URI
        Uri uri = Uri.fromFile(new File(fileName));
        //创建Intent意图
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//启动新的activity
        //设置Uri和类型
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        //执行安装
        context.startActivity(intent);

    }


}
