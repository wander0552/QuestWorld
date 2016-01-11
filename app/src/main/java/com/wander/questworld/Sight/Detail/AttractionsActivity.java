package com.wander.questworld.Sight.Detail;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.wander.Cache.SaveJson;
import com.wander.Json.SightJson;
import com.wander.MyUtils.urlUtils;
import com.handmark.pulltorefresh.library.PullToRefreshRecyclerView;
import com.wander.model.Sight;
import com.wander.questworld.R;
import com.wander.xutils.MyHttp;

public class AttractionsActivity extends ActionBarActivity {

    private String fileName;
    private String id;
    private RecyclerView recyclerView;
    private Sight sight;
    private AttractionAdapter adapter;
    private PullToRefreshRecyclerView pullToRefreshRecyclerView;
    private LinearLayoutManager manager;
    private FrameLayout frameLayout;
    private AttractionAdapter.ViewHolder holder;
    private Toolbar myToolbar;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attractions);
        setActionBar();

        id = getIntent().getExtras().getString("id");
        fileName = "attraction" + id;
        initRecycler();

    }

    void initRecycler() {
        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        pullToRefreshRecyclerView = (PullToRefreshRecyclerView) findViewById(R.id.attr_recycler);
        pullToRefreshRecyclerView.setMode(PullToRefreshBase.Mode.DISABLED);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        sight = new Sight();
        adapter = new AttractionAdapter(this, sight);
        //获取悬停的view  类型为Tag
        holder = adapter.onCreateViewHolder(frameLayout, AttractionAdapter.TAG);
        frameLayout.addView(holder.itemView);
        holder.itemView.setVisibility(View.VISIBLE);
        recyclerView.setAdapter(adapter);
        setRefresh();
        getLocal();
    }

    void setRefresh() {
        pullToRefreshRecyclerView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<RecyclerView>() {
            @Override
            public void onRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                getData();
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            //TODO  hotel 和header 状态栏图片模糊  标题悬停
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                View child = recyclerView.getChildAt(0);

                int position0 = manager.findFirstVisibleItemPosition();
                if (position0==2){
                    myToolbar.setBackgroundColor(getResources().getColor(R.color.primary));
                }
                if (position0==1){
                    myToolbar.setBackgroundColor(Color.alpha(22));
                }
//                Log.d("attra_position", position + " child.isShown()" + child.isShown() + "activity" + child.isActivated());
                //dy表示还在向下滑动，当到达倒数第四个自动加载下一页
                //todo  悬停
                for (int i = 0; i < recyclerView.getChildCount(); i++) {
                    View view = recyclerView.getChildAt(i);
                    int position = recyclerView.getChildAdapterPosition(view);
                    if (view.getTop() > holder.itemView.getHeight()) {
                        ViewCompat.setY(holder.itemView, myToolbar.getHeight());
                        break;
                    }
                    if (adapter.getItemViewType(position) == AttractionAdapter.TAG) {
                        if (view.getTop() < myToolbar.getHeight()) {
                            ViewCompat.setY(holder.itemView, myToolbar.getHeight());
                        } else {
                            ViewCompat.setY(holder.itemView, view.getTop() - holder.itemView.getHeight()-myToolbar.getHeight());
                        }
                        break;
                    }
                }
                View view = recyclerView.getChildAt(0);
                int position = recyclerView.getChildAdapterPosition(view);
                for (int i = position; i >= 0; i--) {
                    if (adapter.getItemViewType(i) == AttractionAdapter.TAG) {
                        adapter.onBindViewHolder(holder, i);
                        break;
                    }
                }


            }
        });
    }

    void getLocal() {
        String json = SaveJson.GetJson(this, fileName);
        if (json.length() < 200) {
            getData();
        } else {
            sight = (SightJson.getSight(json));
            adapter.Mynotify(sight);
            pullToRefreshRecyclerView.onRefreshComplete();
        }
    }

    void showProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(R.style.CustomProgressDialog);
        progressDialog.setMessage("努力加载中···");
        progressDialog.show();
    }


    void getData() {
        MyHttp.getInstance().send(HttpRequest.HttpMethod.GET, urlUtils.getSight(id), new RequestCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                showProgressDialog();
            }

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                sight = SightJson.getSight(responseInfo.result);
                SaveJson.Save(getApplicationContext(), responseInfo.result, fileName);
                adapter.Mynotify(sight);
                pullToRefreshRecyclerView.onRefreshComplete();
                if (progressDialog!=null){
                    progressDialog.dismiss();
                }
//                reloadData(new_list);
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }

    void setActionBar() {
        myToolbar = (Toolbar) findViewById(R.id.alpha_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("景点");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_attractions, menu);
        return true;
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
        if (id == R.id.attr_share) {
            Toast.makeText(this, "share", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}
