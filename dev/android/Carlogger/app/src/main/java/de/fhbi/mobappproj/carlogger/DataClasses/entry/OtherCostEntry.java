package de.fhbi.mobappproj.carlogger.DataClasses.entry;

import android.os.Parcel;
import android.os.Parcelable;

import de.fhbi.mobappproj.carlogger.DataClasses.AutoEntryDates;
import de.fhbi.mobappproj.carlogger.DataClasses.list.OtherCostEntryList;
import de.fhbi.mobappproj.carlogger.dataAccess.DataAccess;
import de.fhbi.mobappproj.carlogger.dataAccess.FirebaseAccess;
import de.fhbi.mobappproj.carlogger.dataAccess.entryAccess.CarAccess;

/**
 * Created by Johannes on 25.12.2017.
 */

public class OtherCostEntry extends EntrySuper implements Parcelable {

    private DataAccess dataAccess = FirebaseAccess.getInstance();

    // Calendar createTime; - in SuperClass
    // AutoEntryDates.AutoEntry autoEntry; - in SuperClass
    private String description;


    public OtherCostEntry(){
        super();
        entryType = entryType.OTHERCOSTENTRY;
    }

    public OtherCostEntry(OtherCostEntry oe){
        super();
        entryType = entryType.OTHERCOSTENTRY;

        autoEntry = oe.autoEntry;
        description = oe.description;
    }

    public String getDescription() {
        return description;
    }


    @Override
    protected void pushToFirebase() {
        Car currentCar = CarAccess.getInstance().getCurrentCar();
        String path = String.format(DataAccess.OTHERENTRY_PATH, dataAccess.getUid(), currentCar.getKey(), "");
        dataAccess.push(path, this);
    }

    @Override
    protected void removeFromFirebase() {
        Car currentCar = CarAccess.getInstance().getCurrentCar();
        String path = String.format(DataAccess.OTHERENTRY_PATH, dataAccess.getUid(), currentCar.getKey(), "");
        dataAccess.delete(path);
    }

    @Override
    public void updateChangesOnFirebase() {
        Car currentCar = CarAccess.getInstance().getCurrentCar();
        String path = String.format(DataAccess.OTHERENTRY_PATH, dataAccess.getUid(), currentCar.getKey(), "");
        dataAccess.update(path, this);
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
        createTime = in.readLong();
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
        dest.writeLong(createTime);
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