package com.example.desktop.msg;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;

import com.example.desktop.setting.Settings;

public class MsgServices extends Service {

    MessageList list = new MessageList();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        new MsgTask.getMsg(this).execute(Settings.USERNAME);

        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        long trigger = SystemClock.elapsedRealtime() + Settings.REFRESH_INTERVAL;
        Intent intent1 = new Intent("MESSAGE_SERVICE");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent1, 0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, trigger, pendingIntent);
        return super.onStartCommand(intent, flags, startId);
    }
}
