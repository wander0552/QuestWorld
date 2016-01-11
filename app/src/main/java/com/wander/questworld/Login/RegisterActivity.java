package com.wander.questworld.Login;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.graphics.Palette;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appdatasearch.DocumentContents;
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
import com.wander.MyUtils.ImageUtils;
import com.wander.MyUtils.SDCardHelper;
import com.wander.MyUtils.SystemInfo;
import com.wander.MyUtils.urlUtils;
import com.wander.model.User;
import com.wander.questworld.AppCreate;
import com.wander.questworld.MainActivity;
import com.wander.questworld.R;
import com.wander.questworld.User.UserActivity;
import com.wander.xutils.MyDB;
import com.wander.xutils.MyHttp;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RegisterActivity extends Activity implements View.OnClickListener {
    @ViewInject(R.id.commit)
    private Button commit;
    @ViewInject(R.id.register_back)
    private ImageView back;
    @ViewInject(R.id.register_header)
    private ImageView header;
    @ViewInject(R.id.register_password)
    private EditText password;
    @ViewInject(R.id.register_username)
    private EditText username;
    @ViewInject(R.id.radio)
    private RadioGroup radio;


    private String phone = "未绑定手机";
    private String header_dat = "";
    private String sex = "未知";
    private String name;
    private String pass;

    public static final int KITKAT = 1;//4.4以前
    public static final int NOKITKAT = 2;//4.4以后uri处理
    private static final int PHOTO_REQUEST_CUT = 3;//裁剪结果

    private boolean hasHeader = false;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ViewUtils.inject(this);
        setSkin();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            phone = extras.getString("phone", "18955");
        }

        initView();
        getData();
    }

    void initView() {
        commit.setOnClickListener(this);
        header.setOnClickListener(this);
        back.setOnClickListener(this);

    }

    void getData() {
        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.man) {
                    sex = "男";
                } else if (checkedId == R.id.woman) {
                    sex = "女";
                }
            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String s1 = s.toString();
                if (s1.length() > 6) {
                    commit.setEnabled(true);
                } else {
                    commit.setEnabled(false);
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

    void showProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(R.style.CustomProgressDialog);
        progressDialog.show();
    }

    private void Register() {
        String url = urlUtils.Register;
        //`username`, `password`, `city`, `sex`, `header`, `phone_num`, 'os_version'
        RequestParams params = new RequestParams();
        params.addBodyParameter("username", name);
        params.addBodyParameter("password", pass);
        params.addBodyParameter("city", "city");
        params.addBodyParameter("sex", sex);
        if (hasHeader) {
            params.addBodyParameter("img_header", new File(header_dat));
        } else {
            params.addBodyParameter("img_header", "");
            url = urlUtils.Register2;
        }
        params.addBodyParameter("phone_num", phone);
        params.addBodyParameter("os_version", SystemInfo.version + "");
        MyHttp.getInstance().send(HttpRequest.HttpMethod.POST, url, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        commit.setEnabled(false);
                        showProgressDialog();
                    }

                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                        super.onLoading(total, current, isUploading);
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }
                        Log.d("regiter", responseInfo.result);
                        User user = LoginJson.getUser(responseInfo.result);
                        Toast.makeText(RegisterActivity.this, user.getMessage(), Toast.LENGTH_SHORT).show();
                        if (user.getCode() == 200) {
                            try {
                                DbUtils dbUtil = MyDB.getDbUtil();
                                dbUtil.deleteAll(User.class);
                                dbUtil.save(user);
                                SystemInfo.saveLogin(true);
                            } catch (DbException e) {
                                e.printStackTrace();
                            }
                            AppCreate.id = user.getId();
                            startActivity(new Intent(RegisterActivity.this, UserActivity.class));
                            finish();
                        }
                        if (user.getCode() == 400) {
                            commit.setEnabled(true);
                        }
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        commit.setEnabled(true);
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_back:
                finish();
                break;
            case R.id.register_header:
                //使用是系统的相册选择图片
                Intent intent = new Intent();
                intent.setType("image/*");
                if (SystemInfo.version >= Build.VERSION_CODES.KITKAT) {
                    intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                    startActivityForResult(intent, KITKAT);
                } else {
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, NOKITKAT);
                }
                break;
            case R.id.commit:
                name = username.getText().toString();
                pass = password.getText().toString();
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(pass)) {
                    Register();
                } else {
                    Toast.makeText(this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == KITKAT) {
            if (data != null) {
                String content=null;
                Uri uri = data.getData();
                if (!DocumentsContract.isDocumentUri(RegisterActivity.this, uri)) {
                    content = ImageUtils.getPath(this, uri);
                    uri=Uri.parse(content);
                }
                startPhotoZoom(uri, 150);
            }
        }
        else if (requestCode == NOKITKAT) {
            if (data != null) {
                Uri uri = data.getData();
                startPhotoZoom(uri, 150);
            }
        }
        if (requestCode == PHOTO_REQUEST_CUT) {
            if (data != null) {
                setPicToView(data);
            }
        }
    }

    private void startPhotoZoom(Uri uri, int size) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", size);
        intent.putExtra("outputY", size);
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("return-data", true);

        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    //将进行剪裁后的图片显示到UI界面上
    private void setPicToView(Intent picdata) {
        Bundle bundle = picdata.getExtras();
        if (bundle != null) {
            Bitmap photo = bundle.getParcelable("data");
            header_dat = ImageUtils.saveHeader(this, photo);
            hasHeader = true;
            Log.d("header", header_dat);
            Bitmap bitmap = Bitmap.createBitmap(150, 150, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            //设置画笔的填充物
            paint.setShader(new BitmapShader(photo, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
            canvas.drawCircle(150 / 2, 75, 75, paint);
            header.setImageBitmap(bitmap);
        }
    }
}
