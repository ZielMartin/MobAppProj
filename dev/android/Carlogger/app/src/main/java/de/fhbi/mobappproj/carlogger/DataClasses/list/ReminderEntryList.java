package de.fhbi.mobappproj.carlogger.DataClasses.list;

import de.fhbi.mobappproj.carlogger.DataClasses.entry.Car;
import de.fhbi.mobappproj.carlogger.DataClasses.entry.FuelEntry;
import de.fhbi.mobappproj.carlogger.DataClasses.entry.ReminderEntry;
import de.fhbi.mobappproj.carlogger.dataAccess.DataAccess;
import de.fhbi.mobappproj.carlogger.dataAccess.FirebaseAccess;
import de.fhbi.mobappproj.carlogger.dataAccess.entryAccess.CarAccess;
import de.fhbi.mobappproj.carlogger.listEntryAdapter.ReminderAdapter;

/**
 * Singleton Pattern
 * Created by Johannes on 15.12.2017.
 */

public class ReminderEntryList extends EntryListSuper<ReminderEntry, ReminderAdapter> {

    //Singleton Instance
    private static final ReminderEntryList ourInstance = new ReminderEntryList();

    public static ReminderEntryList getInstance() {
        return ourInstance;
    }

    private ReminderEntryList(){
       super();
    }

    @Override
    public boolean getAllEntriesFromFirebase() {
        //create ReminderEntry instances from Firebase-Result. Entries will be auto-inserted to ReminderEntryList on Constructor-call

        Car currentCar = CarAccess.getInstance().getCurrentCar();

        if (currentCar != null) {
            DataAccess dataAccess = FirebaseAccess.getInstance();
            String path = String.format(DataAccess.REMINDERENTRY_PATH, dataAccess.getUid(), currentCar.getKey(), "");
            dataAccess.getAll(path, this, ReminderEntry.class);
        }


        return false;
    }





}
