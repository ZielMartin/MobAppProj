package de.fhbi.mobappproj.carlogger.DataClasses;

import android.support.annotation.NonNull;
import android.util.Log;

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

    public enum EntryType {FUELENTRY, REPAIRENTRY, REMINDERENTRY, OTHERCOSTENTRY}




    protected EntryType entryType;
    protected Calendar createTimeCalendar;
    protected AutoEntryDates.AutoEntry autoEntry;
    protected double cost;

    public EntrySuper(){
        createTimeCalendar = Calendar.getInstance();
    }


    protected abstract void pushToFirebase();


    protected abstract void removeFromFirebase();

    protected abstract void updateChangesOnFirebase();

    /**
     * check all variables and call pushToFirebase()
     */
    public abstract void push();



    public void setAutoEntry(AutoEntryDates.AutoEntry autoEntry) {
        this.autoEntry = autoEntry;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Calendar getCreateTimeCalendar() {

        return createTimeCalendar;
    }

    /**
     * createTimeCalendar is setted in the Constructor
     * edit createTimeCalendar if this entry is for editing an other
     * or for test usage
     * @param createTimeCalendar
     */
    public void editCreateTimeCalendar(Calendar createTimeCalendar) {
        this.createTimeCalendar = createTimeCalendar;
    }


    public void removeEntry(){
        removeFromFirebase();
        AllEntryList.getInstance().getAllEntries().remove(this);
        switch(this.entryType){
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


    public void updateEntry(){
        updateChangesOnFirebase();
        int index = AllEntryList.getInstance().getAllEntries().indexOf(this);
        if(index >= 0 ){
            AllEntryList.getInstance().getAllEntries().set(index, this);
            switch(this.entryType){
                case FUELENTRY:
                    index = FuelEntryList.getInstance().getAllEntries().indexOf(this);
                    if(index >= 0){
                        FuelEntryList.getInstance().getAllEntries().set(index, (FuelEntry) this);
                    }
                case REPAIRENTRY:
                    index = RepairEntryList.getInstance().getAllEntries().indexOf(this);
                    if(index >= 0){
                        RepairEntryList.getInstance().getAllEntries().set(index, (RepairEntry) this);
                    }
                case REMINDERENTRY:
                    index = ReminderEntryList.getInstance().getAllEntries().indexOf(this);
                    if(index >= 0){
                        ReminderEntryList.getInstance().getAllEntries().set(index, (ReminderEntry) this);
                    }
                case OTHERCOSTENTRY:
                    index = OtherCostEntryList.getInstance().getAllEntries().indexOf(this);
                    if(index >= 0){
                        OtherCostEntryList.getInstance().getAllEntries().set(index, (OtherCostEntry) this);
                    }
            }
        }
    }

    public AutoEntryDates.AutoEntry getAutoEntry() {
        return autoEntry;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public int compareTo(@NonNull EntrySuper entrySuper) {
        long thisTime = this.createTimeCalendar.getTimeInMillis();
        long anotherTime = entrySuper.createTimeCalendar.getTimeInMillis();
        return (thisTime<anotherTime ? -1 : (thisTime==anotherTime ? 0 : 1));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EntrySuper)) {
            return false;
        }
        EntrySuper other = (EntrySuper) o;
        return createTimeCalendar.getTimeInMillis() == other.createTimeCalendar.getTimeInMillis();
    }

    @Override
    public int hashCode() {
        return (int) createTimeCalendar.getTimeInMillis();
    }


}
