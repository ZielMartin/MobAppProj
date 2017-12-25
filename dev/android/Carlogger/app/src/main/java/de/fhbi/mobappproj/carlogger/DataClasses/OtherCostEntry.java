package de.fhbi.mobappproj.carlogger.DataClasses;

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
    public void push() {
        pushToFirebase();
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setAutoEntry(AutoEntryDates.AutoEntry autoEntry) {
        this.autoEntry = autoEntry;
    }
}
