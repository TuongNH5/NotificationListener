package com.example.notificationlistener;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;

import androidx.annotation.Nullable;

public class NotificationService extends Service {

    private static final int NOTIFICATION_ID = 1;
    private static final long CHECK_INTERVAL = 15 * 60 * 1000; // 15 minutes

    private NotificationManager mNotificationManager;
    private NotificationListenerService mNotificationListenerService;
    private PendingIntent mCheckIntent;

    @Override
    public void onCreate() {
        super.onCreate();

        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Start the NotificationListenerService as a foreground service
        mNotificationListenerService = new MyNotificationListenerService();
        Intent intent = new Intent(this, MyNotificationListenerService.class);
        startForeground(NOTIFICATION_ID, NotificationUtil.createNotification(this, "Listening for notifications", intent));

        // Schedule an alarm to check if the service is still running
        Intent checkIntent = new Intent(this, NotificationService.class);
        mCheckIntent = PendingIntent.getService(this, 0, checkIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + CHECK_INTERVAL, CHECK_INTERVAL, mCheckIntent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // Cancel the check alarm
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(mCheckIntent);

        // Stop the NotificationListenerService
        if (mNotificationListenerService != null) {
            mNotificationListenerService.onDestroy();
        }

        // Remove the foreground notification
        mNotificationManager.cancel(NOTIFICATION_ID);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

