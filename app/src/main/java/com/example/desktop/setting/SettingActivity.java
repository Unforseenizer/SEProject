package com.example.desktop.setting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.desktop.project.R;

// implements SettingActivityFragment.Communicator
public class SettingActivity extends AppCompatActivity {

    private SettingActivityFragment f1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);
       /* f1 = (SettingActivityFragment) getSupportFragmentManager().findFragmentById(R.id.setting_fragment);
        f1.setcomm(this);*/
        Toolbar toolbar = (Toolbar) findViewById(R.id.action_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

   /* public void respond(boolean config) {
        if (config) {

            Settings.IS_NOTIFY = true;
        } else {
            Settings.IS_NOTIFY = false;

        }
    }*/

    public void onDestroy() {
        super.onDestroy();

    }
}
