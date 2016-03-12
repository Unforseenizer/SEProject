package com.example.desktop.project;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class Fragment_category_map extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    public static SupportMapFragment mapFragment;
    private GoogleMap mMap;
    private View root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        if (root != null) {
            ViewGroup parent = (ViewGroup) root.getParent();
            if (parent != null)
                parent.removeView(root);
        }

        try {
            root = inflater.inflate(R.layout.event_map_click, container, false);
        } catch (InflateException e) {
                        /* map is already there, just return view as it is */
        }

        if (mapFragment == null) {
            try {
                mapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(this);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return root;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.e("frag2", "onMapReady");
        mMap = googleMap;
        MapUtil.initCamera(googleMap);
        if (Settings.EventHoldList != null) {
            MapUtil.parseMarker(mMap);
            Log.e("Parse", "Marker");
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public void onStart() {
        Log.e("frag2", "onStart");
        SupportMapFragment mapFragment = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map));
        if (mapFragment != null) {
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.attach(mapFragment);
            ft.commit();
        }
        super.onStart();
    }

    @Override
    public void onDestroyView() {
        Log.e("frag2", "onDestroyView");
        super.onDestroyView();

    }

    private void killOldMap() {
        SupportMapFragment mapFragment = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map));
        if (mapFragment != null) {
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.detach(mapFragment);
            ft.commit();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("frag2", "onDestroy");
    }

    @Override
    public void onDetach() {
        Log.e("frag2  ", "onDetach");
        super.onDetach();
    }


    @Override
    public void onAttach(Context context) {
        Log.e("frag2", "onAttach");
        super.onAttach(context);
    }


    @Override
    public void onPause() {
        killOldMap();
        mMap = null;
        mapFragment = null;
        Log.e("frag2", "onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.e("frag2", "onStop");
        super.onStop();
    }

    @Override
    public void onResume() {
        Log.e("frag2", "onResume");

        super.onResume();
    }

}


