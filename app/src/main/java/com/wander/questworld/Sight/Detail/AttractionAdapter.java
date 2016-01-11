package com.wander.questworld.Sight.Detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wander.MyUtils.VeDate;
import com.wander.model.Attraction_content;
import com.wander.model.Hotel;
import com.wander.model.Nearby_Attractions;
import com.wander.model.Note;
import com.wander.model.Sight;
import com.wander.model.Tags;
import com.wander.questworld.R;
import com.wander.xutils.BtUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wander on 2015/5/24.
 * time 12:34
 * Email 18955260352@163.com
 */
public class AttractionAdapter extends RecyclerView.Adapter<AttractionAdapter.ViewHolder> implements View.OnClickListener {
    private Context context;
    private Sight sight;
    private List<Object> list;

    public AttractionAdapter(Context context, Sight sight) {
        this.context = context;
        this.sight = sight;
        initList();

    }

    private Tags createTrip() {
        Tags trip_Tags = new Tags();
        trip_Tags.setName("实用贴士");
        return trip_Tags;
    }

    public void Mynotify(Sight sight) {
        this.sight = sight;
        initList();
        notifyDataSetChanged();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        LayoutInflater inflater = LayoutInflater.from(context);
        switch (viewType) {
            case HEADER_IMAGE:
                view = inflater.inflate(R.layout.attraction_item, parent, false);
                break;
            case HEADER:
                view = inflater.inflate(R.layout.attraction_item1, parent, false);
                break;
            case WEB:
                view = inflater.inflate(R.layout.attration_item2, parent, false);
                break;
            case TAG:
                view = inflater.inflate(R.layout.attration_tab, parent, false);
                break;
            case CONTENT:
                view = inflater.inflate(R.layout.attration_item3, parent, false);
                break;
            case NEARBY:
                view = inflater.inflate(R.layout.attration_item3, parent, false);
                break;
            case HOTEL:
                view = inflater.inflate(R.layout.attration_item3, parent, false);
                break;
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Object o = list.get(position);
        //为不同的布局绑定数据
        if (o instanceof Integer) {
            if ((int)o==HEADER_IMAGE){
                holder.header_name.setText(sight.getName_zh_cn());
                holder.header_name_en.setText(sight.getName_en().toUpperCase());
                BtUtils.getBitmapUtils(context).display(holder.header_image, sight.getImage_url());
            }
            if ((int)o == HEADER) {
                holder.photo_count.setText(sight.getPhotos_count());
                holder.ins_count.setText(sight.getAttractions_count());
               holder.header_summary.setText(sight.getDescription());
                holder.header_ins.setOnClickListener(this);
                holder.header_map.setOnClickListener(this);
                holder.header_photo.setOnClickListener(this);
            }
            if ((int)o == WEB) {
                holder.attr_web.setWebViewClient(new WebViewClient());
                holder.attr_web.setWebChromeClient(new WebChromeClient());
                holder.attr_web.getSettings().setJavaScriptEnabled(true);
                holder.attr_web.loadDataWithBaseURL(null, sight.getTips_html(), "text/html", "utf-8", null);
            }
            if ((int)o == HOTEL) {
                List<Hotel> hotelList = sight.getHotelList();
                if (hotelList == null) {
                    holder.content_recycler.setVisibility(View.GONE);
                } else {
                    holder.content_recycler.setVisibility(View.VISIBLE);
                    holder.create_time.setVisibility(View.GONE);
                    holder.content_des.setText("附近酒店(" + hotelList.size() + ")");
                    LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                    holder.content_recycler.setLayoutManager(manager);
                    holder.content_recycler.setAdapter(new HotelAdapter(context, hotelList));
                }
            }
            if ((int)o == NEARBY) {
                List<Nearby_Attractions> nearby_attractionsList = sight.getNearby_attractionsList();
                if (nearby_attractionsList == null) {
                    holder.content_recycler.setVisibility(View.GONE);
                } else {
                    holder.content_recycler.setVisibility(View.VISIBLE);
                    holder.create_time.setVisibility(View.GONE);
                    holder.content_des.setText("附近旅行地(" + nearby_attractionsList.size() + ")");
                    LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                    holder.content_recycler.setLayoutManager(manager);
                    holder.content_recycler.setAdapter(new NearbyAdapter(context, nearby_attractionsList));
                }
            }
        }
        if (o instanceof Tags) {
            holder.tab_name.setText(((Tags) o).getName());
        }
        if (o instanceof Attraction_content) {
            holder.create_time.setVisibility(View.VISIBLE);
            holder.content_des.setText(((Attraction_content) o).getDescription());
            String updated_at = ((Attraction_content) o).getUpdated_at();
            holder.create_time.setText(VeDate.longToString(updated_at));
            List<Note> notes = ((Attraction_content) o).getNotes();
            if (notes == null) {
                holder.content_recycler.setVisibility(View.GONE);
            } else {
                holder.create_time.setVisibility(View.VISIBLE);
                holder.content_recycler.setVisibility(View.VISIBLE);
                LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                holder.content_recycler.setLayoutManager(manager);
                holder.content_recycler.setAdapter(new NotesAdapter(context, notes));
            }

        }
    }

    //把sight分类存放到list中
    public void initList() {
        list = new ArrayList<>();
        if (sight.getName_zh_cn() != null) {
            list.add(HEADER_IMAGE);
            list.add(HEADER);
            list.add(createTrip());
            if (sight.getTips_html() != null) {
                list.add(WEB);
            }
            List<Tags> tagsList = sight.getTagsList();
            if (tagsList != null) {
                for (Tags tag : tagsList) {
                    list.add(tag);
                    List<Attraction_content> attraction_contents = tag.getAttraction_contents();
                    if (attraction_contents != null) {
                        for (Attraction_content attraction_content : attraction_contents) {
                            list.add(attraction_content);
                        }
                    }
                }
            }
            if (sight.getNearby_attractionsList() != null) {
                list.add(NEARBY);
            }
            if (sight.getHotelList() != null) {
                list.add(HOTEL);
            }
        }
    }

    public static final  int HEADER_IMAGE=6;
    public static final int HEADER = 0;
    public static final int TAG = 1;
    public static final int WEB = 2;
    public static final int CONTENT = 3;
    public static final int NEARBY = 4;
    public static final int HOTEL = 5;

    @Override
    public int getItemViewType(int position) {
        Object o = list.get(position);
        if (o instanceof Integer) {
            return (Integer) o;
        }
        if (o instanceof Tags) {
            return TAG;
        }
        if (o instanceof Attraction_content) {
            return CONTENT;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onClick(View v) {
        Bundle bundle=new Bundle();
        bundle.putString("id", sight.getId());
        switch (v.getId()) {
            case R.id.header_ins:
                Toast.makeText(context,"header_ins",Toast.LENGTH_SHORT).show();
                break;
            case R.id.header_map:
                Intent intent=new Intent(context, NearbyMapActivity.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
                break;
            case R.id.header_photo:
                bundle.putString("sight_name",sight.getName_zh_cn());
                Intent intent1=new Intent(context,Att_Photos.class);
                intent1.putExtras(bundle);
                context.startActivity(intent1);
                break;

        }
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView header_image;
        TextView header_name;
        TextView header_name_en;
        TextView photo_count;
        TextView ins_count;
        TextView header_summary;
        LinearLayout header_photo;
        LinearLayout header_ins;
        LinearLayout header_map;

        WebView attr_web;

        TextView content_des;
        RecyclerView content_recycler;
        TextView create_time;

        TextView tab_name;


        public ViewHolder(View itemView) {
            super(itemView);
            //获取header的控件
            header_image = (ImageView) itemView.findViewById(R.id.attr1_image);
            header_name = (TextView) itemView.findViewById(R.id.attr_name);
            header_name_en = (TextView) itemView.findViewById(R.id.attr_name_en);
            photo_count = (TextView) itemView.findViewById(R.id.attr_photo_count);
            ins_count = (TextView) itemView.findViewById(R.id.attr_ins_count);
            header_map = (LinearLayout) itemView.findViewById(R.id.header_map);
            header_photo = (LinearLayout) itemView.findViewById(R.id.header_photo);
            header_ins = (LinearLayout) itemView.findViewById(R.id.header_ins);
            header_summary= (TextView) itemView.findViewById(R.id.attr_summary);

            //获取实用贴士的控件
            attr_web = (WebView) itemView.findViewById(R.id.attr_web);
            //获取content的控件
            content_des = (TextView) itemView.findViewById(R.id.attr_content_dis);
            content_recycler = (RecyclerView) itemView.findViewById(R.id.attr_recycler);
            create_time = (TextView) itemView.findViewById(R.id.attr_content_create_time);

            //获取tab——name中的控件
            tab_name = (TextView) itemView.findViewById(R.id.tab_name);
        }
    }
}
