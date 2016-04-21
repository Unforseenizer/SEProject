package com.example.desktop.msg;

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
    ClickListener clickListener;

    public MsgAdapter(Context mContext, List<Message> data) {
        inflater = LayoutInflater.from(mContext);
        this.data = data;
    }

    @Override
    public void registerAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
        super.registerAdapterDataObserver(observer);
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

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public interface ClickListener {
        public void itemClicked(View view, int position);
    }

    class MsgHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView sender, title, time;

        public MsgHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            sender = (TextView) itemView.findViewById(R.id.row1);
            title = (TextView) itemView.findViewById(R.id.row2);
            time = (TextView) itemView.findViewById(R.id.row3);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.itemClicked(v, getLayoutPosition());
            }

        }
    }
}
