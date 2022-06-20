package com.example.notifications;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button commonNotification, extendedNotification, actionNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        commonNotification = findViewById(R.id.common_notification);
        extendedNotification = findViewById(R.id.extended_notification);
        actionNotification = findViewById(R.id.notification_with_action);

        commonNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commonNotification();
            }
        });

        extendedNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                extendedNotification();
            }
        });

        actionNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionNotification();
            }
        });
    }

    private void commonNotification() {
        Intent intent = new Intent(this, Result.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background);

        String channel_id = "1";
        NotificationChannel notificationChannel = new NotificationChannel(channel_id
                , "common notification"
                , NotificationManager.IMPORTANCE_LOW);

        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.BLUE);

        Notification notification = new NotificationCompat.Builder(this, channel_id)
                .setContentTitle("New notification")
                .setContentText("Hello, i am a common notification")
                .setTicker("Hello world!")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setLargeIcon(icon)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(notificationChannel);
        notificationManager.notify(0, notification);
    }

    private void extendedNotification() {
        Intent intent = new Intent(this, Result.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background);

        String[] strings = new String[3];
        strings[0] = "first";
        strings[1] = "second";
        strings[2] = "third";

        String channel_id = "2";
        NotificationChannel notificationChannel = new NotificationChannel(channel_id
                , "extended notification"
                , NotificationManager.IMPORTANCE_LOW);

        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.BLUE);

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.setBigContentTitle("message position");
        for (String str : strings) {
            inboxStyle.addLine(str);
        }

        Notification notification = new NotificationCompat.Builder(this, channel_id)
                .setContentTitle("New notification")
                .setContentText("Hello, i am a extended notification")
                .setTicker("Hello world!")
                .setStyle(inboxStyle)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setLargeIcon(icon)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(notificationChannel);
        notificationManager.notify(0, notification);
    }

    private void actionNotification() {
        Intent intent = new Intent(this, Result.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background);

        Intent actionIntent = new Intent(this, Result.class);
        PendingIntent pendingActionIntent = PendingIntent.getActivity(this, 0, actionIntent, PendingIntent.FLAG_IMMUTABLE);

        String channel_id = "3";
        NotificationChannel notificationChannel = new NotificationChannel(channel_id
                , "notification with action"
                , NotificationManager.IMPORTANCE_LOW);

        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.BLUE);

        Notification notification = new Notification.Builder(this, channel_id)
                .setContentTitle("New notification")
                .setContentText("Hello, i am a notification with action")
                .setTicker("Hello world!")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setLargeIcon(icon)
                .setContentIntent(pendingIntent)
                .addAction(R.drawable.ic_launcher_background, "Action", pendingActionIntent)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(notificationChannel);
        notificationManager.notify(0, notification);
    }
}