package com.example.desktop.msg;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;

import com.example.desktop.msg.msg_done.MsgTask;
import com.example.desktop.project.R;

public class MsgServices extends Service {

    MessageList list = new MessageList();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        new MsgTask.getMsg().execute();

        AlarmManager manager = (AlarmManager)getSystemService(ALARM_SERVICE);
        int second = 1000;
        long trigger = SystemClock.elapsedRealtime()+second;
        Intent intent1 = new Intent(this, MessageList.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,intent1,0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,trigger,pendingIntent);
        return super.onStartCommand( intent,  flags,  startId);
    }
}
