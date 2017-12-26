package de.fhbi.mobappproj.carlogger.DataClasses;

import java.io.File;

/**
 * Created by Johannes on 21.12.2017.
 */

public class RepairEntry extends EntrySuper {

    private String type;
    private double cost;
    private double partCost;
    private double laborCost;
    private String description;
    private File bill;



    public RepairEntry() {
        super();
        RepairEntryList.getInstance().addEntry(this);
    }

    public void setType(String type){ this.type = type; }

    public void setCost(double cost) {
        this.cost = cost;
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
    public void removeEntry(int index) {
        removeFromFirebase();
        RepairEntryList.getInstance().removeEntry(index);

    }

    @Override
    protected void removeFromFirebase() {
        //TODO fill me
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
