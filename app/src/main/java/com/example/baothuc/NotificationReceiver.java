package com.example.baothuc;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationReceiver extends BroadcastReceiver {

    public static final int SELFIE_NOTIFICATION_ID = 1;
    //public static final int CHANNEL_ID = "SAMPLE_CHANNEL";

    // Notification action elements
    private Intent mainIntent;
    private PendingIntent pendingIntent;

    @Override
    public void onReceive(Context context, Intent intent) {
        int notificationId = intent.getIntExtra("notifyId", 0);
        mainIntent = new Intent(context, MainActivity.class);
        pendingIntent = PendingIntent.getActivity(context, 0, mainIntent, 0);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            CharSequence name = "NotificationChannel";
            String description = "Channel for Notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notifyId", name, importance);
            channel.setDescription(description);

            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, "notifyId")
                .setTicker("Time to wake your mind")
                .setAutoCancel(true)
                .setContentTitle("Báo thức")
                .setContentText("It's time to wake up")
                .setSmallIcon(R.drawable.ic_camera)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        notificationManager.notify(200, notificationBuilder.build());

    }
}
