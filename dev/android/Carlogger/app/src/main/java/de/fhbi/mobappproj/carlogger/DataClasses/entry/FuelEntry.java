package de.fhbi.mobappproj.carlogger.DataClasses.entry;

import android.os.Parcel;
import android.os.Parcelable;

import de.fhbi.mobappproj.carlogger.DataClasses.AutoEntryDates;
import de.fhbi.mobappproj.carlogger.dataAccess.DataAccess;
import de.fhbi.mobappproj.carlogger.dataAccess.FirebaseAccess;
import de.fhbi.mobappproj.carlogger.dataAccess.entryAccess.CarAccess;

/**
 * Created by Johannes on 16.12.2017.
 */

public class FuelEntry extends EntrySuper implements Parcelable {


    // Calendar createTime; - in SuperClass
    // AutoEntryDates.AutoEntry autoEntry; - in SuperClass
    private double amount;
    private double costPerLitre;
    private double km;
    private boolean full;

    private DataAccess dataAccess = FirebaseAccess.getInstance();


    public FuelEntry() {
        super();
        entryType = entryType.FUELENTRY;
        amount = 0;
        costPerLitre = 0;
    }

    public FuelEntry(FuelEntry fe) {
        super();
        entryType = entryType.FUELENTRY;
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
        if (costPerLitre != 0) {
            cost = amount * costPerLitre;
        }
    }

    public void setCostPerLitre(double costPerLitre) {
        this.costPerLitre = costPerLitre;
        if (amount != 0) {
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
        Car currentCar = CarAccess.getInstance().getCurrentCar();
        String path = String.format(DataAccess.FUELENTRY_PATH, dataAccess.getUid(), currentCar.getKey(), "");
        dataAccess.push(path, this);
    }

    @Override
    protected void removeFromFirebase() {
        Car currentCar = CarAccess.getInstance().getCurrentCar();
        String path = String.format(DataAccess.FUELENTRY_PATH, dataAccess.getUid(), currentCar.getKey(), "");
        dataAccess.delete(path);
    }

    @Override
    public void updateChangesOnFirebase() {
        Car currentCar = CarAccess.getInstance().getCurrentCar();
        String path = String.format(DataAccess.FUELENTRY_PATH, dataAccess.getUid(), currentCar.getKey(), "");
        dataAccess.update(path, this);
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
        createTime = in.readLong();
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
        dest.writeLong(createTime);
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