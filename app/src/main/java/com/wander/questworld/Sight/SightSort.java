package com.wander.questworld.Sight;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.wander.Cache.SaveJson;
import com.wander.Json.SightJson;
import com.wander.MyAdapter.PocketAdapter;
import com.wander.MyUtils.urlUtils;
import com.handmark.pulltorefresh.library.PullToRefreshRecyclerView;
import com.wander.model.Destination;
import com.wander.questworld.R;
import com.wander.xutils.MyHttp;

import java.util.ArrayList;
import java.util.List;

public class SightSort extends ActionBarActivity {
    private PullToRefreshRecyclerView pullToRefreshRecyclerView;
    private RecyclerView recyclerView;
    private String id;
    private String name;
    private PocketAdapter adapter;
    private List<Destination> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sight_sort);
        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("id");
        name = bundle.getString("name");
        setActionBar();

        pullToRefreshRecyclerView = (PullToRefreshRecyclerView) findViewById(R.id.des_recycler);
        pullToRefreshRecyclerView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(manager);
        list = new ArrayList<>();
        adapter = new PocketAdapter(this, list);
        recyclerView.setAdapter(adapter);
        getLocalData();
        pullToRefreshRecyclerView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<RecyclerView>() {
            @Override
            public void onRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                getData();
            }
        });

    }

    void setActionBar() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        myToolbar.setLogo(R.drawable.logo16);
        setTitle(name+"口袋书");
    }

    private void getLocalData() {
        String json = SaveJson.GetJson(this, name);
        if (json.length() < 200) {
            getData();
        } else {
            reloadData(SightJson.getPocket(json));
        }
    }

    void getData() {
        MyHttp.getInstance().send(HttpRequest.HttpMethod.GET, urlUtils.getPocket(id), new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                List<Destination> destinationList = SightJson.getPocket(responseInfo.result);
                reloadData(destinationList);
                SaveJson.Save(SightSort.this, responseInfo.result, name);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(SightSort.this, "网络连接异常", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void reloadData(List<Destination> new_list) {
        list.clear();
        list.addAll(new_list);
        adapter.notifyDataSetChanged();
        pullToRefreshRecyclerView.onRefreshComplete();
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
}
