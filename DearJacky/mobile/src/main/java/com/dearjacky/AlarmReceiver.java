package com.dearjacky;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by yanrongli on 4/29/16.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "alarm", Toast.LENGTH_SHORT).show();
        System.out.println("meow4");
        Intent watchIntent = new Intent(context, PhoneToWatchService.class);
        context.startService(watchIntent);
    }
}
