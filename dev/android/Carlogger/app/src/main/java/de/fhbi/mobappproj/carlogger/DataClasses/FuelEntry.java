package de.fhbi.mobappproj.carlogger.DataClasses;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Calendar;

/**
 * Created by Johannes on 16.12.2017.
 */

public class FuelEntry extends EntrySuper implements Parcelable {



    private double amount;
    private double costPerLitre;
    private double km;
    private boolean full;


    public FuelEntry() {
        super();
        FuelEntryList.getInstance().addEntry(this);
        amount = 0;
        costPerLitre = 0;

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





    @Override
    protected void pushToFirebase() {
        //TODO fill me
    }


    @Override
    public void removeEntry(int index) {
        removeFromFirebase();
        FuelEntryList.getInstance().removeEntry(index);
    }

    @Override
    protected void removeFromFirebase() {
        //TODO fill me
    }


    @Override
    public void push() {
        if(amount != 0
                && costPerLitre != 0){
            pushToFirebase();
        }

    }

    protected FuelEntry(Parcel in) {
        amount = in.readDouble();
        costPerLitre = in.readDouble();
        km = in.readDouble();
        full = in.readByte() != 0x00;
        createTimeCalendar = (Calendar) in.readValue(Calendar.class.getClassLoader());
        autoEntry = (AutoEntryDates.AutoEntry) in.readValue(AutoEntryDates.AutoEntry.class.getClassLoader());
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

    @Override
    public int compareTo(@NonNull EntrySuper entrySuper) {
        long thisTime = this.createTimeCalendar.getTimeInMillis();
        long anotherTime = entrySuper.createTimeCalendar.getTimeInMillis();
        return (thisTime<anotherTime ? -1 : (thisTime==anotherTime ? 0 : 1));
    }
}