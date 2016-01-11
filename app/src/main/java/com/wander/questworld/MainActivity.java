package com.wander.questworld;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SlidingDrawer;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.wander.MyAdapter.MyPagerAdapter;
import com.wander.MyAdapter.SuperAwesomeCardFragment;
import com.wander.MyUtils.MaterialUtils;
import com.wander.MyUtils.SystemInfo;
import com.wander.MyView.PageSlidingLinearStrip;
import com.wander.MyView.PagerSlidingTabStrip;
import com.wander.questworld.Login.LoginActivity;
import com.wander.questworld.Login.RegisterActivity;
import com.wander.questworld.Message.MessageShow;
import com.wander.questworld.Release.PublishedActivity;
import com.wander.questworld.Sight.MapActivity;
import com.wander.questworld.Update.UpdateUtils;
import com.wander.questworld.User.SettingActivity;
import com.wander.questworld.User.UserActivity;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    //xutils注解findView控件
    @ViewInject(R.id.setting)
    private LinearLayout setting;
    @ViewInject(R.id.attention_sliding)
    private LinearLayout attention;
    @ViewInject(R.id.fans_sliding)
    private LinearLayout fans;
    @ViewInject(R.id.message_sliding)
    private LinearLayout message;
    @ViewInject(R.id.my_toolbar)
    private Toolbar myToolbar;
    @ViewInject(R.id.draw_layout)
    private DrawerLayout drawerLayout;
    @ViewInject(R.id.Viewpager)
    private ViewPager viewPager;
    @ViewInject(R.id.tabs)
    private PageSlidingLinearStrip tabs;


    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);

        initToolBar();
        initButton();
        initTabs();
        UpdateUtils.checkUpdate0(this);
    }

    void initButton() {
        setting.setOnClickListener(this);
        message.setOnClickListener(this);
        fans.setOnClickListener(this);
        attention.setOnClickListener(this);

    }

    void initToolBar() {
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    void initTabs() {
//        sliding.setLayoutParams(new DrawerLayout.LayoutParams(SystemInfo.widthPixels/2, ViewGroup.LayoutParams.MATCH_PARENT));
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.setDrawerListener(toggle);

        initTabsValue();
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(4);
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

    //同步抽屉的状态
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
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

    /**
     * 界面颜色的更改
     */
    @SuppressLint("NewApi")
    private void colorChange(int position) {
        // 用来提取颜色的Bitmap
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                SuperAwesomeCardFragment.getBackgroundBitmapPosition(position));
        // Palette的部分
        Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {
            /**
             * 提取完之后的回调方法
             */
            @Override
            public void onGenerated(Palette palette) {
                Palette.Swatch vibrant = palette.getVibrantSwatch();
                /* 界面颜色UI统一性处理,看起来更Material一些 */
                tabs.setBackgroundColor(vibrant.getRgb());
//                tabs.setTextColor(vibrant.getTitleTextColor());
                // 其中状态栏、游标、底部导航栏的颜色需要加深一下，也可以不加，具体情况在代码之后说明
                tabs.setIndicatorColor(MaterialUtils.colorBurn(vibrant.getRgb()));

                myToolbar.setBackgroundColor(vibrant.getRgb());
                if (android.os.Build.VERSION.SDK_INT >= 21) {
                    Window window = getWindow();
                    // 很明显，这两货是新API才有的。
                    window.setStatusBarColor(MaterialUtils.colorBurn(vibrant.getRgb()));
                    window.setNavigationBarColor(MaterialUtils.colorBurn(vibrant.getRgb()));
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean login = SystemInfo.sharedPreferences.getBoolean("login", false);
        switch (item.getItemId()) {
            case R.id.user:
                if (!login) {
                    startActivity(new Intent(this, LoginActivity.class));
                } else {
                    startActivity(new Intent(this, UserActivity.class));
                }
                break;
            case R.id.search:
//                Toast.makeText(this, "test_search", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.release:
                if (login) {
                    startActivity(new Intent(this, PublishedActivity.class));
                } else {
                    Toast.makeText(this, "请登录后在发表", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return toggle.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setting:
                startActivity(new Intent(this, SettingActivity.class));
                drawerLayout.closeDrawers();
                break;
            case R.id.fans_sliding:
                drawerLayout.closeDrawers();
                break;
            case R.id.message_sliding:
                startActivity(new Intent(this, MessageShow.class));
                drawerLayout.closeDrawers();
                break;
            case R.id.attention_sliding:
                drawerLayout.closeDrawers();
                break;
        }
    }

    private long start = 0, end = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if (start == 0) {
                Toast.makeText(getApplicationContext(), "再按一次退出", Toast.LENGTH_SHORT).show();
                start = System.currentTimeMillis();
                return true;
            } else {
                end = System.currentTimeMillis();
                if (end - start < 2000) {
                    finish();
                } else {
                    start = 0;
                    return true;
                }
            }
        }
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            Toast.makeText(this, "menu", Toast.LENGTH_SHORT).show();
            return true;
        }
        return true;
//        return super.onKeyDown(keyCode, event);
    }
}
