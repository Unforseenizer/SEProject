package com.example.desktop.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class Event_Join extends AppCompatActivity {

    TextView t1, t2, t3, t4, t5;
    Button b1, b2, b3, b4;
    Event dump;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event__join);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        b1 = (Button) findViewById(R.id.join_1);
        b2 = (Button) findViewById(R.id.join_2);
        b3 = (Button) findViewById(R.id.join_3);

        t1 = (TextView) findViewById(R.id.detail_1);
        t2 = (TextView) findViewById(R.id.detail_2);
        t3 = (TextView) findViewById(R.id.detail_3);
        t4 = (TextView) findViewById(R.id.detail_4);
        t5 = (TextView) findViewById(R.id.detail_5);
        b4 = (Button) findViewById(R.id.detail_location);

        dump = (Event) getIntent().getSerializableExtra("editobj");

        t1.setText(dump.getEventName());
        t2.setText(dump.getEventDesc());
        t3.setText(dump.getEventTime().toString());
        t4.setText(dump.getOrigizator());
        t5.setText(dump.getParticipant().toString());

        if (dump.isOriginizator(Settings.USERNAME)) b2.setVisibility(View.VISIBLE);
        else if (!dump.isParticipant(Settings.USERNAME))
            b1.setVisibility(View.VISIBLE);
        else
            b3.setVisibility(View.VISIBLE);

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editEvent();
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinEvent();
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quitEvent();
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapClick = new Intent(Event_Join.this, mapClick.class);
                if (dump.getLat() != null && dump.getLng() != null) {
                    mapClick.putExtra("savedLat", dump.getLat());
                    mapClick.putExtra("savedLng", dump.getLng());
                }
                startActivity(mapClick);
            }
        });
    }


    public void joinEvent() {
        dump.joinEvent(Settings.USERNAME);
        new NetUtil.updateEvent(Event_Join.this).execute(dump);
        b1.setVisibility(View.GONE);
        b3.setVisibility(View.VISIBLE);

    }

    public void editEvent() {
        Intent intent = new Intent(Event_Join.this, Event_Edit.class);
        intent.putExtra("editobj", dump);
        startActivity(intent);
    }

    public void quitEvent() {
        dump.quitEvent(Settings.USERNAME);
        new NetUtil.updateEvent(Event_Join.this).execute(dump);
        b3.setVisibility(View.GONE);
        b1.setVisibility(View.VISIBLE);
    }
}