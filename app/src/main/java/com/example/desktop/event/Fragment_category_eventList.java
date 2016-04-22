package com.example.desktop.event;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.desktop.project.R;
import com.example.desktop.setting.Settings;

import java.util.ArrayList;


public class Fragment_category_eventList extends Fragment implements SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {
    SwipeRefreshLayout swipe;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

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

        eventlist.setOnItemClickListener(this);
        return root;
    }

    @Override
    public void onRefresh() {
        NetUtil.getJSON task = new NetUtil.getJSON(getContext(), swipe);
        task.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Event event = Settings.EventHoldList.get(position);
        Intent intent = new Intent(getContext(), Event_Join.class);
        intent.putExtra("editobj", event);
        startActivity(intent);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.event_search, menu);
        setupSearch(menu);
    }

    private void setupSearch(Menu menu) {
        android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) menu.findItem(R.id.menu_search).getActionView();
        SearchManager sm = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        ComponentName cn = new ComponentName(getActivity(), EventResult.class);
        SearchableInfo info = sm.getSearchableInfo(cn);
        searchView.setSearchableInfo(info);
        searchView.setIconifiedByDefault(true);

    }
}
