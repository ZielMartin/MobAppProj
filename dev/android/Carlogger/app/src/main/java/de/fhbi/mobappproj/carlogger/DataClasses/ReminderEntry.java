package de.fhbi.mobappproj.carlogger.DataClasses;

import android.util.Log;

import java.util.Date;

/**
 * Created by Johannes on 15.12.2017.
 */

public class ReminderEntry extends EntrySuper{


    private String description;
    private Date dateTime;
    private boolean pushNotification;
    private int hoursNotification;


    public void setDescription(String description) {
        this.description = description;
    }

    public void setDateTime(Date dateTime) {
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

    public ReminderEntry() {
        ReminderEntryList.getInstance().addEntry(this);
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
