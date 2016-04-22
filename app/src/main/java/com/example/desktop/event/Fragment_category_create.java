package com.example.desktop.event;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.desktop.project.R;
import com.example.desktop.setting.Settings;

public class Fragment_category_create extends Fragment implements TimePickerFragment.OnHeadlineSelectedListener, DatePickerDialog.OnDateSetListener, View.OnClickListener {
    EditText eventName, eventDescription;
    Button eventLocation, EventSubmit, datepick, timepick;
    Double Lat;
    Double Lng;
    AlertDialog.Builder builder;
    Event Editdump;
    int year, month, day, hour, min;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_category_create, null, false);

        eventName = (EditText) root.findViewById(R.id.create_eventname);
        eventDescription = (EditText) root.findViewById(R.id.create_eventdesc);
        eventLocation = (Button) root.findViewById(R.id.create_location);
        EventSubmit = (Button) root.findViewById(R.id.create_submit);
        datepick = (Button) root.findViewById(R.id.date_click);
        timepick = (Button) root.findViewById(R.id.time_click);

        Editdump = new Event("dump", "dump");

        datepick.setOnClickListener(this);
        timepick.setOnClickListener(this);
        eventLocation.setOnClickListener(this);
        EventSubmit.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.time_click):
                TimePickerFragment newFragment = new TimePickerFragment();
                newFragment.setTargetFragment(Fragment_category_create.this, 0);
                newFragment.show(getFragmentManager(), "timePicker");
                break;
            case (R.id.date_click):
                DatePickerFragment newFragment2 = new DatePickerFragment();
                newFragment2.setTargetFragment(Fragment_category_create.this, 2);
                newFragment2.show(getFragmentManager(), "datePicker");
                break;
            case (R.id.create_location):
                Intent mapClick = new Intent(getContext(), mapClick.class);
                if (Lat != null && Lng != null) {
                    mapClick.putExtra("savedLat", Lat);
                    mapClick.putExtra("savedLng", Lng);
                }
                mapClick.putExtra("EDIT_MODE", true);
                startActivityForResult(mapClick, 1);
                break;
            case (R.id.create_submit):
                dialogBuild();
                builder.create().show();
                break;
        }
    }

    public void dialogBuild() {
        builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Confirm create event?");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (Lat == null || Lng == null) {
                    Toast.makeText(getContext(), "Vaccant Location", Toast.LENGTH_SHORT).show();
                } else if (eventName.getText().toString().isEmpty() || eventDescription.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Vaccant Block", Toast.LENGTH_SHORT).show();
                } else {
                    fetchData();
                    dialog.dismiss();
                    new NetUtil.sendEvent(getContext()).execute(Editdump);
                    Toast.makeText(getContext(), "Submitting", Toast.LENGTH_SHORT).show();
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == 1) {
                    Lat = data.getDoubleExtra("Lat", 00);
                    Lng = data.getDoubleExtra("Lng", 00);
                }
                break;
        }
    }

    public void fetchData() {
        Editdump.setEventDetail(eventName.getText().toString(), eventDescription.getText().toString());
        Editdump.setLatLng(Lat, Lng);
        Editdump.setTimstamp(year, month, day, hour, min);
        Editdump.setOrigizator(Settings.USERNAME);
    }

    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        this.year = year;
        month = monthOfYear + 1;
        day = dayOfMonth;
        datepick.setText(String.format("%s-%s-%s", day, month, year));

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        hour = hourOfDay;

        min = minute;
        String shour = String.valueOf(hour);
        String smin = String.valueOf(hour);
        shour = (shour.length() == 1) ? 0 + shour : shour;
        smin = (smin.length() == 1) ? 0 + smin : smin;
        timepick.setText(String.format("%s : %s", shour, smin));

    }
}
