package com.wander.questworld.Sight.Detail;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.wander.model.AttractionPhoto;
import com.wander.questworld.PhotoFragment;

import java.util.List;

/**
 * Created by wander on 2015/5/25.
 * time 15:21
 * Email 18955260352@163.com
 */
public class PhotoAdapter extends FragmentPagerAdapter {
    private List<AttractionPhoto> list;
    public PhotoAdapter(FragmentManager fm,List<AttractionPhoto> list) {
        super(fm);
        this.list=list;
    }

    @Override
    public Fragment getItem(int position) {
        AttractionPhoto photo = list.get(position);
        return PhotoFragment.newInstance(photo.getImage_url(),photo.getDescription());
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
