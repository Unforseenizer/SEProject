package com.example.desktop.event;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.desktop.project.R;
import com.example.desktop.setting.Settings;

import java.util.ArrayList;

public class Event_Join extends AppCompatActivity implements View.OnClickListener {

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

        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);

        dump = (Event) getIntent().getSerializableExtra("editobj");
        updateUI();

        if (dump.isOriginizator(Settings.USERNAME)) b2.setVisibility(View.VISIBLE);
        else if (!dump.isParticipant(Settings.USERNAME))
            b1.setVisibility(View.VISIBLE);
        else
            b3.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.join_1:
                joinEvent();
                break;
            case R.id.join_2:
                editEvent();
                break;
            case R.id.join_3:
                quitEvent();
                break;
            case R.id.detail_location:
                showLocation();
                break;
        }
    }

    public void updateUI() {
        t1.setText(dump.getEventName());
        t2.setText(dump.getEventDesc());
        t3.setText(dump.getEventTime().toString());
        t4.setText(dump.getOrigizator());
        ArrayList<String> arr = dump.getParticipant();
        t5.setText("");
        for (String i : arr) {
            t5.append(i + "\n");
        }
    }

    public void joinEvent() {
        if (!dump.isParticipant(Settings.USERNAME)) {
            dump.joinEvent(Settings.USERNAME);
            new NetUtil.updateEvent(Event_Join.this).execute(dump);
            b1.setVisibility(View.GONE);
            b3.setVisibility(View.VISIBLE);
            Toast.makeText(Event_Join.this, "Joined", Toast.LENGTH_SHORT).show();
            updateUI();
        }

    }

    public void editEvent() {
        Intent intent = new Intent(Event_Join.this, Event_Edit.class);
        intent.putExtra("editobj", dump);
        startActivity(intent);
    }

    public void quitEvent() {
        if (dump.isParticipant(Settings.USERNAME)) {
            dump.quitEvent(Settings.USERNAME);
            new NetUtil.updateEvent(Event_Join.this).execute(dump);
            b3.setVisibility(View.GONE);
            b1.setVisibility(View.VISIBLE);
            Toast.makeText(Event_Join.this, "Quited", Toast.LENGTH_SHORT).show();
            updateUI();
        }
    }

    public void showLocation() {
        Intent mapClick = new Intent(Event_Join.this, mapClick.class);
        if (dump.getLat() != null && dump.getLng() != null) {
            mapClick.putExtra("savedLat", dump.getLat());
            mapClick.putExtra("savedLng", dump.getLng());
        }
        startActivity(mapClick);
    }
}