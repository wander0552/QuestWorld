package com.wander.questworld.User;

import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.wander.MyAdapter.MyPagerAdapter;
import com.wander.MyAdapter.UserPagerAdapter;
import com.wander.MyView.PageSlidingLinearStrip;
import com.wander.model.User;
import com.wander.questworld.R;
import com.wander.xutils.BtUtils;
import com.wander.xutils.MyDB;

public class UserActivity extends ActionBarActivity {
    @ViewInject(R.id.user_icon)
    private ImageView user_icon;
    @ViewInject(R.id.user_name)
    private TextView user_name;
    @ViewInject(R.id.user_sign)
    private TextView user_sign;
    @ViewInject(R.id.Viewpager)
    private ViewPager viewPager;
    @ViewInject(R.id.tabs)
    private PageSlidingLinearStrip tabs;
    @ViewInject(R.id.my_toolbar)
    private Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ViewUtils.inject(this);

        initToolBar();
        initTabs();
        initUserInfo();
    }

    void initUserInfo(){
        try {
            User user = MyDB.getDbUtil().findFirst(User.class);
            BtUtils.getIconBitmapUtils(this).display(user_icon,user.getHeader());
            user_name.setText(user.getUsername());
            user_sign.setText(user.getSign());
        } catch (DbException e) {
            e.printStackTrace();
        }
    }


    void initToolBar() {
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("个人中心");
    }

    void initTabs() {

        initTabsValue();
        viewPager.setAdapter(new UserPagerAdapter(getSupportFragmentManager()));
        tabs.setViewPager(viewPager);
        tabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
//                colorChange(arg0);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });

    }
    /**
     * mPagerSlidingTabStrip默认值配置
     */
    private void initTabsValue() {
        // 底部游标颜色
        tabs.setIndicatorColor(getResources().getColor(R.color.primary));
        // tab背景
        tabs.setBackgroundColor(0xFFFFFFFF);
        //设置tab的文字大小
//        tabs.setTabTextSize(SystemInfo.heightPixels/12);
        // tab底线高度
        tabs.setUnderlineHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                1, getResources().getDisplayMetrics()));
        // 游标高度
        tabs.setIndicatorHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                4, getResources().getDisplayMetrics()));
        // 选中的文字颜色
        tabs.setSelectColor(getResources().getColor(R.color.primary));
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
