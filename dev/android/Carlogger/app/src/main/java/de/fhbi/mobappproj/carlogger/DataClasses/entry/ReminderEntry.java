package de.fhbi.mobappproj.carlogger.DataClasses.entry;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;

import java.util.Calendar;

import de.fhbi.mobappproj.carlogger.DataClasses.AutoEntryDates;
import de.fhbi.mobappproj.carlogger.DataClasses.list.ReminderEntryList;
import de.fhbi.mobappproj.carlogger.activities.MainActivity;
import de.fhbi.mobappproj.carlogger.reminderNotification.AlarmReceiver;
import de.fhbi.mobappproj.carlogger.reminderNotification.AlarmUtil;

/**
 * Created by Johannes on 15.12.2017.
 */

public class ReminderEntry extends EntrySuper implements Parcelable {


    // Calendar createTimeCalendar; - in SuperClass
    // AutoEntryDates.AutoEntry autoEntry; - in SuperClass
    private String description;
    private long dateTime;
    private boolean pushNotification;
    private int hoursNotification;



    public ReminderEntry() {
        super();
        entryType = entryType.REMINDERENTRY;
        ReminderEntryList.getInstance().addEntry(this);
    }




    public String getDescription() {
        return description;
    }

    @Exclude
    public Calendar getDateTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dateTime);
        return calendar;
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

    @Exclude
    public void setDateTime(Calendar dateTime) {
        this.dateTime = dateTime.getTimeInMillis();
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
        if(description != null && dateTime != 0){
            pushToFirebase();
        }
    }




    protected ReminderEntry(Parcel in) {
        description = in.readString();
        dateTime = in.readLong();
        pushNotification = in.readByte() != 0x00;
        hoursNotification = in.readInt();
        createTimeCalendar = in.readLong();
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
        dest.writeString(description);
        dest.writeLong(dateTime);
        dest.writeByte((byte) (pushNotification ? 0x01 : 0x00));
        dest.writeInt(hoursNotification);
        dest.writeLong(createTimeCalendar);
        dest.writeValue(autoEntry);
        dest.writeSerializable(entryType);
        dest.writeDouble(cost);
        dest.writeByte((byte) (lastEntry ? 0x01 : 0x00));
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


}

