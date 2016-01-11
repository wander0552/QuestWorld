package com.wander.questworld.Comment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.wander.Json.CommentJson;
import com.wander.MyAdapter.CommentAdapter;
import com.wander.MyUtils.JsonUtils;
import com.wander.MyUtils.SystemInfo;
import com.wander.MyUtils.VeDate;
import com.wander.MyUtils.urlUtils;
import com.handmark.pulltorefresh.library.PullToRefreshRecyclerView;
import com.wander.model.Comments;
import com.wander.questworld.AppCreate;
import com.wander.questworld.R;
import com.wander.xutils.MyHttp;

import java.util.ArrayList;
import java.util.List;

public class Comment extends AppCompatActivity implements View.OnClickListener, CommentAdapter.OnItemClickListener {
    private String ins_id;
    private String user_id;
    private RecyclerView recyclerView;
    private PullToRefreshRecyclerView pullToRefreshRecyclerView;
    private LinearLayoutManager manager;
    private EditText edit_comment;
    private TextView send;
    private TextView shadow;
    private TextView no_comments;
    private HttpUtils httpUtils;
    private String time;
    private boolean isRefresh = true;
    private ProgressDialog progressDialog;
    //评论给谁
    private String to_id;
    private String username = AppCreate.username;
    private String to_username;
    private CommentAdapter adapter;
    private List<Comments> list;
    private boolean first_in=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        httpUtils = MyHttp.getInstance();
        Bundle bundle = getIntent().getExtras();
        ins_id = bundle.getString("ins_id");
        user_id = bundle.getString("user_id");
        to_id = user_id;
        time = VeDate.getStringDate();
        initView();
    }


    public void initView() {
        setToolBar();
        setRecycler();
        edit_comment = (EditText) findViewById(R.id.edit_comment);
        send = (TextView) findViewById(R.id.send_comment);
        no_comments = (TextView) findViewById(R.id.no_comments);
        shadow = (TextView) findViewById(R.id.comment_shadow);
        send.setOnClickListener(this);
        edit_comment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    send.setEnabled(true);
                    send.setTextColor(getResources().getColor(R.color.primary));
                } else {
                    send.setEnabled(false);
                    send.setTextColor(getResources().getColor(R.color.secondary_text));
                }
            }
        });
    }

    public void setToolBar() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("评论");
    }

    void setRecycler() {
        pullToRefreshRecyclerView = (PullToRefreshRecyclerView) findViewById(R.id.comment_recycle);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        list = new ArrayList<>();
        adapter = new CommentAdapter(list, this);
        recyclerView.setAdapter(adapter);
        adapter.setOnitemClickListener(this);
        setRefresh();
        initData();
    }

    void showProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(R.style.CustomProgressDialog);
        progressDialog.setMessage("努力加载中···");
        progressDialog.show();
    }

    public void initData() {
        RequestParams params = new RequestParams();
        params.addBodyParameter("time", time);
        params.addBodyParameter("ins_id", ins_id);
        httpUtils.send(HttpRequest.HttpMethod.POST, urlUtils.COMMENTS, params, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                if (first_in) {
                    showProgressDialog();
                    first_in=false;
                }
            }

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                List<Comments> commentsList = CommentJson.getComments(responseInfo.result);
                if (isRefresh) {
                    list.clear();
                }
                list.addAll(commentsList);
                if (list.size() == 0) {
                    no_comments.setVisibility(View.VISIBLE);
                }
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
                pullToRefreshRecyclerView.onRefreshComplete();
            }

            @Override
            public void onFailure(HttpException e, String s) {
                progressDialog.dismiss();
                if (list.size() == 0) {
                    no_comments.setVisibility(View.VISIBLE);
                }
                Toast.makeText(Comment.this, "网络异常", Toast.LENGTH_SHORT).show();
            }
        });

    }

    void setRefresh() {
        pullToRefreshRecyclerView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                isRefresh = true;
                time = VeDate.getStringDate();
                initData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                time = list.get(list.size() - 1).getCreate_time();
                isRefresh = false;
                initData();
            }

/*            @Override
            public void onRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                isRefresh = true;
                time = VeDate.getStringDate();
                initData();
            }*/
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

/*           @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int position = manager.findLastVisibleItemPosition();
                int itemCount = manager.getItemCount();
                //dy表示还在向下滑动，当到达倒数第一个自动加载下一页
                if (position >= itemCount - 1 && dy > 0) {
                    time = list.get(list.size() - 1).getCreate_time();
                    isRefresh = false;
                    initData();
                }
            }*/
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_comment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
        }
        if (id == R.id.comment_release) {
            //评论给说说作者
            to_id = user_id;
            edit_comment.setHint("评论点什么吧...");
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (!SystemInfo.sharedPreferences.getBoolean("login", false)) {
            Toast.makeText(this, "您还未登录", Toast.LENGTH_SHORT).show();
        } else {
            RequestParams params = new RequestParams();
            params.addBodyParameter("ins_id", ins_id);
            params.addBodyParameter("user_id", to_id);
            params.addBodyParameter("from_id", AppCreate.id);
            final String s = edit_comment.getText().toString();
            params.addBodyParameter("content", s);
            httpUtils.send(HttpRequest.HttpMethod.POST, urlUtils.ADD_COMMENT, params, new RequestCallBack<String>() {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    if (JsonUtils.checkSuccess(responseInfo.result)) {
                        Toast.makeText(Comment.this, "评论成功", Toast.LENGTH_SHORT).show();
                        edit_comment.clearFocus();
                        edit_comment.setText("");
                        Comments comments = new Comments();
                        comments.setIns_id(ins_id);
                        comments.setFrom_username(username);
                        comments.setTo_username(to_username);
                        comments.setFrom_header(AppCreate.user_header);
                        comments.setContent(s);
                        comments.setCreate_time("刚刚");
                        comments.setUser_id(to_id);
                        comments.setFrom_id(AppCreate.id);
                        adapter.addComment(comments);
                    }
                }

                @Override
                public void onFailure(HttpException e, String s) {
                    Toast.makeText(Comment.this, "网络异常", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    @Override
    public void onItemClick(final String username, final String user_id, final String content) {
//        Toast.makeText(this, user_id + username, Toast.LENGTH_SHORT).show();
        shadow.setVisibility(View.VISIBLE);
        to_username = username;
        View view = LayoutInflater.from(this).inflate(R.layout.comment_click, null);
        TextView reply = (TextView) view.findViewById(R.id.comment_reply);
        TextView report = (TextView) view.findViewById(R.id.comment_report);
        TextView copy = (TextView) view.findViewById(R.id.comment_copy);
        TextView divider = (TextView) view.findViewById(R.id.comment_click_divider);
        TextView divider2 = (TextView) view.findViewById(R.id.comment_click_divider2);
        TextView divider3 = (TextView) view.findViewById(R.id.comment_click_divider3);

        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.date_bg));
        if (user_id.equals(AppCreate.id)) {
            copy.setVisibility(View.GONE);
            report.setVisibility(View.GONE);
            divider.setVisibility(View.GONE);
            divider2.setVisibility(View.GONE);
            divider3.setVisibility(View.GONE);


        }
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_comment.setFocusable(true);
                edit_comment.setHint("回复" + username + ":");
                to_id = user_id;
                popupWindow.dismiss();
            }
        });

        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager manager = (ClipboardManager) Comment.this.getSystemService(Context.CLIPBOARD_SERVICE);
                manager.setPrimaryClip(ClipData.newPlainText(null, content));
                popupWindow.dismiss();
            }
        });
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Comment.this);
                builder.setIcon(R.drawable.logo16);
                builder.setTitle("举报");
                builder.setMessage("无法举报");
                builder.show();

            }
        });

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                shadow.setVisibility(View.GONE);
            }
        });

    }
}
