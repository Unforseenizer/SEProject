package com.example.desktop.event;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.desktop.project.R;
import com.example.desktop.setting.Settings;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class Fragment_category_map extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    public static SupportMapFragment mapFragment;
    FragmentTransaction fragmentTransaction;
    private GoogleMap mMap;
    private View root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getView(inflater, container, savedInstanceState);
        if (mapFragment == null) {
            fragmentTransaction = getFragmentManager().beginTransaction();
            mapFragment = SupportMapFragment.newInstance();
            fragmentTransaction.replace(R.id.frag_map, mapFragment).commit();
        }
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapFragment.getMapAsync(this);
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
        mapFragment = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.frag_map));
        if (mapFragment != null) {
            fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.attach(mapFragment).commit();
            mapFragment.getMapAsync(this);
        }
        super.onStart();
    }

    @Override
    public void onPause() {
        mapFragment = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.frag_map));
        if (mapFragment != null) {
            fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.detach(mapFragment).commit();
        }
        mMap = null;
        mapFragment = null;
        super.onPause();
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


