package com.wander.MyAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.wander.model.AttractionPhoto;
import com.wander.questworld.PhotoFragment;

import java.util.List;

/**
 * Created by wander on 2015/5/31.
 * time 11:19
 * Email 18955260352@163.com
 */
public class InsPhotoAdapter extends FragmentPagerAdapter {

    private List<String> list;
    public InsPhotoAdapter(FragmentManager fm,List<String> list) {
        super(fm);
        this.list=list;
    }

    @Override
    public Fragment getItem(int position) {
        return PhotoFragment.newInstance(list.get(position),(position+1)+"/"+(list.size()));
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }


    @Override
    public int getCount() {
        return list.size();
    }

}
