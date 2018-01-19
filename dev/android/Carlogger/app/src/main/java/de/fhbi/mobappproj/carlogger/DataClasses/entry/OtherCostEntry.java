package de.fhbi.mobappproj.carlogger.DataClasses.entry;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

import de.fhbi.mobappproj.carlogger.DataClasses.AutoEntryDates;
import de.fhbi.mobappproj.carlogger.DataClasses.list.OtherCostEntryList;

/**
 * Created by Johannes on 25.12.2017.
 */

public class OtherCostEntry extends EntrySuper implements Parcelable {


    // Calendar createTimeCalendar; - in SuperClass
    // AutoEntryDates.AutoEntry autoEntry; - in SuperClass
    private String description;


    public OtherCostEntry(){
        super();
        entryType = entryType.OTHERCOSTENTRY;
        OtherCostEntryList.getInstance().addEntry(this);
    }

    public OtherCostEntry(OtherCostEntry oe){
        super();
        entryType = entryType.OTHERCOSTENTRY;
        OtherCostEntryList.getInstance().addEntry(this);

        autoEntry = oe.autoEntry;
        description = oe.description;
    }

    public String getDescription() {
        return description;
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
    public void updateChangesOnFirebase() {
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




    protected OtherCostEntry(Parcel in) {
        description = in.readString();
        cost = in.readDouble();
        createTimeCalendar = in.readLong();
        autoEntry = (AutoEntryDates.AutoEntry) in.readValue(AutoEntryDates.AutoEntry.class.getClassLoader());
        entryType = (EntryType) in.readSerializable();
        lastEntry = in.readByte() != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }



    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(description);
        dest.writeDouble(cost);
        dest.writeLong(createTimeCalendar);
        dest.writeValue(autoEntry);
        dest.writeSerializable(entryType);
        dest.writeByte((byte) (lastEntry ? 0x01 : 0x00));
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