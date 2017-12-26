package de.fhbi.mobappproj.carlogger.DataClasses;

import android.os.Parcelable;

import java.util.Calendar;

/**
 * all sub-classes have to call their EntryList-singleton-instance in the contructor and add themself to the list
 * and pushToFirebase() in push(), after checking the variables are setted.
 *
 * variables should be setted with setters to avoid long parameter-lists
 *
 * example: ReminderEntry
 * usage example: ReminderAddActivity
 *
 * Created by Johannes on 15.12.2017.
 */

public abstract class EntrySuper {

    protected Calendar currCalendar;
    protected AutoEntryDates.AutoEntry autoEntry;

    public EntrySuper(){
        currCalendar = Calendar.getInstance();
    }


    protected abstract void pushToFirebase();

    public abstract void removeEntry(int index);

    protected abstract void removeFromFirebase();

    /**
     * check all variables and call pushToFirebase()
     */
    public abstract void push();



    public void setAutoEntry(AutoEntryDates.AutoEntry autoEntry) {
        this.autoEntry = autoEntry;
    }

    public Calendar getCurrCalendar() {

        return currCalendar;
    }

    public AutoEntryDates.AutoEntry getAutoEntry() {
        return autoEntry;
    }
}
