package com.example.desktop.event;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.desktop.project.R;
import com.example.desktop.setting.Settings;

import java.util.ArrayList;

public class EventResult extends AppCompatActivity implements AdapterView.OnItemClickListener {
    Toolbar toolbar;
    ListView listView;
    EventAdapter adapter;
    ArrayList<Event> matchEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_result);

        toolbar = (Toolbar) findViewById(R.id.action_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Search Result");

        final Intent query = getIntent();
        doSearch(query);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        doSearch(intent);
    }

    private void doSearch(Intent i) {
        final String queryAct = i.getAction();
        if (!(Intent.ACTION_SEARCH.equals(queryAct))) {
            Toast.makeText(this, "NOT QUERYING", Toast.LENGTH_SHORT).show();
            return;
        }
        final String quertString = String.format(".*%s.*", i.getStringExtra(SearchManager.QUERY));
        matchEvent = new ArrayList<>();
        for (Event e : Settings.EventHoldList) {
            if (e.getEventName().matches(quertString))
                matchEvent.add(e);
        }
        adapter = new EventAdapter(this, R.layout.event_item, matchEvent);
        listView = (ListView) findViewById(R.id.event_result);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Event event = matchEvent.get(position);

        Intent intent = new Intent(this, Event_Join.class);
        intent.putExtra("editobj", event);
        startActivity(intent);
    }
}
