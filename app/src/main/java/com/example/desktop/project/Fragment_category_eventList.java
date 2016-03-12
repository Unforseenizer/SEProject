package com.example.desktop.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Dudy on 1/3/2016.
 */
public class Fragment_category_eventList extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_category_eventlist, null, false);

        Settings.EventHoldList = new ArrayList<Event>();
        ListView eventlist = (ListView) root.findViewById(R.id.frag3_list);
        Settings.adapter = new EventAdapter(getContext(), R.layout.event_item, Settings.EventHoldList);
        eventlist.setAdapter(Settings.adapter);
        new NetUtil.getJSON(getContext()).execute();

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
}
