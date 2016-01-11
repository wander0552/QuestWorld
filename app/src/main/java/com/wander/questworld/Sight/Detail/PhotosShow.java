package com.wander.questworld.Sight.Detail;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.wander.model.AttractionPhoto;
import com.wander.questworld.R;

import java.util.List;

public class PhotosShow extends ActionBarActivity {

    private int position;
    private List<AttractionPhoto> list;
    private ViewPager viewPager;
    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos_show);
        Bundle extras = getIntent().getExtras();
        position = extras.getInt("position", 0);
        list = extras.getParcelableArrayList("list");

        initViewPager();
    }

    void initViewPager() {
        viewPager = (ViewPager) findViewById(R.id.sight_photo_viewpager);
        manager = getSupportFragmentManager();
        viewPager.setAdapter(new PhotoAdapter(manager, list));
        viewPager.setOffscreenPageLimit(2);
        viewPager.setCurrentItem(position);
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
