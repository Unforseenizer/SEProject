package com.example.desktop.project;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class SettingActivity extends AppCompatActivity implements SettingActivityFragment.Communicator {
    public static boolean isNotification;
    private SettingActivityFragment f1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);
        f1 = (SettingActivityFragment) getSupportFragmentManager().findFragmentById(R.id.setting_fragment);
        f1.setcomm(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.action_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void respond(boolean config) {
        if (config) {

            Settings.IS_NOTIFY = true;
        } else {
            Settings.IS_NOTIFY = false;

        }
    }

    public void OnDestroy() {
        SharedPreferences.Editor editor = getSharedPreferences("setting", MODE_PRIVATE).edit();
        editor.putBoolean("isNotification", isNotification);
        editor.commit();
    }
}
