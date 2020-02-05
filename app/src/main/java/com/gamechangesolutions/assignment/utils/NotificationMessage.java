package com.gamechangesolutions.assignment.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.gamechangesolutions.assignment.R;

import java.util.Objects;

/**
 * utility class to show notification throughout the app
 */
public class NotificationMessage {

    private static final String TAG = NotificationMessage.class.getSimpleName();

    /**
     * @param context context
     * @param task    text to show in notification
     */
    public static void showNotification(Context context, String task) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Log.i(TAG, "showNotification: task =" + task);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new
                    NotificationChannel(Constants.CHANNEL_ID, Constants.CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            Objects.requireNonNull(manager).createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, Constants.CHANNEL_ID)
                .setContentTitle(task)
                .setSmallIcon(R.mipmap.ic_launcher);
        Objects.requireNonNull(manager).notify(Constants.NOTIFICATION_ID, builder.build());
    }

    /**
     * @param context context
     * @param task    show Indeterminate progress with message
     */
    public static void showNotificationProgressIndeterminate(Context context, String task) {
        showNotificationProgress(context, task);
    }

    /**
     * @param context context
     * @param task    show determinate progress with message
     */

    private static void showNotificationProgress(Context context, String task) {

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Log.i(TAG, "showNotification: task =" + task);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new
                    NotificationChannel(Constants.CHANNEL_ID, Constants.CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            Objects.requireNonNull(manager).createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, Constants.CHANNEL_ID)
                .setContentTitle(task)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setProgress(0, 0, true);
        Objects.requireNonNull(manager).notify(Constants.NOTIFICATION_ID, builder.build());
    }

}
