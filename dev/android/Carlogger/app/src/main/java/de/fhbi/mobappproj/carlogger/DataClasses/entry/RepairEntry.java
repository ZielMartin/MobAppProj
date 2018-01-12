package de.fhbi.mobappproj.carlogger.DataClasses.entry;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;
import java.util.Calendar;

import de.fhbi.mobappproj.carlogger.DataClasses.AutoEntryDates;
import de.fhbi.mobappproj.carlogger.DataClasses.list.RepairEntryList;

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
        entryType = entryType.REPAIRENTRY;
        RepairEntryList.getInstance().addEntry(this);
    }

    public RepairEntry(RepairEntry re) {
        super();
        entryType = entryType.REPAIRENTRY;
        RepairEntryList.getInstance().addEntry(this);

        autoEntry = re.autoEntry;
        type = re.type;
        partCost = re.partCost;
        laborCost = re.laborCost;
        description = re.description;
        bill = re.bill;
    }

    public void setType(String type){ this.type = type; }


    public void setPartCost(double partCost) {
        this.partCost = partCost;
        cost += partCost;
    }

    public void setLaborCost(double laborCost) {
        this.laborCost = laborCost;
        cost += laborCost;
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
        entryType = (EntryType) in.readSerializable();
        lastEntry = in.readByte() != 0x00;
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
        dest.writeSerializable(entryType);
        dest.writeByte((byte) (lastEntry ? 0x01 : 0x00));
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