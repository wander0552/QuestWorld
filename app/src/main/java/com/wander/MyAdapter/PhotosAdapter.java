package com.wander.MyAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wander.MyView.WaterView;
import com.wander.model.AttractionPhoto;
import com.wander.questworld.R;
import com.wander.xutils.BtUtils;

import java.util.List;

/**
 * Created by wander on 2015/5/25.
 * Email 18955260352@163.com
 */
public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.ViewHolder> implements View.OnClickListener {

    private List<AttractionPhoto> list;
    private Context context;
    private RecyclerView recycler;
    private onItemClickListener onItemClickListener;

    public PhotosAdapter(List<AttractionPhoto> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.photo_item, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AttractionPhoto photo = list.get(position);
//        holder.imageView.setLayoutParams(new FrameLayout.LayoutParams(DisplayUtils.px2dip(context, photo.getImage_width()),DisplayUtils.px2dip(context,photo.getImage_height())));
        BtUtils.getBitmapUtils(context).display(holder.imageView, photo.getImage_url());
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        recycler = recyclerView;
    }

    @Override
    public void onClick(View v) {
        if (onItemClickListener != null && recycler != null) {
            RecyclerView.ItemAnimator itemAnimator = recycler.getItemAnimator();
            if (itemAnimator.isRunning()){
                return;
            }
            int position = recycler.getChildAdapterPosition(v);
            onItemClickListener.onItemClick(position);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private WaterView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (WaterView) itemView.findViewById(R.id.photo_item);
        }
    }

    public interface onItemClickListener {
        void onItemClick(int position);
    }
}
