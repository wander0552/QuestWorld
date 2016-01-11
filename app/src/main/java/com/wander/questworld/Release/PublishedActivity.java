package com.wander.questworld.Release;

import android.app.Notification;
import android.app.NotificationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
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
import com.wander.MyUtils.urlUtils;
import com.wander.model.User;
import com.wander.questworld.R;
import com.wander.xutils.MyDB;
import com.wander.xutils.MyHttp;

import org.json.JSONException;
import org.json.JSONObject;

public class PublishedActivity extends AppCompatActivity implements OnClickListener {
    @ViewInject(R.id.release_content)
    private EditText content;
    @ViewInject(R.id.release_address)
    private LinearLayout address;
    @ViewInject(R.id.add_tag)
    private ImageView add_tag;
    @ViewInject(R.id.tag_recycler)
    private RecyclerView recyclerView;
    @ViewInject(R.id.noScrollgridview)
    private GridView noScrollgridview;
    @ViewInject(R.id.release_address_info)
    private TextView release_address_info;
    @ViewInject(R.id.publish_tag_no)
    private TextView publish_tag;
    @ViewInject(R.id.release_high)
    private CheckBox release_high;

    private GridAdapter adapter;
    private TagsAdapter tagsAdapter;
    private NotificationManager notificationManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_published);
        ViewUtils.inject(this);
        setActionBar();
        Init();
    }

    void getData() {
        if (Tags.tags.size() != 0) {
            publish_tag.setVisibility(View.INVISIBLE);
        }
    }

    void setActionBar() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("时光足迹");
    }

    public void Init() {
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        add_tag.setOnClickListener(this);
        address.setOnClickListener(this);
        //标签布局初始化
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(manager);
        tagsAdapter = new TagsAdapter();
        tagsAdapter.setOnItemClickListener(new TagsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                tagsAdapter.removeItem(position);
            }
        });
        recyclerView.setAdapter(tagsAdapter);


        //照片显示布局
        noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        this.adapter = new GridAdapter(this);
        this.adapter.update();
        noScrollgridview.setAdapter(this.adapter);
        //给你已选照片添加编辑事件
        noScrollgridview.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                if (arg2 == Bimp.bmp.size()) {
                    new PopupWindows(PublishedActivity.this, noScrollgridview);
                } else {
                    Intent intent = new Intent(PublishedActivity.this,
                            PhotoActivity.class);
                    intent.putExtra("ID", arg2);
                    startActivity(intent);
                }
            }
        });
    }

    public String getString(String s) {
        String path = null;
        if (s == null)
            return "";
        for (int i = s.length() - 1; i > 0; i++) {
            s.charAt(i);
        }
        return path;
    }

    protected void onRestart() {
        getData();
        adapter.update();
        tagsAdapter.notifyDataSetChanged();
        super.onRestart();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.release_address:
                Toast.makeText(this, "地理位置", Toast.LENGTH_SHORT).show();
                break;
            case R.id.add_tag:
                startActivity(new Intent(this, AddTag.class));
                break;
        }

    }

    public class PopupWindows extends PopupWindow {

        public PopupWindows(Context mContext, View parent) {

            View view = View
                    .inflate(mContext, R.layout.item_popupwindows, null);
            view.startAnimation(AnimationUtils.loadAnimation(mContext,
                    R.anim.fade_ins));
            LinearLayout ll_popup = (LinearLayout) view
                    .findViewById(R.id.ll_popup);
            ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
                    R.anim.push_bottom_in_2));

            setWidth(LayoutParams.WRAP_CONTENT);
            setHeight(LayoutParams.WRAP_CONTENT);
            setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_back_nor));
            setFocusable(true);
            setOutsideTouchable(true);
            setContentView(view);
            showAtLocation(parent, Gravity.BOTTOM, 0, 0);
            update();

            Button bt1 = (Button) view
                    .findViewById(R.id.item_popupwindows_camera);
            Button bt2 = (Button) view
                    .findViewById(R.id.item_popupwindows_Photo);
            Button bt3 = (Button) view
                    .findViewById(R.id.item_popupwindows_cancel);
            bt1.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    photo();
                    dismiss();
                }
            });
            bt2.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(PublishedActivity.this,
                            PicsActivity.class);
                    startActivity(intent);
                    dismiss();
                }
            });
            bt3.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    dismiss();
                }
            });

        }
    }

    private static final int TAKE_PICTURE = 0x000000;
    private String path = "";

    public void photo() {
        //todo  take photo
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File dirPath = new File(Environment.getExternalStorageDirectory()
                + "/myimage/");
        if (!dirPath.exists()) {
            dirPath.mkdir();
        }
        File file = new File(dirPath, String.valueOf(System.currentTimeMillis())
                + ".jpg");

        path = file.getPath();
        Uri imageUri = Uri.fromFile(file);
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(openCameraIntent, TAKE_PICTURE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PICTURE:
                if (Bimp.drr.size() < 9 && resultCode == -1) {
                    Bimp.drr.add(path);
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_published, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }
        if (id == R.id.release_commit) {
            final List<String> list = new ArrayList<String>();
            for (int i = 0; i < Bimp.drr.size(); i++) {
                String Str = Bimp.drr.get(i).substring(
                        Bimp.drr.get(i).lastIndexOf("/") + 1,
                        Bimp.drr.get(i).lastIndexOf("."));
                list.add(FileUtils.SDPATH + Str + ".JPEG");
            }
            Log.d("pic_list", "11111" + "----" + list.toString());
            // 高清的压缩图片全部就在  list 路径里面了
            // 高清的压缩过的 bmp 对象  都在 Bimp.bmp里面
            String content = this.content.getText().toString();
            if (content.length() < 1 && list.size() < 1) {
                Toast.makeText(this, "请输入一点东西吧", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    User user = MyDB.getDbUtil().findFirst(User.class);
                    RequestParams params = new RequestParams();
                    params.addBodyParameter("user_id", user.getId());
                    params.addBodyParameter("address", release_address_info.getText().toString());
                    params.addBodyParameter("tag", Tags.tags.toString());
                    params.addBodyParameter("content", content);
                    //判断是否上传图片，选择不同上传路径
                    String url = "";
                    if (list.size() > 0) {
                        url = urlUtils.RELEASE;
                        int size = list.size();
                        if (!release_high.isChecked()) {
                            for (int i = 0; i < size; i++) {
                                params.addBodyParameter("image" + i, new File(list.get(i)));
                            }
                        } else {
                            for (int i = 0; i < size; i++) {
                                params.addBodyParameter("image" + i, new File(Bimp.drr.get(i)));
                            }
                        }
                    } else {
                        url = urlUtils.RELEASE_NO;
                    }
                    //开启上传

                    final Notification.Builder builder = new Notification.Builder(this);
                    MyHttp.getInstance().send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<String>() {

                        @Override
                        public void onStart() {
                            super.onStart();
                            builder.setContentTitle("众行天下");
                            builder.setSmallIcon(R.drawable.logo28);
                            builder.setContentText("图片上传中");
                            builder.setAutoCancel(true);
                            notificationManager.notify(0, builder.build());
                        }

                        @Override
                        public void onLoading(long total, long current, boolean isUploading) {
                            super.onLoading(total, current, isUploading);
                            builder.setProgress(100, (int) (100 * current / total), false);
                        }

                        @Override
                        public void onSuccess(ResponseInfo<String> responseInfo) {
                            builder.setContentText("发布成功");
                            try {

                                JSONObject object = new JSONObject(responseInfo.result);
                                int code = object.getInt("code");
                                Toast.makeText(PublishedActivity.this, object.getString("message"), Toast.LENGTH_SHORT).show();
                                if (code == 200) {
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            notificationManager.cancel(0);
                            // 完成上传服务器后 .........
                            FileUtils.deleteDir();
                            //初始化静态变量
                            list.clear();
                            Bimp.init();
                            Tags.tags.clear();
                        }

                        @Override
                        public void onFailure(HttpException e, String s) {
                            builder.setContentText("发布失败");
                            notificationManager.notify(0, builder.build());

                        }
                    });
                } catch (DbException e) {
                    e.printStackTrace();
                }
                finish();
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
