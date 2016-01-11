package com.wander.questworld.Login;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.wander.Json.LoginJson;
import com.wander.MyUtils.SystemInfo;
import com.wander.MyUtils.urlUtils;
import com.wander.model.User;
import com.wander.questworld.AppCreate;
import com.wander.questworld.MainActivity;
import com.wander.questworld.R;
import com.wander.questworld.User.UserActivity;
import com.wander.xutils.MyDB;
import com.wander.xutils.MyHttp;
import com.wander.xutils.XutilsInit;

import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;


public class LoginActivity extends Activity implements View.OnClickListener {
    @ViewInject(R.id.login)
    private Button login;
    @ViewInject(R.id.login_phone)
    private EditText login_phone;
    @ViewInject(R.id.login_password)
    private EditText login_password;
    @ViewInject(R.id.forgot_password)
    private TextView forgot_password;
    @ViewInject(R.id.login_register)
    private TextView login_register;
    @ViewInject(R.id.qq_login)
    private ImageView qq_login;
    @ViewInject(R.id.wechat_login)
    private ImageView wechat_login;
    @ViewInject(R.id.sina_login)
    private ImageView sina_login;
    @ViewInject(R.id.login_back)
    private ImageView login_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ViewUtils.inject(this);

        setSkin();
        initView();
    }

    void initView() {
        login.setOnClickListener(this);
        login_back.setOnClickListener(this);
        login_register.setOnClickListener(this);
        forgot_password.setOnClickListener(this);
        qq_login.setOnClickListener(this);
        wechat_login.setOnClickListener(this);
        sina_login.setOnClickListener(this);

        login_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String s1 = s.toString();
                if (s1.length() > 0) {
                    login.setEnabled(true);
                } else {
                    login.setEnabled(false);
                }
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    void setSkin() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg_login);
        Palette.Builder builder = new Palette.Builder(bitmap);
        Palette.Swatch swatch = builder.generate().getDarkVibrantSwatch();
        if (SystemInfo.version >= 21) {
            Window window = getWindow();
            window.setStatusBarColor(swatch.getRgb());
//            window.setNavigationBarColor(swatch.getRgb());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_back:
                finish();
                break;
            case R.id.login:
                login();
                break;
            case R.id.forgot_password:
                break;
            case R.id.login_register:
                //TODO register
                register();
//                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.qq_login:
                break;
            case R.id.wechat_login:
                break;
            case R.id.sina_login:
                break;
        }

    }

    void login() {
        String phone = login_phone.getText().toString();
        String password = login_password.getText().toString();
        RequestParams params = new RequestParams();
        params.addBodyParameter("password", password);
        params.addBodyParameter("phone_num", phone);
        params.addBodyParameter("os_version", SystemInfo.version + "");
        MyHttp.getInstance().send(HttpRequest.HttpMethod.POST, urlUtils.LOGIN, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                User user = LoginJson.getUser(responseInfo.result);
                if (user.getCode() == 200) {
                    try {
                        DbUtils dbUtil = MyDB.getDbUtil();
                        dbUtil.deleteAll(User.class);
                        dbUtil.save(user);
                        AppCreate.id=user.getId();
                        SystemInfo.saveLogin(true);
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                    startActivity(new Intent(LoginActivity.this, UserActivity.class));
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, user.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(LoginActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
            }
        });

    }

    void register() {
        initShear();
        //打开注册页面
        RegisterPage registerPage = new RegisterPage();
        registerPage.setRegisterCallback(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                // 解析注册结果
                if (result == SMSSDK.RESULT_COMPLETE) {
                    @SuppressWarnings("unchecked")
                    HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
                    String country = (String) phoneMap.get("country");
                    String phone = (String) phoneMap.get("phone");
                    // 提交用户信息
                    registerUser(country, phone);
                }
            }
        });
        registerPage.show(getApplicationContext());
    }

    void initShear() {
        SMSSDK.initSDK(this, "75c939ff9b54", "480af8c7990ebe8895f123493d2696fb");
    }

    private void registerUser(String country, final String phone) {
        final Bundle bundle = new Bundle();
        bundle.putString("phone", phone);
        MyHttp.getInstance().send(HttpRequest.HttpMethod.GET, urlUtils.ISREGISTER + phone, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                if (responseInfo.result.equals("yes")) {
                    Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "该手机号已经注册，请登录。", Toast.LENGTH_SHORT).show();
                    login_phone.setText(phone);
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(LoginActivity.this,"网络异常，请稍后再试",Toast.LENGTH_SHORT).show();

            }
        });

    }
}
