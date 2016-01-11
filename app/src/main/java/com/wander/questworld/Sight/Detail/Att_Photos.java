package com.wander.questworld.Sight.Detail;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.wander.Cache.SaveJson;
import com.wander.Json.SightJson;
import com.wander.MyAdapter.PhotosAdapter;
import com.wander.MyUtils.urlUtils;
import com.handmark.pulltorefresh.library.PullToRefreshRecyclerView;
import com.wander.model.AttractionPhoto;
import com.wander.questworld.R;
import com.wander.xutils.MyHttp;

import java.util.ArrayList;
import java.util.List;

public class Att_Photos extends ActionBarActivity implements PhotosAdapter.onItemClickListener {

    private int pageNum = 1;
    private RecyclerView recyclerView;
    private PullToRefreshRecyclerView pullToRefreshRecyclerView;
    private String id;
    private ArrayList<AttractionPhoto> list;
    private StaggeredGridLayoutManager manager;
    private String filename;
    private PhotosAdapter adapter;
    private String sight_name;
    private ProgressDialog progressDialog;
    private ProgressBar photos_loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_att__photos);
        Bundle extras = getIntent().getExtras();
        id = extras.getString("id");
        sight_name = extras.getString("sight_name");
        filename = "Photos" + id;
        photos_loading = (ProgressBar) findViewById(R.id.photos_loading);
        setActionBar();
        setRecycler();
    }

    void showProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(R.style.CustomProgressDialog);
        progressDialog.setMessage("努力加载中···");
        progressDialog.show();
    }

    void setActionBar() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        myToolbar.setLogo(R.drawable.logo16);
        setTitle(sight_name);
    }

    void setRecycler() {
        pullToRefreshRecyclerView = (PullToRefreshRecyclerView) findViewById(R.id.photos_recycler);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        list = new ArrayList<>();
        adapter = new PhotosAdapter(list, this);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        setRefresh();
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
                int[] position = manager.findLastVisibleItemPositions(new int[2]);
                int itemCount = manager.getItemCount();
                //dy表示还在向下滑动，当到达倒数第四个自动加载下一页
                if (position[0] >= itemCount - 4 && dy > 0) {
                    pageNum++;
                    getData();
                }
            }
        });
    }

    private void getLocalData() {
        String json = SaveJson.GetJson(this, filename);
        if (json.length() < 200) {
            getData();
        } else {
            reloadData(SightJson.getPhotos(json));
        }
    }

    void getData() {
        MyHttp.getInstance().send(HttpRequest.HttpMethod.GET, urlUtils.getAttPhoto(id, pageNum), new RequestCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                if (pageNum == 1) {
                    showProgressDialog();
                } else {
                    photos_loading.setVisibility(View.VISIBLE);
                    photos_loading.setMax(100);
                }
            }

            @Override
            public void onLoading(long total, long current, boolean isUploading) {
                super.onLoading(total, current, isUploading);
                if (pageNum == 1) {
                    progressDialog.setProgress((int) (100 * current / total));
                }
                else {
                    photos_loading.setProgress((int) (100 * current / total));
                }
            }

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                List<AttractionPhoto> photos = SightJson.getPhotos(responseInfo.result);
                reloadData(photos);
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                if (pageNum == 1) {
                    SaveJson.Save(Att_Photos.this, responseInfo.result, filename);
                }
                if (photos_loading.isShown()){
                    photos_loading.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
//                Toast.makeText(PlaceActivity.this, "网络连接异常", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void reloadData(List<AttractionPhoto> new_list) {
        if (pageNum == 1) {
            list.clear();
        }
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

    @Override
    public void onItemClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putParcelableArrayList("list", list);
        Intent intent = new Intent(this, PhotosShow.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
