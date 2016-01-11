package com.wander.questworld.Sight.Detail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wander.model.Hotel;
import com.wander.model.Nearby_Attractions;
import com.wander.questworld.R;
import com.wander.xutils.BtUtils;

import java.util.List;

/**
 * Created by wander on 2015/5/24.
 * time 17:22
 * Email 18955260352@163.com
 */
public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.ViewHolder>{
    private Context context;
    private List<Hotel> list;

    public HotelAdapter(Context context, List<Hotel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.attration_nearby, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Hotel o = list.get(position);
            holder.nearby_name.setText(o.getName_zh_cn());
            holder.distance.setText(o.getDistance().substring(0,3)+"KM");
            BtUtils.getBitmapUtils(context).display(holder.image, o.getImage_url());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView nearby_name;
        TextView distance;
        ImageView image;
        public ViewHolder(View itemView) {
            super(itemView);
            nearby_name= (TextView) itemView.findViewById(R.id.nearby_name);
            distance= (TextView) itemView.findViewById(R.id.nearby_distance);
            image= (ImageView) itemView.findViewById(R.id.nearby_image);
        }
    }
}
