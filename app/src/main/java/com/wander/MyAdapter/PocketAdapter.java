package com.wander.MyAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wander.model.Destination;
import com.wander.model.Place;
import com.wander.questworld.R;
import com.wander.questworld.Sight.PlaceActivity;
import com.wander.xutils.BtUtils;


import java.net.ContentHandler;
import java.util.List;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by wander on 2015/5/23.
 * time 13:03
 * Email 18955260352@163.com
 */
public class PocketAdapter extends RecyclerView.Adapter<PocketAdapter.ViewHolder> {
    private Context context;
    private List<Destination> list;
    private int pos;

    public PocketAdapter(Context context, List<Destination> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pocket_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Destination destination = list.get(position);
        holder.Des.setText(destination.getName_zh_cn());
        holder.en_Des.setText(destination.getName_en());
        BtUtils.getBitmapUtils(context).display(holder.image,destination.getImage_url());

        //添加点击事件
        holder.trip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        holder.travel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                Destination destination = list.get(position);
                bundle.putString("id", destination.getId());
                bundle.putString("name",destination.getName_zh_cn());
                Intent intent=new Intent(context, PlaceActivity.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        holder.ins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("onclick", "ins");
            }
        });
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShare();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void showShare() {
        ShareSDK.initSDK(context);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(context.getString(R.string.share));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://www.bbyjpc.com");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("众行天下是一款旅游分享的软件，你可以随手记录身边的美景与他人分享，在这里能看到世界各地不同的景点的内容分享，在你还在计划去哪旅游的时候就能提前了解景点的特色。应用还提供了世界各地景点的介绍以及地图查看，方便出行。");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        //设置网上加载图片的路径
        oks.setImageUrl("http://wander0515.vicp.cc:54121/ZXTXserver/user_header/default.png");
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(context.getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("www.bbyjpc.com");

        // 启动分享GUI
        oks.show(context);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        //item布局
        TextView Des;
        TextView en_Des;
        ImageView image;

        LinearLayout trip;
        LinearLayout travel;
        LinearLayout share;
        LinearLayout ins;

        public ViewHolder(View itemView) {
            super(itemView);
            Des= (TextView) itemView.findViewById(R.id.pocket_name);
            en_Des= (TextView) itemView.findViewById(R.id.pocket_name_en);
            image= (ImageView) itemView.findViewById(R.id.pocket_image);

            trip= (LinearLayout) itemView.findViewById(R.id.pocket_trip);
            travel= (LinearLayout) itemView.findViewById(R.id.pocket_travel);
            share= (LinearLayout) itemView.findViewById(R.id.pocket_shear);
            ins= (LinearLayout) itemView.findViewById(R.id.pocket_ins);
        }
    }
}
