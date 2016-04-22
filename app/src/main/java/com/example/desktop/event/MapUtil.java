package com.example.desktop.event;

import com.example.desktop.setting.Settings;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapUtil {
    public static void initCamera(GoogleMap mMap) {
        LatLng OUHK = new LatLng(22.316353, 114.180182);
        mMap.addMarker(new MarkerOptions().position(OUHK).title("Open University"));
        CameraPosition OU = new CameraPosition.Builder().target(OUHK).zoom(15).build();
        CameraUpdate pos = CameraUpdateFactory.newCameraPosition(OU);
        mMap.animateCamera(pos);
    }

    public static void parseMarker(GoogleMap googleMap) {
        ArrayList<Event> arr = new ArrayList<Event>();
        arr.addAll(Settings.EventHoldList);
        for (Event evt : arr) {
            googleMap.addMarker(new MarkerOptions().position(new LatLng(evt.getLat(), evt.getLng())).snippet(evt.getEventDesc()).title(evt.getEventName()));
        }
    }
}
