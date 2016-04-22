package com.example.desktop.setting;

import android.app.Activity;
import android.content.SharedPreferences;

public class SPUtil {
    public static void fetchSP(String PreferenceName, Activity activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(PreferenceName, 0);
        Settings.IP_ADDRESS = sharedPreferences.getString("IP_ADDRESS", "");
        Settings.USERNAME = sharedPreferences.getString("USERNAME", "");
        Settings.IS_NOTIFY = sharedPreferences.getBoolean("IS_NOTIFY", false);
    }

    public static void saveSP(String PreferenceName, Activity activity) {
        SharedPreferences.Editor editor = activity.getSharedPreferences(PreferenceName, activity.MODE_PRIVATE).edit();
        editor.putString("IP_ADDRESS", Settings.IP_ADDRESS);
        editor.putString("USERNAME", Settings.USERNAME);
        editor.putBoolean("IS_NOTIFY", Settings.IS_NOTIFY);
        editor.commit();
    }
}
