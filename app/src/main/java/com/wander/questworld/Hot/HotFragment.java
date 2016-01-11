package com.wander.questworld.Hot;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
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
import com.wander.Json.InsJson;
import com.wander.MyAdapter.HotInsAdapter;
import com.wander.MyUtils.VeDate;
import com.wander.MyUtils.urlUtils;
import com.wander.model.Inspiration;
import com.wander.questworld.AppCreate;
import com.wander.questworld.R;
import com.wander.xutils.MyDB;
import com.wander.xutils.MyHttp;

import java.util.ArrayList;
import java.util.List;

public class HotFragment extends Fragment {

    private HotInsAdapter adapter;
    private List<Inspiration> list;
    private String time;
    private boolean isRefresh = true;
    private PullToRefreshListView pullToRefreshListView;
    private DbUtils dbUtil;
    private int index;

    private static final String ARG_PARAM1 = "param1";


    public static HotFragment newInstance(int param) {
        HotFragment fragment = new HotFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_PARAM1, param);
        fragment.setArguments(bundle);
        return fragment;
    }

    public HotFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_PARAM1);
            Log.e("index", index + "");
        }
        time = VeDate.getStringDate();

        dbUtil = MyDB.getDbUtil();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hot, container, false);
        pullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
        list = new ArrayList<>();
        adapter = new HotInsAdapter(list, getActivity().getApplicationContext(),pullToRefreshListView);
        pullToRefreshListView.setAdapter(adapter);
        getLocal();
        setRefresh();
        return view;
    }

    void setRefresh() {
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
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
            List<Inspiration> localList = dbUtil.findAll(Selector.from(Inspiration.class).orderBy("create_time", true).limit(50));
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
        RequestParams params = new RequestParams();
        params.addBodyParameter("create_time", time);
        params.addBodyParameter("user_id", AppCreate.id);
        String url = "";
        if (index == 0) {
            url = urlUtils.HOT_INS;
        }
        if (index == 1) {
            url = urlUtils.ATT_INS;
        }

        MyHttp.getInstance().send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                List<Inspiration> inspirations = InsJson.getHot(responseInfo.result);
                Log.e("ins_list", inspirations.toString());
                if (inspirations != null) {
                    reload(inspirations);
                    try {
                        dbUtil.saveOrUpdateAll(inspirations);
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                pullToRefreshListView.onRefreshComplete();
                Toast.makeText(getActivity().getApplicationContext(), "网络异常", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void reload(List<Inspiration> newlist) {
        if (isRefresh) {
            list.clear();
            isRefresh = false;
        }
        list.addAll(newlist);
        adapter.notifyDataSetChanged();
        pullToRefreshListView.onRefreshComplete();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
