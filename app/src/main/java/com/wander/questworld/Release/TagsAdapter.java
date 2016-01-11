package com.wander.questworld.Release;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wander.questworld.R;


/**
 * Created by wander on 2015/5/30.
 * time 11:32
 * Email 18955260352@163.com
 */
public class TagsAdapter extends RecyclerView.Adapter<TagsAdapter.ViewHolder> implements View.OnClickListener {
    private RecyclerView recyclerView;
    private OnItemClickListener listener;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tag, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(Tags.tags.get(position));
    }

    @Override
    public int getItemCount() {
        return Tags.tags.size();
    }

    public void removeItem(int position){
        Tags.tags.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            RecyclerView.ItemAnimator itemAnimator = recyclerView.getItemAnimator();
            if (itemAnimator.isRunning()) {
                return;
            }
            int position = recyclerView.getChildAdapterPosition(v);
            listener.onItemClick(position);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.item_tag);

        }
    }
}
