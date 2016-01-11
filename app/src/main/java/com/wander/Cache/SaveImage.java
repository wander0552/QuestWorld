package com.wander.Cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.wander.MyUtils.SDCardHelper;
import com.wander.xutils.MyHttp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by wander on 2015/5/25.
 * time 21:09
 * Email 18955260352@163.com
 */
public class SaveImage {

    public static void saveBitmap(Bitmap bitmap,Context context) {
        final File dir = new File(SDCardHelper.getSDCardBaseDir() + File.separator + "QuestWord");
        if (!dir.exists()){
            dir.mkdir();
        }
        File file = new File(dir,File.separator+ System.currentTimeMillis() + ".jpg");

        try {
            FileOutputStream fileOutputStream=new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
            Toast.makeText(context, "保存至" + dir, Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(context, "保存失败", Toast.LENGTH_SHORT).show();
        }
    }

    public static void saveImage(String url, final Context context) {
        final String dir = SDCardHelper.getSDCardBaseDir() + File.separator + "QuestWord" + File.separator;
        String filename = dir + System.currentTimeMillis() + ".jpg";
        MyHttp.getInstance().download(url, filename, new RequestCallBack<File>() {
            @Override
            public void onSuccess(ResponseInfo<File> responseInfo) {
                Toast.makeText(context, "保存至" + dir, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(context, "保存失败", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
