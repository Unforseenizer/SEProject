package com.example.desktop.msg;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ServReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1 = new Intent(context, MsgServices.class);
        context.startService(intent1);
    }
}
