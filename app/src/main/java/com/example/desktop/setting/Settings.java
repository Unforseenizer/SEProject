package com.example.desktop.setting;

import android.net.Uri;

import com.example.desktop.event.Event;
import com.example.desktop.event.EventAdapter;

import java.util.ArrayList;

public class Settings {

    public static final int REFRESH_INTERVAL = 3000;

    public static String IP_ADDRESS = "192.168.16.1"; //Server IP here
    public static String USERNAME = "Admin";
    public static boolean IS_NOTIFY = true;

    public static Uri propic;
    public static ArrayList<Event> EventHoldList;
    public static EventAdapter adapter;
}

