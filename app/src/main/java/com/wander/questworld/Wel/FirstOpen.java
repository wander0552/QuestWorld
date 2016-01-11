package com.wander.questworld.Wel;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.wander.MyUtils.SystemInfo;
import com.wander.questworld.MainActivity;
import com.wander.questworld.R;

import java.util.ArrayList;
import java.util.List;

public class FirstOpen extends Activity {
    @ViewInject(R.id.first_open_viewpager)
    private ViewPager openViewpager;
    @ViewInject(R.id.dot_image)
    private ImageView dot_image;


    private int[] imageRes = {R.raw.android, R.raw.wel1};
    private int[] dot_images={R.raw.intro_1_indicator,R.raw.intro_2_indicator,R.raw.intro_3_indicator};
    private List<View> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 允许使用transitions
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_firstopen);
        setStatu();
        ViewUtils.inject(this);

        initViewpager();
    }

    void setStatu() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
    void initViewpager() {
        initIamgeList();
        openViewpager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(list.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(list.get(position));
                return list.get(position);
            }
        });
        openViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                dot_image.setImageResource(dot_images[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    void initIamgeList() {
        list = new ArrayList<>();
        for (int i = 0; i < imageRes.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(imageRes[i]);
            list.add(imageView);
        }
        View view = getLayoutInflater().inflate(R.layout.activity_first_open_last, null, false);
        Button enter = (Button) view.findViewById(R.id.first_open_enter);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent();
            }
        });
        list.add(view);
    }

    public void intent() {
        editPreference();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
    }

    void editPreference() {
        SharedPreferences.Editor edit = SystemInfo.sharedPreferences.edit();
        //todo 第一次启动
        edit.putBoolean("first", false);
        edit.commit();
    }
}
