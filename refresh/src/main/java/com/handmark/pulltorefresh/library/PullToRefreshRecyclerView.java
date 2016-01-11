package com.handmark.pulltorefresh.library;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;


/**
 * Created by wander
 * Date: 15-5-19
 * Time: 上午10:29
 */
public class PullToRefreshRecyclerView extends PullToRefreshBase<RecyclerView> {
    public PullToRefreshRecyclerView(Context context) {
        super(context);
    }

    public PullToRefreshRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullToRefreshRecyclerView(Context context, Mode mode) {
        super(context, mode);
    }

    public PullToRefreshRecyclerView(Context context, Mode mode, AnimationStyle animStyle) {
        super(context, mode, animStyle);
    }

    /**
     * 定义刷新方向
     * @return
     */
    @Override
    public Orientation getPullToRefreshScrollDirection() {
        return Orientation.VERTICAL;
    }

    /**
     * 创建一个被刷新的Ｖiew
     * @param context Context to create view with
     * @param attrs AttributeSet from wrapped class. Means that anything you
     *            include in the XML layout declaration will be routed to the
     *            created View
     * @return
     */
    @Override
    protected RecyclerView createRefreshableView(Context context, AttributeSet attrs) {
        RecyclerView view = new RecyclerView(context, attrs);
        view.setId(R.id.recycler);
        return view;
    }

    /**
     * 是否到达底部
     * @return
     */
    @Override
    protected boolean isReadyForPullEnd() {
        RecyclerView recyclerView = getRefreshableView();
        View view = recyclerView.getChildAt(recyclerView.getChildCount() - 1);
        int position = recyclerView.getChildAdapterPosition(view);
        int count = recyclerView.getAdapter().getItemCount();
        return position == count - 1 && view.getBottom() == recyclerView.getHeight() - recyclerView.getPaddingBottom();
    }

    /**
     * 是否到达顶部
     * @return
     */
    @Override
    protected boolean isReadyForPullStart() {
        RecyclerView recyclerView = getRefreshableView();
        //获得RecyclerView正展示的第一个view
        View view = recyclerView.getChildAt(0);
        int position = recyclerView.getChildAdapterPosition(view);

        return position == 0 && view.getTop() == recyclerView.getPaddingTop();
    }

    public void setAdapter(RecyclerView.Adapter adapter){
        getRefreshableView().setAdapter(adapter);
    }
}
