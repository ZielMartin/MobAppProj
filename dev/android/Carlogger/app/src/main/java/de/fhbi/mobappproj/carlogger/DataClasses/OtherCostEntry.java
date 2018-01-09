package de.fhbi.mobappproj.carlogger.DataClasses;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Calendar;

/**
 * Created by Johannes on 25.12.2017.
 */

public class OtherCostEntry extends EntrySuper implements Parcelable {


    // Calendar createTimeCalendar; - in SuperClass
    // AutoEntryDates.AutoEntry autoEntry; - in SuperClass
    private String description;
    private double cost;


    public OtherCostEntry(){
        super();
        entryType = entryType.OTHERCOSTENTRY;
        OtherCostEntryList.getInstance().addEntry(this);
    }

    public String getDescription() {
        return description;
    }

    public double getCost() {
        return cost;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }



    protected OtherCostEntry(Parcel in) {
        description = in.readString();
        cost = in.readDouble();
        createTimeCalendar = (Calendar) in.readValue(Calendar.class.getClassLoader());
        autoEntry = (AutoEntryDates.AutoEntry) in.readValue(AutoEntryDates.AutoEntry.class.getClassLoader());
        entryType = (EntryType) in.readSerializable();
    }

    @Override
    public int describeContents() {
        return 0;
    }



    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(description);
        dest.writeDouble(cost);
        dest.writeValue(createTimeCalendar);
        dest.writeValue(autoEntry);
        dest.writeSerializable(entryType);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<OtherCostEntry> CREATOR = new Parcelable.Creator<OtherCostEntry>() {
        @Override
        public OtherCostEntry createFromParcel(Parcel in) {
            return new OtherCostEntry(in);
        }

        @Override
        public OtherCostEntry[] newArray(int size) {
            return new OtherCostEntry[size];
        }
    };
}