package com.example.desktop.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Event_Edit extends AppCompatActivity implements View.OnClickListener {

    EditText eventName, eventDescription;
    Button location, delete, update;
    Double lat, lng;
    Event dump;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eventlist_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        eventName = (EditText) findViewById(R.id.val_eventname);
        eventDescription = (EditText) findViewById(R.id.val_eventdesc);
        delete = (Button) findViewById(R.id.event_form_delete);
        update = (Button) findViewById(R.id.event_form_update);
        location = (Button) findViewById(R.id.val_location);

        delete.setOnClickListener(this);
        update.setOnClickListener(this);
        location.setOnClickListener(this);

        if (getIntent().hasExtra("editobj")) {
            dump = new Event((Event) getIntent().getSerializableExtra("editobj"));
            eventName.setText(dump.getEventName());
            eventDescription.setText(dump.getEventDesc());
            lat = dump.getLat();
            lng = dump.getLng();
        }
    }

    public void fetchData() {
        dump.setEventDetail(eventName.getText().toString(), eventDescription.getText().toString());
        dump.setLatLng(lat, lng);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.event_form_update:
                fetchData();
                new NetUtil.updateEvent(Event_Edit.this).execute(dump);
                new NetUtil.getJSON(Event_Edit.this).execute();
                Settings.adapter.clear();
                Settings.adapter.addAll(Settings.EventHoldList);
                Settings.adapter.notifyDataSetChanged();
                getSupportFragmentManager().popBackStack("MainList", 0);

                this.finish();
                break;
            case R.id.event_form_delete:
                new NetUtil.delEvent(Event_Edit.this).execute(dump);

                new NetUtil.getJSON(Event_Edit.this).execute();
                Settings.adapter.clear();
                Settings.adapter.addAll(Settings.EventHoldList);
                Settings.adapter.notifyDataSetChanged();
                this.finish();
                break;
            case R.id.val_location:
                Intent mapClick = new Intent(Event_Edit.this, mapClick.class);
                if (lat != null && lng != null) {
                    mapClick.putExtra("savedLat", lat);
                    mapClick.putExtra("savedLng", lng);
                    mapClick.putExtra("EDIT_MODE", true);
                }
                startActivityForResult(mapClick, 1);
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    lat = data.getDoubleExtra("Lat", 00);
                    lng = data.getDoubleExtra("Lng", 00);
                }
                break;
        }
    }
}