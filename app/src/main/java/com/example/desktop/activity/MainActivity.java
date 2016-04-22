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
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.desktop.event.Fragment_category;
import com.example.desktop.msg.MsgFragment;
import com.example.desktop.msg.MsgServices;
import com.example.desktop.project.R;
import com.example.desktop.setting.SPUtil;
import com.example.desktop.setting.SettingFragment;
import com.example.desktop.setting.Settings;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    static final String TAG = "Home Page";
    TextView username;
    Toolbar toolbar;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    Fragment fg;
    FragmentTransaction ft;

    Intent servIntent;
    //Implement Passing Method to Fragment
    ArrayList<MyOnTouchListener> onTouchListeners = new ArrayList<>(10);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        inflateLayout();
        inflateFragment();
        updateDrawer();
        initServ();
    }

    public void inflateLayout() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        getSupportActionBar().setTitle("Home Page");
    }


    public void inflateFragment() {
        ft = getSupportFragmentManager().beginTransaction();
        fg = new MainFragment();
        ft.replace(R.id.fragment_content, fg).commit();
    }

    public void updateDrawer() {
        ImageView navpic = (ImageView) navigationView.findViewById(R.id.nav_userpic);
        navpic.setImageURI(Settings.propic);
        username = (TextView) navigationView.findViewById(R.id.ShowName);
        username.setText(Settings.USERNAME);
    }

    public void initServ() {
        servIntent = new Intent(this, MsgServices.class);
        startService(servIntent);
        SPUtil.fetchSP("Settings", this);
    }

    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
            {
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
                return;
            }
            else { Toast.makeText(getBaseContext(), "Tap back button in order to exit", Toast.LENGTH_SHORT).show(); }

            mBackPressed = System.currentTimeMillis();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        ft = getSupportFragmentManager().beginTransaction();
        ft.disallowAddToBackStack();
        switch (item.getItemId()) {
            case R.id.nav_home:
                if (!(fg instanceof MainFragment)) {
                    ft.setCustomAnimations(R.anim.push_up_in, R.anim.push_up_out);
                    getSupportActionBar().setTitle(TAG);
                    inflateFragment();
                }
                break;
            case R.id.nav_main:
                Intent intent1 = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent1);
                break;
            case R.id.nav_eventlist:
                if (!(fg instanceof Fragment_category)) {
                    fg = new Fragment_category();
                    ft.setCustomAnimations(R.anim.push_up_in, R.anim.push_up_out);
                    ft.replace(R.id.fragment_content, fg).commit();
                }
                break;
            case R.id.nav_message:
                fg = new MsgFragment();
                ft.setCustomAnimations(R.anim.push_up_in, R.anim.push_up_out);
                ft.replace(R.id.fragment_content, fg).commit();
                break;
            case R.id.nav_setting:
                if (!(fg instanceof SettingFragment)) {
                    fg = new SettingFragment();
                    ft.setCustomAnimations(R.anim.push_up_in, R.anim.push_up_out);
                    ft.replace(R.id.fragment_content, fg).commit();
                }
                break;
            case R.id.nav_logout:
                Toast.makeText(MainActivity.this, "Logout Sucessful.", Toast.LENGTH_SHORT);
                stopService(servIntent);
                Intent intent = new Intent(this,LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                this.finish();
            default:
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        Log.i("Activity", "KeyDown");
        if (fg instanceof MsgFragment) {
            MsgFragment.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

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
