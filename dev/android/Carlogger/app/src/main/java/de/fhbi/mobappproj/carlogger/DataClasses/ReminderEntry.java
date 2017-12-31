package de.fhbi.mobappproj.carlogger.DataClasses;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Calendar;

/**
 * Created by Johannes on 15.12.2017.
 */

public class ReminderEntry extends EntrySuper implements Parcelable {


    private String description;
    private Calendar dateTime;
    private boolean pushNotification;
    private int hoursNotification;



    public ReminderEntry() {
        ReminderEntryList.getInstance().addEntry(this);
    }



    public String getDescription() {
        return description;
    }

    public Calendar getDateTime() {
        return dateTime;
    }

    public boolean isPushNotification() {
        return pushNotification;
    }

    public int getHoursNotification() {
        return hoursNotification;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDateTime(Calendar dateTime) {
        this.dateTime = dateTime;
    }

    public void setPushNotification(boolean pushNotification) {
        this.pushNotification = pushNotification;
    }

    /**
     *
     * @param hoursNotification - how many hours before the Push-Notification should be made
     */
    public void setHoursNotification(int hoursNotification) {
        this.hoursNotification = hoursNotification;
    }



    @Override
    public void pushToFirebase() {
        //TODO fill me
    }

    @Override
    public void removeEntry(int index) {
        removeFromFirebase();
        ReminderEntryList.getInstance().removeEntry(index);
    }

    @Override
    protected void removeFromFirebase() {
        //TODO fill me
    }

    @Override
    public void push() {
        if(description != null && dateTime != null){
            pushToFirebase();
        }
    }




    protected ReminderEntry(Parcel in) {
        description = in.readString();
        dateTime = (Calendar) in.readValue(Calendar.class.getClassLoader());
        pushNotification = in.readByte() != 0x00;
        hoursNotification = in.readInt();
        createTimeCalendar = (Calendar) in.readValue(Calendar.class.getClassLoader());
        autoEntry = (AutoEntryDates.AutoEntry) in.readValue(AutoEntryDates.AutoEntry.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(description);
        dest.writeValue(dateTime);
        dest.writeByte((byte) (pushNotification ? 0x01 : 0x00));
        dest.writeInt(hoursNotification);
        dest.writeValue(createTimeCalendar);
        dest.writeValue(autoEntry);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ReminderEntry> CREATOR = new Parcelable.Creator<ReminderEntry>() {
        @Override
        public ReminderEntry createFromParcel(Parcel in) {
            return new ReminderEntry(in);
        }

        @Override
        public ReminderEntry[] newArray(int size) {
            return new ReminderEntry[size];
        }
    };

    @Override
    public int compareTo(@NonNull EntrySuper entrySuper) {
        long thisTime = this.createTimeCalendar.getTimeInMillis();
        long anotherTime = entrySuper.createTimeCalendar.getTimeInMillis();
        return (thisTime<anotherTime ? -1 : (thisTime==anotherTime ? 0 : 1));
    }
}

