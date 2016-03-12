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
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView username;

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

       /*ImageView navpic = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.nav_userpic);
        navpic.setImageURI(Settings.propic);

        username = (TextView) navigationView.getHeaderView(0).findViewById(R.id.ShowName);
        username.setText(Settings.USERNAME);*/
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

        int id = item.getItemId();
        if (id == R.id.nav_main) {
            Intent intent1 = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent1);
        } else if (id == R.id.nav_message) {
        } else if (id == R.id.nav_notification) {
        } else if (id == R.id.nav_like) {
        } else if (id == R.id.nav_setting) {
            Intent intent5 = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(intent5);
        } else if (id == R.id.nav_eventlist) {
            Fragment_category f1 = new Fragment_category();
            if (!f1.isAdded()) {
                ft.replace(R.id.fragment_content, f1).commit();
            }
        } else if (id == R.id.nav_logout) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            Toast.makeText(MainActivity.this, "Logout Sucessful.", Toast.LENGTH_SHORT);
            startActivity(intent);

            this.finish();

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
