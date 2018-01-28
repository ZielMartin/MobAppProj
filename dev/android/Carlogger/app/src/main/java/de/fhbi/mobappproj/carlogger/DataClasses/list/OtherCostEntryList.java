package de.fhbi.mobappproj.carlogger.DataClasses.list;

import de.fhbi.mobappproj.carlogger.DataClasses.entry.Car;
import de.fhbi.mobappproj.carlogger.DataClasses.entry.FuelEntry;
import de.fhbi.mobappproj.carlogger.DataClasses.entry.OtherCostEntry;
import de.fhbi.mobappproj.carlogger.dataAccess.DataAccess;
import de.fhbi.mobappproj.carlogger.dataAccess.FirebaseAccess;
import de.fhbi.mobappproj.carlogger.dataAccess.entryAccess.CarAccess;

/**
 * Created by Johannes on 25.12.2017.
 */

public class OtherCostEntryList extends EntryListSuper<OtherCostEntry> {

    //Singleton Instance
    private static final OtherCostEntryList ourInstance = new OtherCostEntryList();

    public static OtherCostEntryList getInstance() {
        return ourInstance;
    }

    private OtherCostEntryList(){
        super();
    }

    @Override
    public boolean getAllEntriesFromFirebase() {
        //create OtherCostEntry instances from Firebase-Result. Entries will be auto-inserted to OtherCostEntryList on Constructor-call

        Car currentCar = CarAccess.getInstance().getCurrentCar();

        if (currentCar != null) {
            DataAccess dataAccess = FirebaseAccess.getInstance();
            String path = String.format(DataAccess.OTHERENTRY_PATH, dataAccess.getUid(), currentCar.getKey(), "");
            dataAccess.getAll(path, this, OtherCostEntry.class);
        }

        return false;
    }




}
