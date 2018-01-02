package de.fhbi.mobappproj.carlogger.DataClasses;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.io.File;
import java.util.Calendar;

/**
 * Created by Johannes on 21.12.2017.
 */

public class RepairEntry extends EntrySuper implements Parcelable {

    // Calendar createTimeCalendar; - in SuperClass
    // AutoEntryDates.AutoEntry autoEntry; - in SuperClass
    private String type;
    private double partCost;
    private double laborCost;
    private String description;
    private File bill;

    //workaround for not parcelable File. Save filePath and open file again after CREATOR call
    private String filePathForParcel;


    public RepairEntry() {
        super();
        RepairEntryList.getInstance().addEntry(this);
    }

    public void setType(String type){ this.type = type; }


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
        if(bill != null){
            this.filePathForParcel = bill.getAbsolutePath();
        }
    }

    public String getType() {
        return type;
    }

    public double getPartCost() {
        return partCost;
    }

    public double getLaborCost() {
        return laborCost;
    }

    public String getDescription() {
        return description;
    }

    public File getBill() {
        return bill;
    }



    @Override
    public void removeEntry(int index) {
        removeFromFirebase();
        RepairEntryList.getInstance().removeEntry(index);

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


    @Override
    public int compareTo(@NonNull EntrySuper entrySuper) {
        long thisTime = this.createTimeCalendar.getTimeInMillis();
        long anotherTime = entrySuper.createTimeCalendar.getTimeInMillis();
        return (thisTime<anotherTime ? -1 : (thisTime==anotherTime ? 0 : 1));
    }

    protected RepairEntry(Parcel in) {
        type = in.readString();
        partCost = in.readDouble();
        laborCost = in.readDouble();
        description = in.readString();
        filePathForParcel = in.readString();
        createTimeCalendar = (Calendar) in.readValue(Calendar.class.getClassLoader());
        autoEntry = (AutoEntryDates.AutoEntry) in.readValue(AutoEntryDates.AutoEntry.class.getClassLoader());
        if(filePathForParcel != null){
            bill = new File(filePathForParcel);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeDouble(partCost);
        dest.writeDouble(laborCost);
        dest.writeString(description);
        dest.writeString(filePathForParcel);
        dest.writeValue(createTimeCalendar);
        dest.writeValue(autoEntry);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<RepairEntry> CREATOR = new Parcelable.Creator<RepairEntry>() {
        @Override
        public RepairEntry createFromParcel(Parcel in) {
            return new RepairEntry(in);
        }

        @Override
        public RepairEntry[] newArray(int size) {
            return new RepairEntry[size];
        }
    };
}