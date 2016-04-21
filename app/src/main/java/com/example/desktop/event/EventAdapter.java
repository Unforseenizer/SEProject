package com.example.desktop.event;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.desktop.project.R;

import java.util.ArrayList;

public class EventAdapter extends ArrayAdapter<Event> {
    int ResourceId;

    public EventAdapter(Context context, int layoutResourceId, ArrayList<Event> obj) {
        super(context, layoutResourceId, obj);
        ResourceId = layoutResourceId;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        EventHolder holder;
        Event item = getItem(position);
        if (row == null) {
            row = LayoutInflater.from(getContext()).inflate(ResourceId, null);
            holder = new EventHolder();
            holder.name = (TextView) row.findViewById(R.id.eventitem_name);
            holder.date = (TextView) row.findViewById(R.id.eventitem_date);
            holder.desc = (TextView) row.findViewById(R.id.eventitem_desc);
            row.setTag(holder);
        } else {
            holder = (EventHolder) row.getTag();
        }
        holder.name.setText(item.getEventName());
        holder.date.setText(item.getEventTime().toString() != null ? item.getEventTime().toString() : " ");
        holder.desc.setText(item.getEventDesc());
        return row;
    }

    static class EventHolder {
        TextView name;
        TextView date;
        TextView desc;
    }
}
