package com.wander.questworld.Message;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.wander.Json.CommentJson;
import com.wander.Json.InsJson;
import com.wander.MyAdapter.InfoAdapter;
import com.wander.MyUtils.VeDate;
import com.wander.MyUtils.urlUtils;
import com.wander.model.Info;
import com.wander.model.Inspiration;
import com.wander.questworld.AppCreate;
import com.wander.questworld.Comment.Comment;
import com.wander.questworld.MainActivity;
import com.wander.questworld.R;
import com.wander.questworld.Service.MessageService;
import com.wander.xutils.MyDB;
import com.wander.xutils.MyHttp;

import java.util.ArrayList;
import java.util.List;

public class MessageShow extends ActionBarActivity implements AdapterView.OnItemClickListener {
    private String time;
    private boolean isRefresh = true;
    private PullToRefreshListView pullToRefreshListView;
    private DbUtils dbUtil;
    private TextView empty;
    private List<Info> list;
    private InfoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_show);
        dbUtil = MyDB.getDbUtil();
        time = VeDate.getStringDate();

        initView();
    }

    void initView() {
        setActionBar();
        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.message_pull_refresh_list);
        empty= (TextView) findViewById(R.id.info_empty);
        setRefresh();
    }

    void setActionBar() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        myToolbar.setLogo(R.drawable.logo16);
        setTitle("消息");
    }

    void setRefresh() {
        list = new ArrayList<>();
        adapter = new InfoAdapter(list, this);
        pullToRefreshListView.setAdapter(adapter);
        pullToRefreshListView.setOnItemClickListener(this);
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        getLocal();


        ILoadingLayout startLabels = pullToRefreshListView
                .getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("下拉刷新...");// 刚下拉时，显示的提示
        startLabels.setRefreshingLabel("正在载入...");// 刷新时
        startLabels.setReleaseLabel("放开刷新...");// 下来达到一定距离时，显示的提示

        ILoadingLayout endLabels = pullToRefreshListView.getLoadingLayoutProxy(
                false, true);
        endLabels.setPullLabel("上拉加载下一页...");// 刚上拉时，显示的提示
        endLabels.setRefreshingLabel("正在载入...");// 刷新时
        endLabels.setReleaseLabel("放开刷新...");// 下来达到一定距离时，显示的提示

        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                isRefresh = true;
                time = VeDate.getStringDate();
                getData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                time = list.get(list.size() - 1).getCreate_time();
                getData();
            }
        });

    }

    void getLocal() {
        try {
//            List<Inspiration> localList = dbUtil.findAll(Selector.from(Inspiration.class).orderBy("create_time", true).limit(50));
            List<Info> localList = dbUtil.findAll(Selector.from(Info.class).orderBy("create_time", true).limit(50));
            if (localList != null) {
                reload(localList);
            }
            isRefresh = true;
            getData();
            pullToRefreshListView.setRefreshing();
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    void getData() {

        MyHttp.getInstance().send(HttpRequest.HttpMethod.GET, urlUtils.getInfor(AppCreate.id, time.replaceAll(" ","%20")), new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                List<Info> infoList = CommentJson.getInfo(responseInfo.result);
                Log.e("ins_list", infoList.toString());
                if (infoList != null) {
                    reload(infoList);
                    if (list.size()<1){
                        empty.setVisibility(View.VISIBLE);
                    }
                    try {
                        dbUtil.saveOrUpdateAll(infoList);
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                pullToRefreshListView.onRefreshComplete();
                if (list.size()<1){
                    empty.setVisibility(View.VISIBLE);
                }
//                Toast.makeText(MessageShow.this, "网络异常", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void reload(List<Info> newlist) {
        if (isRefresh) {
            list.clear();
            isRefresh = false;
        }
        list.addAll(newlist);
        adapter.notifyDataSetChanged();
        pullToRefreshListView.onRefreshComplete();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            startActivity(new Intent(this, MainActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this,"ins_id"+id,Toast.LENGTH_SHORT).show();
    }
}
