package de.fhbi.mobappproj.carlogger.DataClasses;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Calendar;

/**
 * Created by Johannes on 16.12.2017.
 */

public class FuelEntry extends EntrySuper implements Parcelable {



    // Calendar createTimeCalendar; - in SuperClass
    // AutoEntryDates.AutoEntry autoEntry; - in SuperClass
    private double amount;
    private double costPerLitre;
    private double km;
    private boolean full;


    public FuelEntry() {
        super();
        entryType = entryType.FUELENTRY;
        FuelEntryList.getInstance().addEntry(this);
        amount = 0;
        costPerLitre = 0;
    }

    public FuelEntry(FuelEntry fe) {
        super();
        entryType = entryType.FUELENTRY;
        FuelEntryList.getInstance().addEntry(this);
        autoEntry = fe.autoEntry;
        amount = fe.amount;
        costPerLitre = fe.costPerLitre;
    }


    public double getAmount() {
        return amount;
    }

    public double getCostPerLitre() {
        return costPerLitre;
    }

    public double getKm() {
        return km;
    }

    public boolean isFull() {
        return full;
    }



    public void setAmount(double amount) {
        this.amount = amount;
        if(costPerLitre != 0){
            cost = amount * costPerLitre;
        }
    }

    public void setCostPerLitre(double costPerLitre) {
        this.costPerLitre = costPerLitre;
        if(amount != 0){
            cost = amount * costPerLitre;
        }
    }

    public void setKm(double km) {
        this.km = km;
    }

    public void setFull(boolean full) {
        this.full = full;
    }




    @Override
    protected void pushToFirebase() {
        //called when entry was added - add to firebase
        //TODO fill me
    }

    @Override
    protected void removeFromFirebase() {
        //called when entry was deleted - delete on firebase to
        //TODO fill me
    }

    @Override
    protected void updateChangesOnFirebase() {
        //called when entry was modified - save changes on firebase
        //TODO - fill me
    }


    @Override
    public void push() {
        pushToFirebase();
    }

    protected FuelEntry(Parcel in) {
        amount = in.readDouble();
        costPerLitre = in.readDouble();
        km = in.readDouble();
        full = in.readByte() != 0x00;
        createTimeCalendar = (Calendar) in.readValue(Calendar.class.getClassLoader());
        autoEntry = (AutoEntryDates.AutoEntry) in.readValue(AutoEntryDates.AutoEntry.class.getClassLoader());
        entryType = (EntryType) in.readSerializable();
        cost = in.readDouble();
        lastEntry = in.readByte() != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(amount);
        dest.writeDouble(costPerLitre);
        dest.writeDouble(km);
        dest.writeByte((byte) (full ? 0x01 : 0x00));
        dest.writeValue(createTimeCalendar);
        dest.writeValue(autoEntry);
        dest.writeSerializable(entryType);
        dest.writeDouble(cost);
        dest.writeByte((byte) (lastEntry ? 0x01 : 0x00));
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<FuelEntry> CREATOR = new Parcelable.Creator<FuelEntry>() {
        @Override
        public FuelEntry createFromParcel(Parcel in) {
            return new FuelEntry(in);
        }

        @Override
        public FuelEntry[] newArray(int size) {
            return new FuelEntry[size];
        }
    };


}