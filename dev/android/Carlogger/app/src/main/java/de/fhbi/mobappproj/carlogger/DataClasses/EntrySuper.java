package de.fhbi.mobappproj.carlogger.DataClasses;

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

public abstract class EntrySuper implements Comparable<EntrySuper> {



    protected Calendar createTimeCalendar;
    protected AutoEntryDates.AutoEntry autoEntry;

    public EntrySuper(){
        createTimeCalendar = Calendar.getInstance();
    }


    protected abstract void pushToFirebase();

    public abstract void removeEntry(int index);

    protected abstract void removeFromFirebase();

    protected abstract void updateChangesOnFirebase();

    /**
     * check all variables and call pushToFirebase()
     */
    public abstract void push();



    public void setAutoEntry(AutoEntryDates.AutoEntry autoEntry) {
        this.autoEntry = autoEntry;
    }

    public Calendar getCreateTimeCalendar() {

        return createTimeCalendar;
    }

    /**
     * edit createTimeCalendar if this entry is for editing an other
     * @param createTimeCalendar
     */
    public void setCreateTimeCalendar(Calendar createTimeCalendar) {
        this.createTimeCalendar = createTimeCalendar;
    }

    public AutoEntryDates.AutoEntry getAutoEntry() {
        return autoEntry;
    }


}
