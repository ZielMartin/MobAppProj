package de.fhbi.mobappproj.carlogger.DataClasses;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Johannes on 09.01.2018.
 */

public class AllEntryList extends EntryListSuper {

    private FuelEntryList fuelEntries;
    private ReminderEntryList reminderEntries;
    private RepairEntryList repairEntries;
    private OtherCostEntryList otherEntries;


    //Singleton Instance
    private static final AllEntryList ourInstance = new AllEntryList();


    public static AllEntryList getInstance() {
        return ourInstance;
    }

    //Singleton Constructor
    private AllEntryList(){
        super();
        fuelEntries = FuelEntryList.getInstance();
        reminderEntries = ReminderEntryList.getInstance();
        repairEntries = RepairEntryList.getInstance();
        otherEntries = OtherCostEntryList.getInstance();
    }


    @Override
    public void addEntry(EntrySuper entry){
        allEntries.add(entry);
    }

    @Override
    public boolean getAllEntriesFromFirebase() {
        fuelEntries.getAllEntriesFromFirebase();
        reminderEntries.getAllEntriesFromFirebase();
        repairEntries.getAllEntriesFromFirebase();
        otherEntries.getAllEntriesFromFirebase();
        return true;
    }
}
