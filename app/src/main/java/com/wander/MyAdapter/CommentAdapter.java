package com.wander.MyAdapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wander.model.Comments;
import com.wander.questworld.AppCreate;
import com.wander.questworld.R;
import com.wander.xutils.BtUtils;

import java.util.List;

/**
 * Created by wander on 2015/6/13.
 * time 16:29
 * Email 18955260352@163.com
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> implements View.OnClickListener {
    private List<Comments> list;
    private Context context;
    private OnItemClickListener listener;
    private RecyclerView recyclerView;

    public CommentAdapter(List<Comments> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Comments comments = list.get(position);
        BtUtils.getIconBitmapUtils(context).display(holder.header, comments.getFrom_header());
        holder.header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"user click",Toast.LENGTH_SHORT).show();
            }
        });
        holder.from_username.setText(comments.getFrom_username());
        holder.create_time.setText(comments.getCreate_time());
        String content = comments.getContent();
        String from_id = comments.getFrom_id();
        if (from_id.equals(AppCreate.id)&&comments.getUser_id().equals(AppCreate.id)){
            holder.content.setText(content);
        }else {
            String to_username = comments.getTo_username();
            String from_username = "回复@"+ to_username +":";
            SpannableString spannableString=new SpannableString(from_username+content);
            spannableString.setSpan(new ForegroundColorSpan(Color.BLUE),2,3+to_username.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            View.OnClickListener click = new View.OnClickListener(){
                @Override
                public void onClick(View v)
                {
                    Toast.makeText(context,comments.getUser_id(),Toast.LENGTH_SHORT).show();
                }
            };
            spannableString.setSpan(new Clickable(click),2,3+to_username.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.content.setText(spannableString);
            holder.content.setMovementMethod(LinkMovementMethod.getInstance());
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addComment(Comments comments){
        list.add(0,comments);
        notifyItemInserted(0);
    }

    public void removeView(int position){
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void setOnitemClickListener(OnItemClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        this.recyclerView=recyclerView;
    }

    @Override
    public void onClick(View v) {
        if (listener!=null){
            int position = recyclerView.getChildAdapterPosition(v);
            Comments comments = list.get(position);
            listener.onItemClick(comments.getFrom_username(),comments.getFrom_id(),comments.getContent());
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView header;
        private TextView from_username;
        private TextView create_time;
        private TextView content;
        public ViewHolder(View itemView) {
            super(itemView);
            header= (ImageView) itemView.findViewById(R.id.from_header);
            from_username= (TextView) itemView.findViewById(R.id.from_username);
            create_time= (TextView) itemView.findViewById(R.id.comment_create_time);
            content= (TextView) itemView.findViewById(R.id.comment_content);
        }
    }

    class Clickable extends ClickableSpan implements View.OnClickListener {
        private final View.OnClickListener mListener;

        public Clickable(View.OnClickListener l){
            mListener = l;
        }

        @Override
        public void onClick(View v){
            mListener.onClick(v);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(String username,String user_id,String content);
    }
}
