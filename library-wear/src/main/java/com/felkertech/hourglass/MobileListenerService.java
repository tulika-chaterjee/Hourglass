package com.felkertech.hourglass;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

/**
 * Created by guest1 on 1/10/2016.
 */
public class MobileListenerService extends WearableListenerService {
    private int NOTIFICATION_ID = 12;
    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        String message = new String(messageEvent.getData());

        if(message.contains("mobile-on")) {
            Intent intent = new Intent(Intent.ACTION_SET_WALLPAPER);
    //        new Intent(WallpaperManager.ACTION_LIVE_WALLPAPER_CHOOSER);
            intent = Intent.createChooser(intent, "Select Watchface");
            PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.ic_full_cancel)
                            .extend(new NotificationCompat.WearableExtender()
                                .setHintHideIcon(true)
                                .setBackground(BitmapFactory.decodeResource(getResources(), R.drawable.pattern))
                                .setHintAvoidBackgroundClipping(true))
                            .setContentTitle("Use this Watchface")
                            .addAction(new NotificationCompat.Action(R.drawable.abc_ic_menu_moreoverflow_mtrl_alpha, "Choose Watchfaces", pi))
                            .setContentText("Swipe left and open the watchface picker");
            mNotifyMgr.notify(NOTIFICATION_ID, mBuilder.build());
        } else if(message.contains("mobile-off")) {
            mNotifyMgr.cancel(NOTIFICATION_ID);
        }
    }
}
