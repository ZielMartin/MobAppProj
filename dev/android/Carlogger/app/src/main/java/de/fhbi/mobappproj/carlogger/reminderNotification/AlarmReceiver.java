package de.fhbi.mobappproj.carlogger.reminderNotification;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.util.Calendar;

import de.fhbi.mobappproj.carlogger.DataClasses.entry.ReminderEntry;
import de.fhbi.mobappproj.carlogger.DataClasses.list.ReminderEntryList;

/**
 * Created by Johannes on 17.01.2018.
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        byte[] byteArrayExtra = intent.getByteArrayExtra("reminder");
        Parcel parcel = Parcel.obtain();
        parcel.unmarshall(byteArrayExtra, 0, byteArrayExtra.length);
        parcel.setDataPosition(0);
        ReminderEntry reminder = ReminderEntry.CREATOR.createFromParcel(parcel);


        NotificationUtil.createNotification(context, reminder);


        Intent updateIntent = new Intent("BROADCAST_REFRESH");
        //updateIntent.putExtra("reminder",reminder);
        LocalBroadcastManager.getInstance(context).sendBroadcast(updateIntent);


    }
}