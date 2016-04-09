package com.example.desktop.msg.msg_done;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.desktop.project.R;

import java.util.Collections;
import java.util.List;

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.MsgHolder> {
    LayoutInflater inflater;
    List<Message> data = Collections.emptyList();

    public MsgAdapter(Context mContext, List<Message> data) {
        inflater = LayoutInflater.from(mContext);
        this.data = data;
    }

    @Override
    public MsgAdapter.MsgHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MsgHolder(inflater.inflate(R.layout.msg_row, parent, false));
    }

    @Override
    public void onBindViewHolder(MsgHolder holder, int position) {
        Message msg = data.get(position);
        holder.title.setText(msg.getTitle());
        holder.sender.setText(msg.getSender());
        holder.time.setText(msg.getMsgTime().toString());
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class MsgHolder extends RecyclerView.ViewHolder {
        TextView sender, title, time;

        public MsgHolder(View itemView) {
            super(itemView);
            sender = (TextView) itemView.findViewById(R.id.row1);
            title = (TextView) itemView.findViewById(R.id.row2);
            time = (TextView) itemView.findViewById(R.id.row3);
        }
    }
}
