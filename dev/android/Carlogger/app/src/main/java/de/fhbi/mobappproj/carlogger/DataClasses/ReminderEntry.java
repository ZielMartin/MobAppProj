package de.fhbi.mobappproj.carlogger.DataClasses;

import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Johannes on 15.12.2017.
 */

public class ReminderEntry extends EntrySuper{


    private String description;
    private Calendar dateTime;
    private boolean pushNotification;
    private int hoursNotification;


    public ReminderEntry() {
        ReminderEntryList.getInstance().addEntry(this);
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public void setDateTime(Calendar dateTime) {
        this.dateTime = dateTime;
    }

    public void setPushNotification(boolean pushNotification) {
        this.pushNotification = pushNotification;
    }

    /**
     *
     * @param hoursNotification - how many hours before the Push-Notification should be made
     */
    public void setHoursNotification(int hoursNotification) {
        this.hoursNotification = hoursNotification;
    }



    @Override
    public void pushToFirebase() {
        //TODO fill me
    }

    @Override
    public void push() {
        if(description != null && dateTime != null){
            pushToFirebase();
        }
    }


}
