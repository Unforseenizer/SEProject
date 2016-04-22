package com.example.desktop.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.desktop.project.LoginDataBaseAdapter;
import com.example.desktop.project.R;
import com.example.desktop.setting.Settings;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    LoginDataBaseAdapter loginDataBaseAdapter;

    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

        final Button login = (Button) findViewById(R.id.login);
        TextView reg = (TextView) findViewById(R.id.register);
        TextView forgot = (TextView) findViewById(R.id.forgot);

        login.setOnClickListener(this);
        reg.setOnClickListener(this);
        forgot.setOnClickListener(this);

        TextView shortcut = (TextView) findViewById(R.id.loginshortcut);
        shortcut.setOnClickListener(this);

        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        loginDataBaseAdapter = loginDataBaseAdapter.open();

        SharedPreferences settings = getSharedPreferences("setting", MODE_PRIVATE);
        boolean isnote = settings.getBoolean("isNotification", true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginshortcut:
                Intent shortcut = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(shortcut);
                finish();
                break;
            case R.id.login:
                final EditText editTextUserName = (EditText) findViewById(R.id.L_user);
                final EditText editTextPassword = (EditText) findViewById(R.id.L_pw);

                // get The User name and Password
                String userName1 = editTextUserName.getText().toString();
                String password = editTextPassword.getText().toString();

                // fetch the Password form database for respective user name
                String storedPassword = loginDataBaseAdapter.getSinlgeEntry(userName1);

                // check if the Stored password matches with  Password entered by user
                if (password.equals(storedPassword) && password.length() > 0) {
                    Toast.makeText(LoginActivity.this, "Congrats: Login Successfull", Toast.LENGTH_LONG).show();
                    if (Settings.IS_NOTIFY == true) {
                        final int notifyID = 1; // 通知的識別號碼
                        final NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE); // 取得系統的通知服務
                        final Notification notification = new Notification.Builder(getApplicationContext()).setSmallIcon(R.drawable.ic_app).setContentTitle("Welcome !").setContentText("You're logged in !").build(); // 建立通知
                        notificationManager.notify(notifyID, notification); // 發送通知
                    }
                    Settings.USERNAME = userName1;
                    Intent mainintent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(mainintent);
                    finish();
                } else
                    Toast.makeText(LoginActivity.this, "User Name or Password does not match", Toast.LENGTH_LONG).show();
                break;

            case R.id.register:
                Intent intent2 = new Intent(LoginActivity.this, register.class);
                startActivityForResult(intent2, 1);
                break;
        }

    }

    protected void onDestroy() {
        super.onDestroy();
        // Close The Database        loginDataBaseAdapter.close();
        Vibrator vibrator;
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(500);

        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(LoginActivity.this, notification);
        r.play();
        loginDataBaseAdapter.close();
    }


    @Override
    public void onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
        {
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
            return;
        }
        else {
            Toast.makeText(getBaseContext(), "Tap back button in order to exit", Toast.LENGTH_SHORT).show();
        }
        mBackPressed = System.currentTimeMillis();
    }
}
