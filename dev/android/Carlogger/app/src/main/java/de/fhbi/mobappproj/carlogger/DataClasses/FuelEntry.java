package de.fhbi.mobappproj.carlogger.DataClasses;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Johannes on 16.12.2017.
 */

public class FuelEntry extends EntrySuper {


    private double quantity;
    private double costPerLitre;
    private double km;
    private boolean full;



    public FuelEntry() {
        super();
        FuelEntryList.getInstance().addEntry(this);
        quantity = 0;
        costPerLitre = 0;

    }





    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public void setCostPerLitre(double costPerLitre) {
        this.costPerLitre = costPerLitre;
    }

    public void setKm(double km) {
        this.km = km;
    }

    public void setFull(boolean full) {
        this.full = full;
    }

    public void setAutoEntry(AutoEntryDates.AutoEntry autoEntry) {
        this.autoEntry = autoEntry;
    }




    @Override
    protected void pushToFirebase() {
        //TODO fill me
    }


    @Override
    public void push() {
            if(quantity != 0
                    && costPerLitre != 0){
                pushToFirebase();
            }

    }
}
