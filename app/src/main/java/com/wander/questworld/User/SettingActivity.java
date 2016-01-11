package com.wander.questworld.User;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.wander.MyUtils.CacheCleanManager;
import com.wander.MyUtils.SystemInfo;
import com.wander.model.User;
import com.wander.questworld.AppCreate;
import com.wander.questworld.Login.LoginActivity;
import com.wander.questworld.R;
import com.wander.questworld.Update.UpdateUtils;
import com.wander.xutils.MyDB;

public class SettingActivity extends ActionBarActivity implements View.OnClickListener {
    @ViewInject(R.id.setting_exit)
    private Button exit;
    @ViewInject(R.id.cache_size)
    private TextView cache_size;
    @ViewInject(R.id.clear_cache)
    private LinearLayout clear_cache;
    @ViewInject(R.id.check_update)
    private LinearLayout check_update;
    @ViewInject(R.id.feedback)
    private TextView feedback;
    @ViewInject(R.id.abort)
    private TextView abord;
    @ViewInject(R.id.version)
    private TextView tv_version;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ViewUtils.inject(this);
        setActionBar();
        initView();
    }

    void setActionBar() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        myToolbar.setLogo(R.drawable.logo16);
        setTitle("设置");
    }

    void initView() {
        exit.setOnClickListener(this);
        clear_cache.setOnClickListener(this);
        check_update.setOnClickListener(this);
        feedback.setOnClickListener(this);
        abord.setOnClickListener(this);
        try {
            cache_size.setText(CacheCleanManager.getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
        PackageManager manager = getPackageManager();
        String version = "1.0";
        try {
            PackageInfo info = manager.getPackageInfo(getPackageName(), 0);
            version = info.versionName;
            tv_version.setText("当前版本"+version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setting_exit:
                SystemInfo.saveLogin(false);
                try {
                    MyDB.getDbUtil().deleteAll(User.class);
                    AppCreate.id = "";
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                } catch (DbException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.clear_cache:
                try {
                    CacheCleanManager.clearAllCache(this);
                } catch (Exception e) {
                }
                cache_size.setText("0");
                Toast.makeText(this, "清理完成", Toast.LENGTH_SHORT).show();
                break;
            case R.id.check_update:
                UpdateUtils.checkUpdate(this);
        }
    }
}
