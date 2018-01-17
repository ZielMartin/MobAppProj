package de.fhbi.mobappproj.carlogger.reminderNotification;



import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Parcel;
import android.util.Log;


import java.util.Calendar;

import de.fhbi.mobappproj.carlogger.DataClasses.entry.ReminderEntry;

/**
 * Created by Johannes on 17.01.2018.
 */
public class AlarmUtil {

    public static void setAlarm(Context context, ReminderEntry reminder, Calendar calendar) {

        Parcel parcel = Parcel.obtain();
        reminder.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);

        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("reminder", parcel.marshall());



        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, reminder.hashCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT);



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
    }

    public static void cancelAlarm(Context context, ReminderEntry reminder) {
        if(reminder != null) {
            Intent intent = new Intent(context, AlarmReceiver.class);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, reminder.hashCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.cancel(pendingIntent);
        }
    }


}