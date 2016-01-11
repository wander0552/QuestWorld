package com.wander.questworld.Sight;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.wander.Cache.SaveJson;
import com.wander.Json.SightJson;
import com.wander.MyAdapter.PlaceAdapter;
import com.wander.MyUtils.urlUtils;
import com.handmark.pulltorefresh.library.PullToRefreshRecyclerView;
import com.wander.model.Place;
import com.wander.questworld.R;
import com.wander.xutils.MyHttp;

import java.util.ArrayList;
import java.util.List;

public class PlaceActivity extends ActionBarActivity {

    private PullToRefreshRecyclerView pullToRefreshRecyclerView;
    private RecyclerView recyclerView;
    private String id;
    private String name;
    private PlaceAdapter adapter;
    private List<Place> list;
    private int pageNum = 1;
    private LinearLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);
        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("id");
        name = bundle.getString("name") + "旅行地";
        setActionBar();

        pullToRefreshRecyclerView = (PullToRefreshRecyclerView) findViewById(R.id.place_recycler);
//        pullToRefreshRecyclerView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        manager = new LinearLayoutManager(this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(manager);
        setRefresh();
        list = new ArrayList<>();
        adapter = new PlaceAdapter(this, list);
        recyclerView.setAdapter(adapter);
        getLocalData();
    }

    void setRefresh() {
        pullToRefreshRecyclerView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<RecyclerView>() {
            @Override
            public void onRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                pageNum = 1;
                getData();
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int position = manager.findLastVisibleItemPosition();
                int itemCount = manager.getItemCount();
                //dy表示还在向下滑动，当到达倒数第四个自动加载下一页
                if (position >= itemCount - 1 && dy > 50) {
                    pageNum++;
                    getData();
                }
            }
        });
    }


    void setActionBar() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        myToolbar.setLogo(R.drawable.logo16);
        setTitle(name);
    }


    private void getLocalData() {
        String json = SaveJson.GetJson(this, name);
        if (json.length() < 200) {
            getData();
        } else {
            reloadData(SightJson.getPlace(json));
        }
    }

    void getData() {
        MyHttp.getInstance().send(HttpRequest.HttpMethod.GET, urlUtils.getPlace(id, pageNum), new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                List<Place> places = SightJson.getPlace(responseInfo.result);
                reloadData(places);
                if (pageNum == 1) {
                    SaveJson.Save(PlaceActivity.this, responseInfo.result, name);
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
//                Toast.makeText(PlaceActivity.this, "网络连接异常", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void reloadData(List<Place> new_list) {
        if (pageNum == 1) {
            list.clear();
        }
        list.addAll(new_list);
        adapter.notifyDataSetChanged();
        pullToRefreshRecyclerView.onRefreshComplete();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_place, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id2 = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id2 == android.R.id.home) {
            finish();
        }
        if (id2 == R.id.place_map) {
            Bundle bundle = new Bundle();
            bundle.putString("id", id);
            Intent intent=new Intent(this,MapActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
