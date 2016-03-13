package com.example.desktop.project;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

        getView(inflater, container, savedInstanceState);

        if (mapFragment == null) {
            try {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                mapFragment = SupportMapFragment.newInstance();
                fragmentTransaction.replace(R.id.frag_map, mapFragment).commit();
                mapFragment.getMapAsync(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return root;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(3);
        MapUtil.initCamera(mMap);
        if (Settings.EventHoldList != null) {
            MapUtil.parseMarker(mMap);
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
    }

    @Override
    public void onStart() {
        SupportMapFragment mapFragment = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.frag_map));
        if (mapFragment != null) {
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.attach(mapFragment);
            ft.commit();
        }
        super.onStart();
    }

    @Override
    public void onPause() {
        killOldMap();
        mMap = null;
        mapFragment = null;
        super.onPause();
    }

    private void killOldMap() {
        SupportMapFragment mapFragment = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.frag_map));
        if (mapFragment != null) {
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.detach(mapFragment);
            ft.commit();
        }
    }

    public View getView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        if (root != null) {
            ViewGroup parent = (ViewGroup) root.getParent();
            if (parent != null)
                parent.removeView(root);
        }

        try {
            root = inflater.inflate(R.layout.fragment_map, container, false);
        } catch (InflateException e) {
                        /* map is already there, just return view as it is */
        }
        return root;
    }
}


