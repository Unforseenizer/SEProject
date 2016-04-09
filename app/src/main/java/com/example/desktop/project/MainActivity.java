package com.example.desktop.project;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.desktop.event.Fragment_category;
import com.example.desktop.msg.msg_done.MsgFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView username;
    Toolbar toolbar;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;

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

        android.support.v4.app.FragmentTransaction ft = (android.support.v4.app.FragmentTransaction) getSupportFragmentManager().beginTransaction();
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
                Fragment_category f1 = new Fragment_category();
                if (!f1.isAdded()) {
                    ft.replace(R.id.fragment_content, f1).commit();
                }
                break;
            case R.id.nav_logout:
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                Toast.makeText(MainActivity.this, "Logout Sucessful.", Toast.LENGTH_SHORT);
                startActivity(intent);
                this.finish();
                break;
            case R.id.nav_message:
                MsgFragment msgFragment = new MsgFragment();
                ft.replace(R.id.fragment_content, msgFragment).commit();
                break;
            default:
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
