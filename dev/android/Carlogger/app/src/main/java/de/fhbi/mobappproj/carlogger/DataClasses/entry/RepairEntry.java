package de.fhbi.mobappproj.carlogger.DataClasses.entry;

import android.os.Parcel;
import android.os.Parcelable;

import de.fhbi.mobappproj.carlogger.DataClasses.AutoEntryDates;
import de.fhbi.mobappproj.carlogger.dataAccess.DataAccess;
import de.fhbi.mobappproj.carlogger.dataAccess.FirebaseAccess;
import de.fhbi.mobappproj.carlogger.dataAccess.entryAccess.CarAccess;

/**
 * Created by Johannes on 21.12.2017.
 */

public class RepairEntry extends EntrySuper implements Parcelable {

    private static final String TAG = "RepairEntry";
    // Calendar createTime; - in SuperClass
    // AutoEntryDates.AutoEntry autoEntry; - in SuperClass
    private String type;
    private double partCost;
    private double laborCost;
    private String description;
    private String billPath;

    private DataAccess dataAccess = FirebaseAccess.getInstance();


    public RepairEntry() {
        super();
        entryType = entryType.REPAIRENTRY;
    }

    public RepairEntry(RepairEntry re) {
        super();
        entryType = entryType.REPAIRENTRY;

        autoEntry = re.autoEntry;
        type = re.type;
        partCost = re.partCost;
        laborCost = re.laborCost;
        description = re.description;
        billPath = re.billPath;
    }

    public void setType(String type) {
        this.type = type;
    }


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

    public void setBillPath(String billPath) {
        this.billPath = billPath;

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

    public String getBillPath() {
        return billPath;
    }



    @Override
    protected void pushToFirebase() {
        Car currentCar = CarAccess.getInstance().getCurrentCar();
        String path = String.format(DataAccess.REPAIRENTRY_PATH, dataAccess.getUid(), currentCar.getKey(), "");
        dataAccess.push(path, this);
    }

    @Override
    protected void removeFromFirebase() {
        Car currentCar = CarAccess.getInstance().getCurrentCar();
        String path = String.format(DataAccess.REPAIRENTRY_PATH, dataAccess.getUid(), currentCar.getKey(), "");
        dataAccess.delete(path);
    }

    @Override
    public void updateChangesOnFirebase() {
        Car currentCar = CarAccess.getInstance().getCurrentCar();
        String path = String.format(DataAccess.REPAIRENTRY_PATH, dataAccess.getUid(), currentCar.getKey(), "");
        dataAccess.update(path, this);
    }


    @Override
    public void push() {
        pushToFirebase();
    }


    protected RepairEntry(Parcel in) {
        key = in.readString();
        type = in.readString();
        partCost = in.readDouble();
        laborCost = in.readDouble();
        description = in.readString();
        billPath = in.readString();
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
        dest.writeString(key);
        dest.writeString(type);
        dest.writeDouble(partCost);
        dest.writeDouble(laborCost);
        dest.writeString(description);
        dest.writeString(billPath);
        dest.writeLong(createTime);
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