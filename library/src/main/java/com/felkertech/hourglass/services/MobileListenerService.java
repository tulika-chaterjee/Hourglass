package com.felkertech.hourglass.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import com.felkertech.hourglass.R;
import com.felkertech.hourglass.datatypes.MobileBattery;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

/**
 * Created by guest1 on 1/10/2016.
 */
public class MobileListenerService extends WearableListenerService {
    private int NOTIFICATION_ID = 12;
    private MobileBattery mb;
    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        String message = new String(messageEvent.getData());

        if(message.contains("enable-mobile-battery")) {
            mb = new MobileBattery(this);
            mb.registerBattery();
        } else if(message.contains("disable-mobile-battery")) {
            mb.unregisterBattery();
        }
    }
}
