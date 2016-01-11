package com.wander.questworld;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import com.wander.MyAdapter.InsPhotoAdapter;

import java.util.Arrays;
import java.util.List;


public class InsPhotoActivity extends ActionBarActivity {

    private List<String> pic_list;
    private ViewPager viewPager;
    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ins_photo);

        String pic=getIntent().getExtras().getString("pic");
        pic_list = Arrays.asList(pic.split(","));

        initViewPager();
    }

    void initViewPager() {
        viewPager = (ViewPager) findViewById(R.id.ins_photo_viewpager);
        manager = getSupportFragmentManager();
        viewPager.setAdapter(new InsPhotoAdapter(manager, pic_list));
        viewPager.setOffscreenPageLimit(2);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {
                //todo  图片浏览进度

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

}
