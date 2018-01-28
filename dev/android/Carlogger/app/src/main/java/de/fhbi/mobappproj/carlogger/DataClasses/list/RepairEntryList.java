package de.fhbi.mobappproj.carlogger.DataClasses.list;

import android.util.Log;

import de.fhbi.mobappproj.carlogger.DataClasses.entry.Car;
import de.fhbi.mobappproj.carlogger.DataClasses.entry.RepairEntry;
import de.fhbi.mobappproj.carlogger.dataAccess.DataAccess;
import de.fhbi.mobappproj.carlogger.dataAccess.FirebaseAccess;
import de.fhbi.mobappproj.carlogger.dataAccess.entryAccess.CarAccess;

/**
 * Created by Johannes on 21.12.2017.
 */

public class RepairEntryList extends EntryListSuper<RepairEntry> {

    private static final String TAG = RepairEntryList.class.getSimpleName();

    //Singleton Instance
    private static final RepairEntryList ourInstance = new RepairEntryList();

    public static RepairEntryList getInstance() {
        return ourInstance;
    }

    private RepairEntryList() {
        super();
    }

    @Override
    public boolean getAllEntriesFromFirebase() {
        //create RepairEntry instances from Firebase-Result. Entries will be auto-inserted to RepairEntryList on Constructor-call

        Car currentCar = CarAccess.getInstance().getCurrentCar();

        if (currentCar != null) {
            DataAccess dataAccess = FirebaseAccess.getInstance();
            String path = String.format(DataAccess.REPAIRENTRY_PATH, dataAccess.getUid(), currentCar.getKey(), "");
            dataAccess.getAll(path, this, RepairEntry.class);
        }

        return false;
    }
}
