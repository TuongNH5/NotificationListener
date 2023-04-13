package com.example.notificationlistener;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Check if the app has permission to read notifications
        if (!isNotificationServiceEnabled()) {
            // If not, ask the user to grant permission
            Intent intent = new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);
            startActivity(intent);
            Toast.makeText(this, "Please grant permission to read notifications", Toast.LENGTH_LONG).show();
        }

        // Start the NotificationListenerService
        Intent intent = new Intent(this, MyNotificationListenerService.class);
        startService(intent);
    }
    private boolean isNotificationServiceEnabled() {
        ComponentName cn = new ComponentName(this, MyNotificationListenerService.class);
        String flat = Settings.Secure.getString(getContentResolver(), "enabled_notification_listeners");
        return flat != null && flat.contains(cn.flattenToString());
    }
}