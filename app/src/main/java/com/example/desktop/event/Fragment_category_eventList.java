package com.example.desktop.event;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.desktop.project.EventAdapter;
import com.example.desktop.project.R;
import com.example.desktop.project.Settings;

import java.util.ArrayList;


public class Fragment_category_eventList extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    SwipeRefreshLayout swipe;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_category_eventlist, null, false);
        Settings.EventHoldList = new ArrayList<Event>();

        ListView eventlist = (ListView) root.findViewById(R.id.frag3_list);
        Settings.adapter = new EventAdapter(getContext(), R.layout.event_item, Settings.EventHoldList);
        eventlist.setAdapter(Settings.adapter);
        new NetUtil.getJSON(getContext()).execute();

        swipe = (SwipeRefreshLayout) root.findViewById(R.id.eventlist_swipe);
        swipe.setOnRefreshListener(this);

        eventlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Event event = Settings.EventHoldList.get(position);
                Log.e("Browse CREATE TIME", event.getCREATE_TIME());
                Intent intent = new Intent(getContext(), Event_Join.class);
                intent.putExtra("editobj", event);
                startActivity(intent);
            }
        });
        return root;
    }

    @Override
    public void onRefresh() {
        NetUtil.getJSON task = new NetUtil.getJSON(getContext(),swipe);
        task.execute();
    }
}
