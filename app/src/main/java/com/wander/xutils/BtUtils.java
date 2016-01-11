package com.wander.xutils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.wander.MyUtils.EncryptUtil;
import com.wander.MyUtils.SDCardHelper;
import com.wander.questworld.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2015/5/11.
 * Email:18955260352@163.com
 */
public class BtUtils {
    private static BitmapUtils bitmapUtils;
    private static BitmapUtils bitmapUtils0;
    private static BitmapUtils icon_bitmapUtils;
    private static String thumbPath;
    private static String absolutePath;

    public static BitmapUtils getBitmapUtils(Context context) {
        if (bitmapUtils == null) {
/*            File thumbcacheDir = new File(SDCardHelper.getSDCardPublicDir(Environment.DIRECTORY_PICTURES), File.separator + "thumbs");
            if (!thumbcacheDir.exists()) {
                thumbcacheDir.mkdirs();
            }
            //指定的缓存路径
            thumbPath = thumbcacheDir.getAbsolutePath();*/

            File cacheDir = new File(SDCardHelper.getSDCardPrivateFilesDir(context,Environment.DIRECTORY_PICTURES), File.separator + "QuestWord");
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            //设置内存缓存大小
            //指定默认的缓存路径
            absolutePath = cacheDir.getAbsolutePath();
            bitmapUtils = new BitmapUtils(context, absolutePath, 0.4f, 1024 * 20);
            //设置加载用图片等
            bitmapUtils.configDefaultLoadingImage(R.drawable.bg_discover_white);
            AlphaAnimation animation = new AlphaAnimation(0, 1);
//            animation.setDuration(300);
            bitmapUtils.configDefaultImageLoadAnimation(animation);
            bitmapUtils.configDefaultLoadFailedImage(R.drawable.dashboard_img_default);
        }
        return bitmapUtils;
    }

    public static BitmapUtils getIconBitmapUtils(Context context) {
        if (bitmapUtils == null) {
            File cacheDir = new File(SDCardHelper.getSDCardPrivateFilesDir(context,Environment.DIRECTORY_PICTURES), File.separator + "icon");
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            //设置内存缓存大小
            //指定默认的缓存路径
            absolutePath = cacheDir.getAbsolutePath();
            bitmapUtils = new BitmapUtils(context, absolutePath, 0.3f, 1024 * 20);
            //设置加载用图片等
            AlphaAnimation animation = new AlphaAnimation(0, 1);
            animation.setDuration(100);
            bitmapUtils.configDefaultImageLoadAnimation(animation);
            bitmapUtils.configDefaultLoadFailedImage(R.drawable.blog_avator_default);
        }
        return bitmapUtils;
    }

    public static BitmapUtils getMasterPhoto(Context context) {
        if (bitmapUtils0 == null) {
            bitmapUtils0 = new BitmapUtils(context);
//            bitmapUtils0.configDefaultShowOriginal(true);
//            bitmapUtils0.configDiskCacheEnabled(false);
            //设置加载用图片等
            bitmapUtils0.configDefaultLoadFailedImage(R.drawable.skyblue_logo_wechat);
        }
        return bitmapUtils0;
    }

    public static Bitmap getLocal(String imageurl) {

        File file = new File(thumbPath, urlToMd5(imageurl));
        if (file.exists()) {
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(
                        file));
                Log.i("getfile", "获取本地缩略图");
                return bitmap;
            } catch (FileNotFoundException e) {
                return null;
            }
        }
        return null;
    }

    public static String saveLocal(String name, Bitmap bitmap1) {
        File file = new File(thumbPath, urlToMd5(name));
        FileOutputStream outputStream = null;
        try {
            if (SDCardHelper.isSDCardMounted()) {
                outputStream = new FileOutputStream(file);
                bitmap1.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                Log.i("sava", "保存本地成功");
            }

            if (outputStream != null) {
                outputStream.close();
            }
        } catch (IOException e) {
            Log.i("sava", "保存本地失败");
            return "";
        }
        return file.getAbsolutePath();

    }


    public static Bitmap createThumbnail(String path, int height, int width) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        int oldHeight = options.outHeight;
        int oldWidth = options.outWidth;

        int ratioHeight = oldHeight / height;
        int ratioWidth = oldWidth / width;

        options.inSampleSize = ratioHeight > ratioWidth ? ratioWidth
                : ratioHeight;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inJustDecodeBounds = false;
        Bitmap bm = BitmapFactory.decodeFile(path, options);
        return bm;
    }

    public static String urlToMd5(String url) {
        return EncryptUtil.toHex(EncryptUtil.md5(url.getBytes()));
    }
}
