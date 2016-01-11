package com.wander.MyAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.wander.model.Destination;
import com.wander.model.Destinations;
import com.wander.questworld.R;
import com.wander.xutils.BtUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by wander on 2015/5/18.
 * Email 18955260352@163.com
 */
public class SightAdapter extends RecyclerView.Adapter<SightAdapter.ViewHolder> implements View.OnClickListener {
    private Context context;
    private List<Destinations> list;

    private onItemClickListener listener;
    private RecyclerView recycler;
    private List<Object> totalList;
    private final String[] sort;

    public SightAdapter(Context context, List<Destinations> list) {
        this.context = context;
        this.list = list;
        sort = context.getResources().getStringArray(R.array.sort);
        totalList = new ArrayList<>();
        initTotalList();
    }

    public void Mynotify() {
        initTotalList();
        notifyDataSetChanged();
    }

    private void initTotalList() {
        totalList.clear();
        for (int i = 0; i < list.size(); i++) {
            totalList.add(sort[i]);
            for (Destination destination : list.get(i).getDistinations()) {
                totalList.add(destination);
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case TITLE:
                view = LayoutInflater.from(context).inflate(R.layout.sort_title, parent, false);
                break;
            case ITEM:
                view = LayoutInflater.from(context).inflate(R.layout.sight_fragment_item, parent, false);
                view.setOnClickListener(this);
                break;
            case FOOT:
                view = LayoutInflater.from(context).inflate(R.layout.listfoot, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position < totalList.size()) {
            Object o = totalList.get(position);
            if (o instanceof String) {
                holder.title.setText((String) o);
            }
            if (o instanceof Destination) {
                holder.country.setText(((Destination) o).getName_zh_cn());
                holder.en_country.setText(((Destination) o).getName_en().toUpperCase());
                holder.count.setText(((Destination) o).getPoi_count() + "旅行地");
                Animation animation = new AlphaAnimation(0, 1);
                animation.setDuration(300);
                holder.image.setAnimation(animation);
                BtUtils.getBitmapUtils(context).display(holder.image, ((Destination) o).getImage_url());
            }
        }
        if (position == totalList.size()) {
            holder.more.setText("更多旅行口袋书锐意制作中···");
        }
    }


    @Override
    public int getItemCount() {
        return totalList.size() + 1;
    }

    public static final int TITLE = 0;
    public static final int ITEM = 1;
    public static final int FOOT = 2;

    @Override
    public int getItemViewType(int position) {
        if (position == totalList.size()) {
            return FOOT;
        } else {
            Object o = totalList.get(position);
            if (o instanceof String) {
                return TITLE;
            }
            if (o instanceof Destination) {
                return ITEM;
            }
        }
        return super.getItemViewType(position);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        recycler = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        recycler = null;
    }

    @Override
    public void onClick(View v) {
        int position = recycler.getChildAdapterPosition(v);
        Destination destination = (Destination) (totalList.get(position));
        listener.setOnItemClick(destination.getId(), destination.getName_zh_cn());
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }


    public interface onItemClickListener {
        void setOnItemClick(String id, String name);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        //item布局
        TextView country;
        TextView en_country;
        TextView count;
        ImageView image;
        //sort title
        TextView title;
        TextView more;

        public ViewHolder(View itemView) {
            super(itemView);
            count = (TextView) itemView.findViewById(R.id.sort_count);
            country = (TextView) itemView.findViewById(R.id.country);
            image = (ImageView) itemView.findViewById(R.id.sort_image);
            en_country = (TextView) itemView.findViewById(R.id.english_country);

            title = (TextView) itemView.findViewById(R.id.sort_title);
            more = (TextView) itemView.findViewById(R.id.more);
        }
    }
}
