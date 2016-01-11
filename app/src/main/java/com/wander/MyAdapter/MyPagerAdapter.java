package com.wander.MyAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.wander.questworld.Hot.HotFragment;
import com.wander.questworld.Sight.SightFragment;

/**
 * Created by wander on 2015/5/14.
 * Email:18955260352@163.com
 */
public class MyPagerAdapter extends FragmentPagerAdapter {

    private final String[] TITLES = {"众行", "关注", "天下"};

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0||position==1) {
            return HotFragment.newInstance(position);
        }
        if (position == 2) {
            return SightFragment.newInstance("","");
        }
        return null;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }
}
