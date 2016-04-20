package com.example.desktop.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.desktop.event.Fragment_category;
import com.example.desktop.msg.MsgFragment;
import com.example.desktop.msg.MsgServices;
import com.example.desktop.project.R;
import com.example.desktop.project.Settings;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView username;
    Toolbar toolbar;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    Fragment fg;
    FragmentTransaction ft;

    @Override
    protected void onStart() {
        super.onStart();
        Intent servIntent = new Intent(this, MsgServices.class);
        startService(servIntent);

        ft = getSupportFragmentManager().beginTransaction();
        MainFragment mf = new MainFragment();
        ft.add(R.id.fragment_content, mf).commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        getSupportActionBar().setTitle("Home Page");
        updateDrawer();
    }

    public void updateDrawer() {
        ImageView navpic = (ImageView) navigationView.findViewById(R.id.nav_userpic);
        navpic.setImageURI(Settings.propic);
        username = (TextView) navigationView.findViewById(R.id.ShowName);
        username.setText(Settings.USERNAME);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        ft = getSupportFragmentManager().beginTransaction();
        switch (item.getItemId()) {
            case R.id.nav_main:
                Intent intent1 = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent1);
                break;
            case R.id.nav_setting:
                Intent intent5 = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent5);
                break;
            case R.id.nav_eventlist:
                fg = new Fragment_category();
                if (!fg.isAdded()) {
                    ft.replace(R.id.fragment_content, fg).commit();
                }
                break;
            case R.id.nav_logout:
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                Toast.makeText(MainActivity.this, "Logout Sucessful.", Toast.LENGTH_SHORT);
                startActivity(intent);
                this.finish();
                break;
            case R.id.nav_message:
                fg = new MsgFragment();
                ft.replace(R.id.fragment_content, fg).commit();
                break;
            default:
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        Log.i("Activity","KeyDown");
        if (fg instanceof MsgFragment) {
            MsgFragment.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

    //Implement Passing Method to Fragment
    private ArrayList<MyOnTouchListener> onTouchListeners = new ArrayList<>(
            10);

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        for (MyOnTouchListener listener : onTouchListeners) {
            listener.onTouch(ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    public void registerMyOnTouchListener(MyOnTouchListener myOnTouchListener) {
        onTouchListeners.add(myOnTouchListener);
    }

    public void unregisterMyOnTouchListener(MyOnTouchListener myOnTouchListener) {
        onTouchListeners.remove(myOnTouchListener);
    }

    public interface MyOnTouchListener {
        public boolean onTouch(MotionEvent ev);
    }
}
