package com.example.desktop.msg;

import android.content.*;

public class ServReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1 = new Intent(context, MsgServices.class);
        context.startService(intent1);
    }
}
