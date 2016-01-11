package com.wander.MyAdapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wander.model.Info;
import com.wander.questworld.R;
import com.wander.xutils.BtUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by wander on 2015/6/15.
 * time 16:49
 * Email 18955260352@163.com
 */
public class InfoAdapter extends BaseAdapter {
    private List<Info> list;
    private Context context;
    private ViewHolder holder;
    private ViewHolder2 holder2;
    private LayoutInflater inflater;
    private final SpannableString spannableString;

    public InfoAdapter(List<Info> list, Context context) {
        this.list = list;
        this.context = context;
        if (context != null) {
            inflater = LayoutInflater.from(context);
        }
        spannableString = new SpannableString("赞");
        Drawable drawable = context.getResources().getDrawable(R.drawable.liked_a);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        spannableString.setSpan(new ImageSpan(drawable), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
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
        long ret = position;
        try {
            ret = Long.parseLong(list.get(position).getIns_id());
        } catch (Exception e) {
        }
        return ret;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View ret = null;
        Info info = list.get(position);

        if (info.getPics().length() < 10) {
            if (convertView != null) {
                ret = convertView;
            } else {
                ret = inflater.inflate(R.layout.item_info2, parent, false);
            }
            holder2 = (ViewHolder2) ret.getTag();
            if (holder2 == null) {
                holder2= new ViewHolder2();
                holder2.header = (ImageView) ret.findViewById(R.id.info_header);
                holder2.content = (TextView) ret.findViewById(R.id.info_content);
                holder2.create_time = (TextView) ret.findViewById(R.id.info_create_time);
                holder2.username = (TextView) ret.findViewById(R.id.info_username);
                holder2.message= (TextView) ret.findViewById(R.id.info_message);
            }
            holder2.username.setText(info.getUsername());
            holder2.create_time.setText(info.getCreate_time());
            if (info.getType().equals("hot")) {
                holder2.content.setText(spannableString);
            }else {
                holder2.content.setText(info.getContent());
            }
            BtUtils.getIconBitmapUtils(context).display(holder2.header,info.getUser_header());
            holder2.message.setText(info.getMessage());

        } else {
            if (convertView != null) {
                ret = convertView;
            } else {
                ret = inflater.inflate(R.layout.item_info, parent, false);
            }
            holder = (ViewHolder) ret.getTag();
            if (holder == null) {
                holder = new ViewHolder();
                holder.header = (ImageView) ret.findViewById(R.id.info_header);
                holder.content = (TextView) ret.findViewById(R.id.info_content);
                holder.create_time = (TextView) ret.findViewById(R.id.info_create_time);
                holder.username = (TextView) ret.findViewById(R.id.info_username);
                holder.pic = (ImageView) ret.findViewById(R.id.info_pic);
            }

            holder.username.setText(info.getUsername());
            holder.create_time.setText(info.getCreate_time());
            if (info.getType().equals("hot")) {
                holder.content.setText(spannableString);
            }else {
                holder.content.setText(info.getContent());
            }
            BtUtils.getIconBitmapUtils(context).display(holder.header,info.getUser_header());
            String pics = info.getPics();
            List<String> pic_list = Arrays.asList(pics.split(","));
            BtUtils.getBitmapUtils(context).display(holder.pic, pic_list.get(0));

        }
        return ret;
    }

    public static final int PIC = 0;
    public static final int MESSAGE = 1;

    @Override
    public int getItemViewType(int position) {

        if (list.get(position).getPics().length() < 10) {
            return MESSAGE;
        } else {
            return PIC;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    class ViewHolder {
        private ImageView header;
        private ImageView pic;
        private TextView username;
        private TextView content;
        private TextView create_time;
    }

    class ViewHolder2 {
        private ImageView header;
        private TextView message;
        private TextView username;
        private TextView content;
        private TextView create_time;
    }


}
