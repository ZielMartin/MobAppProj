package de.fhbi.mobappproj.carlogger.DataClasses;

import java.io.File;

/**
 * Created by Johannes on 21.12.2017.
 */

public class RepairEntry extends EntrySuper {

    private double cost;
    private double partCost;
    private double laborCost;
    private String description;
    private File bill;



    public RepairEntry() {
        super();
        RepairEntryList.getInstance().addEntry(this);
    }


    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setAutoEntry(AutoEntryDates.AutoEntry autoEntry) {
        this.autoEntry = autoEntry;
    }

    public void setPartCost(double partCost) {
        this.partCost = partCost;
    }

    public void setLaborCost(double laborCost) {
        this.laborCost = laborCost;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBill(File bill) {
        this.bill = bill;
    }




    @Override
    protected void pushToFirebase() {
        //TODO fill me
    }


    @Override
    public void push() {
        pushToFirebase();
    }
}
