package com.example.notificationlistener;

import android.app.Application;
import android.app.Notification;
import android.content.Intent;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MyNotificationListenerService extends NotificationListenerService {

    private static final String TAG = "MyNotificationListener";
    StringViewModel stringViewModel;
    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("NotificationListenerService created");
        stringViewModel = new ViewModelProvider(this).get(StringViewModel.class);

    }
    @Override
    public void onDestroy() {
        super.onDestroy();

        // Restart the service if it was killed
        Intent intent = new Intent(this, NotificationService.class);
        startService(intent);
    }
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
//        String packageName = sbn.getPackageName();
//        String tickerText = sbn.getNotification().tickerText != null ?
//                sbn.getNotification().tickerText.toString() : "";
//        Log.d(TAG, "New notification posted: " + packageName + " - " + tickerText);


        // Handle the new notification
        Notification notification = sbn.getNotification();
        if (notification != null) {
//            // Extract the relevant information from the notification
//            String packageName = sbn.getPackageName();
//            String title = notification.extras.getString(Notification.EXTRA_TITLE);
//            String text = notification.extras.getString(Notification.EXTRA_TEXT);
//
//            // Log the notification details
//            Log.d(TAG, "Received notification from " + packageName + ": " + title + " - " + text);


            String packageName = sbn.getPackageName();
            String tickerText = sbn.getNotification().tickerText != null ?
                    sbn.getNotification().tickerText.toString() : "";
            Log.d(TAG, "New notification posted: " + packageName + " - " + tickerText);
        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        String packageName = sbn.getPackageName();
        Log.d(TAG, "Notification removed: " + packageName);
    }
}
