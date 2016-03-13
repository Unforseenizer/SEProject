package com.example.desktop.project;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Event_Edit extends AppCompatActivity {

    EditText eventName, eventDate, eventDescription;
    Button eventLocation, EventSubmit, delete, update;
    Double Lat;
    Double Lng;
    AlertDialog.Builder builder;
    Event Editdump;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.eventlist_edit);
        init();
        Editdump = new Event("dump", "dump");
        isEdit(); // check if edit state
        EventSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBuild();
                builder.create().show();
            }
        });
        eventLocation = (Button) findViewById(R.id.val_location);
        eventLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapClick = new Intent(Event_Edit.this, mapClick.class);
                if (Lat != null && Lng != null) {
                    mapClick.putExtra("savedLat", Lat);
                    mapClick.putExtra("savedLng", Lng);
                }
                Log.e("lat", Lat.toString());
                Log.e("lng", Lng.toString());
                startActivityForResult(mapClick, 1);
            }
        });
    }

    // .putDouble("Lat", Lat);
    //.putDouble("Lng", Lng);
    public void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        eventName = (EditText) findViewById(R.id.val_eventname);
        eventDescription = (EditText) findViewById(R.id.val_eventdesc);
        delete = (Button) findViewById(R.id.event_form_delete);
        EventSubmit = (Button) findViewById(R.id.event_form_submit);
        update = (Button) findViewById(R.id.event_form_update);
    }

    public void dialogBuild() {
        builder = new AlertDialog.Builder(Event_Edit.this);
        builder.setTitle("Confirm create event?");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (Lat == null || Lng == null) {
                    Toast.makeText(Event_Edit.this, "Vaccant Location", Toast.LENGTH_SHORT).show();
                } else if (eventName.getText().toString().isEmpty() || eventDate.getText().toString().isEmpty() || eventDescription.getText().toString().isEmpty()) {
                    Toast.makeText(Event_Edit.this, "Vaccant Block", Toast.LENGTH_SHORT).show();
                } else {
                    fetchData();
                    dialog.dismiss();
                    new NetUtil.sendEvent(Event_Edit.this).execute(Editdump);
                    Toast.makeText(Event_Edit.this, "Submitting", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }

    public void isEdit() {
        if (getIntent().hasExtra("editobj")) {
            Editdump = new Event((Event) getIntent().getSerializableExtra("editobj"));
            setTitle("Edit Event");
            updateUI(Editdump);
            EventSubmit.setVisibility(View.INVISIBLE);
            delete.setVisibility(View.VISIBLE);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new NetUtil.delEvent(Event_Edit.this).execute(Editdump);
                }
            });
            update.setVisibility(View.VISIBLE);

            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fetchData();
                    new NetUtil.updateEvent(Event_Edit.this).execute(Editdump);
                }
            });
        }
    }

    public void updateUI(Event ex) {
        eventName.setText(ex.getEventName());
        eventDescription.setText(ex.getEventDesc());
        Lat = ex.getLat();
        Lng = ex.getLng();
    }

    public void fetchData() {
        Editdump.setEventDetail(eventName.getText().toString(), eventDescription.getText().toString());
        Editdump.setLatLng(Lat, Lng);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    Lat = data.getDoubleExtra("Lat", 00);
                    Lng = data.getDoubleExtra("Lng", 00);
                    Log.d("lat" + Lat, "lng:" + Lng);
                }
                break;
        }
    }

    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }
}