package com.wander.MyAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.wander.model.Place;
import com.wander.questworld.R;
import com.wander.questworld.Sight.Detail.AttractionsActivity;
import com.wander.xutils.BtUtils;

import java.util.List;

/**
 * Created by wander on 2015/5/23.
 * time 17:40
 * Email 18955260352@163.com
 */
public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder> {
    private Context context;
    private List<Place> list;


    public PlaceAdapter(Context context, List<Place> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public PlaceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.place_item, parent, false);
//        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlaceAdapter.ViewHolder holder, int position) {
        final Place place = list.get(position);
        holder.name.setText(place.getName());
        holder.count.setText(place.getAttraction_trips_count() + "篇游记");
        String user_score = place.getUser_score();
        try {
            holder.sore.setRating(Float.parseFloat(user_score));
        } catch (NumberFormatException e) {
            holder.sore.setRating(0);
        }
        holder.content.setText(place.getDescription());
        Animation animation = new AlphaAnimation(0, 1);
        animation.setDuration(300);
        holder.image.setAnimation(animation);
        BtUtils.getBitmapUtils(context).display(holder.image, place.getImage_url());
        holder.item_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("id", place.getId());
                Intent intent = new Intent(context, AttractionsActivity.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView name;
        private RatingBar sore;
        private TextView content;
        private TextView count;
        private LinearLayout item_click;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.place_image);
            name = (TextView) itemView.findViewById(R.id.place_name);
            sore = (RatingBar) itemView.findViewById(R.id.place_sore);
            content = (TextView) itemView.findViewById(R.id.place_content);
            count = (TextView) itemView.findViewById(R.id.place_count);
            item_click = (LinearLayout) itemView.findViewById(R.id.place_click);
        }
    }

}
