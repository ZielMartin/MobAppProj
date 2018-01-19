package de.fhbi.mobappproj.carlogger.reminderNotification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

import de.fhbi.mobappproj.carlogger.DataClasses.entry.ReminderEntry;
import de.fhbi.mobappproj.carlogger.DataClasses.list.ReminderEntryList;

/**
 * Created by Johannes on 17.01.2018.
 */

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        ReminderEntryList.getInstance().getAllEntriesFromFirebase();




        for (ReminderEntry reminder : ReminderEntryList.getInstance().getAllEntries()) {
            if(reminder.isPushNotification()) {
                Intent alarmIntent = new Intent(context, AlarmReceiver.class);
                Calendar calendar = (Calendar) reminder.getDateTime().clone();
                calendar.add(Calendar.HOUR, -reminder.getHoursNotification());
                AlarmUtil.setAlarm(context, reminder, calendar);
            }
        }
    }
}