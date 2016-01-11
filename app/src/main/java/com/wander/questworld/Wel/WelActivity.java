package com.wander.questworld.Wel;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Fade;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.wander.MyUtils.SystemInfo;
import com.wander.MyUtils.urlUtils;
import com.wander.questworld.MainActivity;
import com.wander.questworld.R;
import com.wander.xutils.BtUtils;

import thirdparty.SystemBarTint.SystemBarTintManager;


public class WelActivity extends Activity {

    @ViewInject(R.id.Welcome)
    private ImageView welcome;
    private boolean first;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //允许使用transition
        setContentView(R.layout.activity_wel);
        ViewUtils.inject(this);
        setStatu();

        first = SystemInfo.sharedPreferences.getBoolean("first", true);
        BtUtils.getBitmapUtils(this).display(welcome, urlUtils.WEL_PUSH, new BitmapLoadCallBack<ImageView>() {
            @Override
            public void onLoadCompleted(ImageView imageView, String s, Bitmap bitmap, BitmapDisplayConfig bitmapDisplayConfig, BitmapLoadFrom bitmapLoadFrom) {
                imageView.setImageBitmap(bitmap);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (first) {
                            intent(FirstOpen.class);
                        } else {
                            intent(MainActivity.class);
                        }
                    }
                }, 1000);
            }

            @Override
            public void onLoadFailed(ImageView imageView, String s, Drawable drawable) {
                if (first) {
                    intent(FirstOpen.class);
                } else {
                    intent(MainActivity.class);
                }
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }

    void setStatu() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void intent(Class intentActivity) {
        Intent intent = new Intent(this, intentActivity);
//        if (SystemInfo.version >= Build.VERSION_CODES.LOLLIPOP) {
//
//            // 设置一个enter transition
//            Fade fade = new Fade();
//            fade.setDuration(3000);
//            getWindow().setEnterTransition(fade);
//            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
//            finishAfterTransition();
//        } else {
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
//        }
    }
}
