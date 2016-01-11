package com.wander.questworld.Sight;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.wander.Cache.SaveJson;
import com.wander.Json.SightJson;
import com.wander.MyAdapter.SightAdapter;
import com.wander.MyUtils.urlUtils;
import com.handmark.pulltorefresh.library.PullToRefreshRecyclerView;
import com.wander.model.Destinations;
import com.wander.questworld.R;
import com.wander.xutils.MyHttp;

import java.util.ArrayList;
import java.util.List;

public class SightFragment extends Fragment implements SightAdapter.onItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private PullToRefreshRecyclerView pullToRefreshRecyclerView;
    private RecyclerView recyclerView;
    private SightAdapter adapter;
    private List<Destinations> list;
    private String fileName = "Sort.txt";
    private GridLayoutManager manager;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SightFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SightFragment newInstance(String param1, String param2) {
        SightFragment fragment = new SightFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public SightFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sight, container, false);
        pullToRefreshRecyclerView = (PullToRefreshRecyclerView) view.findViewById(R.id.sight_recyclerView);
        pullToRefreshRecyclerView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        //设置recyclerView的格式
        manager = new GridLayoutManager(getActivity().getApplicationContext(), 2);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            /**
             * 设置title占2列
             */
            public int getSpanSize(int position) {
                if (adapter.getItemViewType(position) == SightAdapter.ITEM) {
                    return 1;
                }
                return 2;
            }
        });
        recyclerView.setLayoutManager(manager);
        //设置数据源
        list = new ArrayList<>();
        adapter = new SightAdapter(getActivity().getApplicationContext(), list);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
        setRefresh();
        getLocalData();
        return view;
    }

    void setRefresh() {
/*        //todo delete
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int position = manager.findLastVisibleItemPosition();
                int itemCount = manager.getItemCount();
                //dy表示还在向下滑动，当到达倒数第四个自动加载下一页
                if (position >= itemCount - 4 && dy > 0) {
                }
            }
        });*/
        pullToRefreshRecyclerView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<RecyclerView>() {
            @Override
            public void onRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                getData();
            }
        });
    }

    private void getLocalData() {
        String json = SaveJson.GetJson(getActivity().getApplicationContext(), fileName);
        if (json.length() < 200) {
            getData();
        } else {
            reloadData(SightJson.getDestinations(json));
        }
    }

    @Override
    public void setOnItemClick(String id,String name) {
        Log.d("sight", id);
        Bundle bundle=new Bundle();
        bundle.putString("id",id);
        bundle.putString("name",name);
        Intent intent = new Intent(getActivity(), SightSort.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }


    void getData() {
        MyHttp.getInstance().send(HttpRequest.HttpMethod.GET, urlUtils.DESTINATIONS, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                List<Destinations> new_list = SightJson.getDestinations(responseInfo.result);
                SaveJson.Save(getActivity().getApplicationContext(), responseInfo.result, fileName);
                reloadData(new_list);
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }

    void reloadData(List<Destinations> new_list) {
        list.clear();
        list.addAll(new_list);
        adapter.Mynotify();
        pullToRefreshRecyclerView.onRefreshComplete();
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
