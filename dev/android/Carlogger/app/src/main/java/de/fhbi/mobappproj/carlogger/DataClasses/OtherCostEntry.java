package de.fhbi.mobappproj.carlogger.DataClasses;

import android.support.annotation.NonNull;

/**
 * Created by Johannes on 25.12.2017.
 */

public class OtherCostEntry extends EntrySuper {

    private String description;
    private double cost;

    public OtherCostEntry(){
        super();
        OtherCostEntryList.getInstance().addEntry(this);
    }

    @Override
    protected void pushToFirebase() {
        //TODO fill me
    }

    @Override
    public void removeEntry(int index) {
        removeFromFirebase();
        OtherCostEntryList.getInstance().removeEntry(index);

    }

    @Override
    protected void removeFromFirebase() {
        //TODO fill me
    }

    @Override
    public void push() {
        pushToFirebase();
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public int compareTo(@NonNull EntrySuper entrySuper) {
        long thisTime = this.createTimeCalendar.getTimeInMillis();
        long anotherTime = entrySuper.createTimeCalendar.getTimeInMillis();
        return (thisTime<anotherTime ? -1 : (thisTime==anotherTime ? 0 : 1));
    }

}
