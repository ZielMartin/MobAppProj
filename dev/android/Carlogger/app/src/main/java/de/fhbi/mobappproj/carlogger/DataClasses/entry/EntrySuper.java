package de.fhbi.mobappproj.carlogger.DataClasses.entry;

import android.support.annotation.NonNull;

import com.google.firebase.database.Exclude;

import java.util.Calendar;

import de.fhbi.mobappproj.carlogger.DataClasses.AutoEntryDates;
import de.fhbi.mobappproj.carlogger.DataClasses.list.AllEntryList;
import de.fhbi.mobappproj.carlogger.DataClasses.list.FuelEntryList;
import de.fhbi.mobappproj.carlogger.DataClasses.list.OtherCostEntryList;
import de.fhbi.mobappproj.carlogger.DataClasses.list.ReminderEntryList;
import de.fhbi.mobappproj.carlogger.DataClasses.list.RepairEntryList;

/**
 * all sub-classes have to call their EntryList-singleton-instance in the contructor and add themself to the list
 * and pushToFirebase() in push(), after checking the variables are setted.
 * <p>
 * variables should be setted with setters to avoid long parameter-lists
 * <p>
 * example: ReminderEntry
 * usage example: ReminderAddActivity
 * <p>
 * Created by Johannes on 15.12.2017.
 */

public abstract class EntrySuper implements Comparable<EntrySuper> {

    public enum EntryType {FUELENTRY, REPAIRENTRY, REMINDERENTRY, OTHERCOSTENTRY}


    @Exclude
    protected String key;

    protected EntryType entryType;
    protected long createTime;
    protected AutoEntryDates.AutoEntry autoEntry;
    protected double cost;
    protected boolean lastEntry;


    public EntrySuper() {
        lastEntry = true;
        createTime = Calendar.getInstance().getTimeInMillis();
    }


    protected abstract void pushToFirebase();

    protected abstract void removeFromFirebase();

    public abstract void updateChangesOnFirebase();

    /**
     * check all variables and call pushToFirebase()
     */
    public abstract void push();


    /**
     * createTime is setted in the Constructor
     * edit createTime if this entry is for editing an other
     * or for test usage
     *
     * @param createTimeCalendar
     */
    public void editCreateTimeCalendar(Calendar createTimeCalendar) {
        this.createTime = createTimeCalendar.getTimeInMillis();
    }


    public void removeEntry() {
        removeFromFirebase();
        AllEntryList.getInstance().getAllEntries().remove(this);
        switch (this.entryType) {
            case FUELENTRY:
                FuelEntryList.getInstance().getAllEntries().remove(this);
            case REPAIRENTRY:
                RepairEntryList.getInstance().getAllEntries().remove(this);
            case REMINDERENTRY:
                ReminderEntryList.getInstance().getAllEntries().remove(this);
            case OTHERCOSTENTRY:
                OtherCostEntryList.getInstance().getAllEntries().remove(this);
        }
    }


    public void updateEntry() {
        updateChangesOnFirebase();
    }

    public void setAutoEntry(AutoEntryDates.AutoEntry autoEntry) {
        this.autoEntry = autoEntry;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setKey(String key) { this.key = key; }

    @Exclude
    public Calendar getCreateTimeCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(createTime);
        return calendar;
    }

    public long getCreateTime() {
        return createTime;
    }

    public AutoEntryDates.AutoEntry getAutoEntry() {
        return autoEntry;
    }

    public double getCost() {
        return cost;
    }

    public EntryType getEntryType() {
        return entryType;
    }

    @Exclude
    public String getKey() { return this.key; }

    public void setEntryType(EntryType entryType) {
        this.entryType = entryType;
    }

    public void setLastEntry(boolean lastEntry) {
        this.lastEntry = lastEntry;
    }


    public boolean isLastEntry() {

        return lastEntry;
    }

    @Override
    public int compareTo(@NonNull EntrySuper entrySuper) {
        long thisTime = this.createTime;
        long anotherTime = entrySuper.createTime;
        return (thisTime < anotherTime ? -1 : (thisTime == anotherTime ? 0 : 1));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EntrySuper)) {
            return false;
        }
        EntrySuper other = (EntrySuper) o;
        //return this.key == other.key;
        return this.createTime == other.createTime;
    }

    @Override
    public int hashCode() {
        return (int) createTime;
    }


}
