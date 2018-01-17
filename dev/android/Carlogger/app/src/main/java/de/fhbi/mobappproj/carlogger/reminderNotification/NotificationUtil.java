package de.fhbi.mobappproj.carlogger.reminderNotification;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


import java.text.SimpleDateFormat;
import java.util.Calendar;

import de.fhbi.mobappproj.carlogger.DataClasses.entry.ReminderEntry;
import de.fhbi.mobappproj.carlogger.R;
import de.fhbi.mobappproj.carlogger.activities.AddActivities.ReminderAddActivity;
import de.fhbi.mobappproj.carlogger.activities.MainActivity;

/**
 * Created by Johannes on 17.01.2018.
 */
public class NotificationUtil {



    public static void createNotification(Context context, ReminderEntry reminder) {


        Intent viewIntent = new Intent(context, MainActivity.class);
        viewIntent.putExtra("id", reminder.hashCode());
        PendingIntent pending = PendingIntent.getActivity(context, reminder.hashCode(), viewIntent, PendingIntent.FLAG_UPDATE_CURRENT);



        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd. MMM yyyy HH:mm");

        Notification.Builder builder = new Notification.Builder(context)
                .setSmallIcon(R.drawable.ic_action_calendar)
                .setContentTitle(reminder.getDescription())
                .setContentText(dateFormat.format(reminder.getDateTime().getTime()))
                .setContentIntent(pending);

        long[] pattern = {0, 300, 0};
        builder.setVibrate(pattern);


        builder.setPriority(Notification.PRIORITY_HIGH);


        String CHANNEL_ID = "ReminderChannel";
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, "Carlogger", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);
            builder.setChannelId(CHANNEL_ID);
        }
        notificationManager.notify(reminder.hashCode(), builder.build());


    }

    public static void cancelNotification(Context context, int notificationId) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(notificationId);
    }
}
