package com.wander.MyAdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.wander.MyUtils.DisplayUtils;
import com.wander.MyUtils.JsonUtils;
import com.wander.MyUtils.SystemInfo;
import com.wander.MyUtils.urlUtils;
import com.wander.model.Inspiration;
import com.wander.questworld.AppCreate;
import com.wander.questworld.Comment.Comment;
import com.wander.questworld.InsPhotoActivity;
import com.wander.questworld.R;
import com.wander.xutils.BtUtils;
import com.wander.xutils.MyDB;
import com.wander.xutils.MyHttp;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wander on 2015/5/31.
 * time 14:00
 * Email 18955260352@163.com
 * 灵感listview适配器，以及点击事件
 */
public class HotInsAdapter extends BaseAdapter implements InsTagAdapter.OnItemClickListener {
    private final HttpUtils httpUtils;
    private final DbUtils dbUtil;
    private LayoutInflater inflater;
    private List<Inspiration> list;
    private ListView mListView;
    private Context context;
    ViewHolder holder;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ViewHolder holder= (ViewHolder) msg.obj;
            if (msg.what==1){
                Log.e("message",holder.toString());
                if (msg.arg1==1){
                    holder.ins_good.setImageResource(R.drawable.dashboard_recommand_on_default);
                }else {
                    holder.ins_good.setImageResource(R.drawable.dashboard_recommand_off_default);
                }
                holder.ins_good_count.setText(msg.arg2+"");
            }
        }
    };

    public HotInsAdapter(List<Inspiration> list, Context context,PullToRefreshListView listView) {
        this.list = list;
        this.context = context;
        this.mListView=listView.getRefreshableView();
        httpUtils = MyHttp.getInstance();
        dbUtil = MyDB.getDbUtil();
        if (context != null) {
            inflater = LayoutInflater.from(context);
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void removeItem(int position) {
        list.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View ret = null;
        if (convertView != null) {
            ret = convertView;
        } else {
            ret = inflater.inflate(R.layout.ins_item_layout, parent, false);
        }
        holder = (ViewHolder) ret.getTag();
        if (holder == null) {
            holder = new ViewHolder();
            holder.header = (ImageView) ret.findViewById(R.id.ins_header);
            holder.username = (TextView) ret.findViewById(R.id.ins_username);
            holder.create_time = (TextView) ret.findViewById(R.id.ins_create_time);
            holder.attention = (ImageView) ret.findViewById(R.id.ins_attention);
            holder.delete = (ImageView) ret.findViewById(R.id.delete_ins);
            holder.ins_image = (ImageView) ret.findViewById(R.id.ins_image);
            holder.ins_image_count = (TextView) ret.findViewById(R.id.ins_image_count);
            holder.content = (TextView) ret.findViewById(R.id.ins_content);
            holder.tags_recycler = (RecyclerView) ret.findViewById(R.id.ins_tags);
            holder.ins_address_layout = (LinearLayout) ret.findViewById(R.id.ins_address_layout);
            holder.ins_address = (TextView) ret.findViewById(R.id.ins_address);
            holder.ins_good = (ImageView) ret.findViewById(R.id.ins_good);
            holder.ins_good_count = (TextView) ret.findViewById(R.id.ins_good_count);
            holder.ins_comment = (ImageView) ret.findViewById(R.id.ins_comment);
            holder.ins_comment_count = (TextView) ret.findViewById(R.id.ins_comment_count);
            holder.ins_like = (ImageView) ret.findViewById(R.id.ins_like);
            holder.tag_layout = (LinearLayout) ret.findViewById(R.id.ins_tag_layout);
            ret.setTag(holder);
        }

        final Inspiration inspiration = list.get(position);
        final String ins_id = inspiration.getId();
        if (inspiration.getHeader() != null) {
            //画圆形头像*************************************************
            BtUtils.getIconBitmapUtils(context).display(holder.header, inspiration.getHeader());
        }
        holder.header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"userclick"+inspiration.getUser_id(),Toast.LENGTH_SHORT).show();
            }
        });

        holder.username.setText(inspiration.getUsername());
        holder.create_time.setText(inspiration.getCreate_time());
        //设置删除和关注，当前用户自己的ins则可删除++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        if (inspiration.getUser_id().equals(AppCreate.id)) {
            holder.delete.setVisibility(View.VISIBLE);
            holder.attention.setVisibility(View.GONE);
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    httpUtils.send(HttpRequest.HttpMethod.GET, urlUtils.DELETE_INS + inspiration.getId(), new RequestCallBack<String>() {
                        @Override
                        public void onSuccess(ResponseInfo<String> responseInfo) {
                            try {
                                JSONObject object = new JSONObject(responseInfo.result);
                                if (object.getInt("code") == 200) {
                                    removeItem(position);
                                    try {
                                        dbUtil.delete(inspiration);
                                    } catch (DbException e) {
                                        e.printStackTrace();
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(HttpException e, String s) {

                        }
                    });
                }
            });
        } else {
            ///关注用户---------------------------------------------------------------
            holder.delete.setVisibility(View.GONE);
            String attention = inspiration.getAttention();
            if (attention.equals("0")) {
                holder.attention.setVisibility(View.VISIBLE);
                if (SystemInfo.sharedPreferences.getBoolean("login", false)) {
                    holder.attention.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            httpUtils.send(HttpRequest.HttpMethod.GET, urlUtils.getAttention(AppCreate.id, inspiration.getUser_id()), new RequestCallBack<String>() {
                                @Override
                                public void onSuccess(ResponseInfo<String> responseInfo) {
                                    if (JsonUtils.checkSuccess(responseInfo.result)) {
//                                        holder.attention.setVisibility(View.GONE);
                                        inspiration.setAttention("1");
                                        notifyDataSetInvalidated();
                                        try {
                                            dbUtil.saveOrUpdate(inspiration);
                                        } catch (DbException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(HttpException e, String s) {

                                }
                            });
                        }
                    });
                }
                else {
                    holder.attention.setVisibility(View.GONE);
                }
            } else {
                holder.attention.setVisibility(View.GONE);
            }
        }
        //图片显示//////////////////////////////////////////////
         final String pic_list = inspiration.getPic_list();
        if (pic_list.length() > 0) {
            holder.ins_image.setVisibility(View.VISIBLE);
            List<String> pics = Arrays.asList(pic_list.split(","));
            if (pics.size()>1) {
                holder.ins_image_count.setVisibility(View.VISIBLE);
                holder.ins_image_count.setText("" + pics.size());
            }
            else {
                holder.ins_image_count.setVisibility(View.GONE);
            }
            BtUtils.getBitmapUtils(context).display(holder.ins_image, pics.get(0));
            holder.ins_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("pic", pic_list);
                    Intent intent = new Intent(context, InsPhotoActivity.class);
                    intent.putExtras(bundle);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        } else {
            holder.ins_image.setVisibility(View.GONE);
            holder.ins_image_count.setVisibility(View.GONE);
        }
        final String content = inspiration.getContent();
        if (content.length() > 0) {
            holder.content.setVisibility(View.VISIBLE);
            holder.content.setText(content);
        } else {
            holder.content.setVisibility(View.GONE);
        }
        String tag = inspiration.getTag();
        if (tag.length() > 0) {
            holder.tag_layout.setVisibility(View.VISIBLE);
            List<String> tags = Arrays.asList(tag.split(","));
            Log.d("ins_tag", tags.toString());
            LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            holder.tags_recycler.setLayoutManager(manager);
            InsTagAdapter adapter = new InsTagAdapter(context, tags);
            adapter.setOnItemClickListener(this);
            holder.tags_recycler.setAdapter(adapter);
        } else {
            holder.tag_layout.setVisibility(View.GONE);
        }
        //Todo  地理未知
        if (inspiration.getAddress().equals("未知地区")) {
            holder.ins_address_layout.setVisibility(View.GONE);
        } else {
            holder.ins_address_layout.setVisibility(View.VISIBLE);
        }
        //设置点赞事件--------------------------------------------------------------------------
        holder.ins_good_count.setText(inspiration.getHot_count());
        if (inspiration.getHot().equals("0")) {
            holder.ins_good.setImageResource(R.drawable.dashboard_recommand_off_default);
        } else {
            holder.ins_good.setImageResource(R.drawable.dashboard_recommand_on_default);
        }
        holder.ins_good.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (SystemInfo.sharedPreferences.getBoolean("login", false)) {
                    String hot = inspiration.getHot();
                    final String hot_count = inspiration.getHot_count();
                    if (hot.equals("0")) {
                        httpUtils.send(HttpRequest.HttpMethod.GET, urlUtils.getGOOD(ins_id, AppCreate.id), new RequestCallBack<String>() {
                            @Override
                            public void onSuccess(ResponseInfo<String> responseInfo) {
                                if (JsonUtils.checkSuccess(responseInfo.result)) {
//                                    holder.ins_good.setImageResource(R.drawable.dashboard_recommand_on_default);
                                    inspiration.setHot("1");
                                    int newcount = Integer.parseInt(hot_count) + 1;
                                    inspiration.setHot_count("" + newcount);
                                    holder.ins_good_count.setText(newcount + "");
                                    notifyDataSetInvalidated();
//                                    Message msg = new Message();
//                                    msg.what=1;
//                                    msg.arg1=1;
//                                    msg.arg2=newcount;
//                                    msg.obj=holder;
//                                    handler.sendMessage(msg);
                                    try {
                                        dbUtil.saveOrUpdate(inspiration);
                                    } catch (DbException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(HttpException e, String s) {

                            }
                        });
                    } else {
                        httpUtils.send(HttpRequest.HttpMethod.GET, urlUtils.getCancelGood(ins_id, AppCreate.id), new RequestCallBack<String>() {
                            @Override
                            public void onSuccess(ResponseInfo<String> responseInfo) {
                                if (JsonUtils.checkSuccess(responseInfo.result)) {
//                                    holder.ins_good.setImageResource(R.drawable.dashboard_recommand_off_default);
                                    inspiration.setHot("0");
                                    int newcount = Integer.parseInt(hot_count) - 1;
                                    inspiration.setHot_count("" + newcount);
                                    holder.ins_good_count.setText(newcount+"");
                                    notifyDataSetInvalidated();
//                                    Message msg = new Message();
//                                    msg.what=1;
//                                    msg.arg1=0;
//                                    msg.arg2=newcount;
//                                    msg.obj=holder;
//                                    handler.sendMessage(msg);
                                    try {
                                        dbUtil.saveOrUpdate(inspiration);
                                    } catch (DbException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(HttpException e, String s) {

                            }
                        });
                    }
                }
                else {
                   Toast.makeText(context,"请登陆",Toast.LENGTH_SHORT).show();
               }
            }
        });
        //todo comment
        //设置评论=======================================
        holder.ins_comment_count.setText(inspiration.getComment_count());
        holder.ins_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("ins_id", inspiration.getId());
                bundle.putString("user_id",inspiration.getUser_id());
                Intent intent=new Intent(context, Comment.class);
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        //设置用户收藏喜欢---------------------------------------------------------
        String like = inspiration.getLike();
        if (like.equals("0")) {
            holder.ins_like.setImageResource(R.drawable.dashboard_like_off_default);
        } else {
            holder.ins_like.setImageResource(R.drawable.dashboard_like_on_default);
        }

        holder.ins_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String like = inspiration.getLike();
                if (SystemInfo.sharedPreferences.getBoolean("login", false)) {
                    if (like.equals("0")) {
                        httpUtils.send(HttpRequest.HttpMethod.GET, urlUtils.getLIKE(ins_id, AppCreate.id), new RequestCallBack<String>() {
                            @Override
                            public void onSuccess(ResponseInfo<String> responseInfo) {
                                if (JsonUtils.checkSuccess(responseInfo.result)) {
//                                    holder.ins_like.setImageResource(R.drawable.dashboard_like_on_default);
                                    inspiration.setLike("1");
                                    notifyDataSetInvalidated();
//                                    updateItem(position);
                                    try {
                                        dbUtil.saveOrUpdate(inspiration);
                                    } catch (DbException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(HttpException e, String s) {

                            }
                        });
                    } else {
                        httpUtils.send(HttpRequest.HttpMethod.GET, urlUtils.getDISLIKE(ins_id, AppCreate.id), new RequestCallBack<String>() {
                            @Override
                            public void onSuccess(ResponseInfo<String> responseInfo) {
                                if (JsonUtils.checkSuccess(responseInfo.result)) {
//                                    holder.ins_like.setImageResource(R.drawable.dashboard_like_off_default);
                                    inspiration.setLike("0");
                                    notifyDataSetInvalidated();
                                    try {
                                        dbUtil.saveOrUpdate(inspiration);
                                    } catch (DbException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(HttpException e, String s) {

                            }
                        });
                    }
                }
                else {
                    Toast.makeText(context,"请登陆",Toast.LENGTH_SHORT).show();
                }
            }
        });


        return ret;
    }

//更新指定的item
    //todo 接口回掉
    public void updateItem(int itemindex){
        //得到第一个可显示控件的位置，
        int firstVisiblePosition = mListView.getFirstVisiblePosition();
        //当被滑过去则不更新
        if (itemindex-firstVisiblePosition>0){
            //得到需要更新的item的view
            View ret = mListView.getChildAt(itemindex - firstVisiblePosition);
            //从view中获得holder
            ViewHolder holder= (ViewHolder) ret.getTag();
            Inspiration inspiration = list.get(itemindex);

            holder.header = (ImageView) ret.findViewById(R.id.ins_header);
            holder.username = (TextView) ret.findViewById(R.id.ins_username);
            holder.create_time = (TextView) ret.findViewById(R.id.ins_create_time);
            holder.attention = (ImageView) ret.findViewById(R.id.ins_attention);
            holder.delete = (ImageView) ret.findViewById(R.id.delete_ins);
            holder.ins_image = (ImageView) ret.findViewById(R.id.ins_image);
            holder.ins_image_count = (TextView) ret.findViewById(R.id.ins_image_count);
            holder.content = (TextView) ret.findViewById(R.id.ins_content);
            holder.tags_recycler = (RecyclerView) ret.findViewById(R.id.ins_tags);
            holder.ins_address_layout = (LinearLayout) ret.findViewById(R.id.ins_address_layout);
            holder.ins_address = (TextView) ret.findViewById(R.id.ins_address);
            holder.ins_good = (ImageView) ret.findViewById(R.id.ins_good);
            holder.ins_good_count = (TextView) ret.findViewById(R.id.ins_good_count);
            holder.ins_comment = (ImageView) ret.findViewById(R.id.ins_comment);
            holder.ins_comment_count = (TextView) ret.findViewById(R.id.ins_comment_count);
            holder.ins_like = (ImageView) ret.findViewById(R.id.ins_like);
            holder.tag_layout = (LinearLayout) ret.findViewById(R.id.ins_tag_layout);
            ret.postInvalidate();
        }

    }

    @Override
    public void onItemClick(String tag) {
        Toast.makeText(context, "tag:" + tag, Toast.LENGTH_SHORT).show();
    }

    class ViewHolder {
        ImageView header;
        TextView username;
        TextView create_time;
        ImageView attention;
        ImageView delete;
        ImageView ins_image;
        TextView ins_image_count;
        TextView content;
        RecyclerView tags_recycler;
        LinearLayout ins_address_layout;
        TextView ins_address;
        ImageView ins_good;
        TextView ins_good_count;
        ImageView ins_comment;
        TextView ins_comment_count;
        ImageView ins_like;
        LinearLayout tag_layout;
    }
}
