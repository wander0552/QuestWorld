package com.wander.questworld.Sight.Detail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wander.model.Note;
import com.wander.questworld.R;
import com.wander.xutils.BtUtils;

import java.util.List;

/**
 * Created by wander on 2015/5/24.
 * time 18:08
 * Email 18955260352@163.com
 */
public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {
    private Context context;
    private List<Note> list;

    public NotesAdapter(Context context, List<Note> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.attration_notes, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Note note = list.get(position);
        holder.nearby_name.setText(note.getDescription());
        BtUtils.getBitmapUtils(context).display(holder.image, note.getPhoto_url());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView nearby_name;
        ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            nearby_name = (TextView) itemView.findViewById(R.id.note_des);
            image = (ImageView) itemView.findViewById(R.id.notes_image);
        }
    }

}
