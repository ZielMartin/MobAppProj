package de.fhbi.mobappproj.carlogger.DataClasses;

import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
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
    private AllEntryList() {
        super();
        fuelEntries = FuelEntryList.getInstance();
        reminderEntries = ReminderEntryList.getInstance();
        repairEntries = RepairEntryList.getInstance();
        otherEntries = OtherCostEntryList.getInstance();
    }


    @Override
    public void addEntry(EntrySuper entry) {
        allEntries.add(entry);
    }

    @Override
    public boolean getAllEntriesFromFirebase() {
        fuelEntries.getAllEntriesFromFirebase();
        reminderEntries.getAllEntriesFromFirebase();
        repairEntries.getAllEntriesFromFirebase();
        otherEntries.getAllEntriesFromFirebase();
        setAutoEntries();
        return true;
    }

    @Override
    public void clear() {
        fuelEntries.clear();
        repairEntries.clear();
        reminderEntries.clear();
        otherEntries.clear();
        allEntries.clear();
    }



    public void setAutoEntries() {
        ArrayList<EntrySuper> lastAutoEntries = new ArrayList<>();

        for (Object o : allEntries) {
            EntrySuper entry = (EntrySuper) o;
            if (entry.isLastEntry()) {
                lastAutoEntries.add(entry);
            }
        }

        for (EntrySuper entry : lastAutoEntries) {
            entry.setLastEntry(false);
            ArrayList<Calendar> calList = AutoEntryDates.getList(entry.createTimeCalendar, entry.autoEntry);

            if(calList.size() > 0){
                entry.setLastEntry(false);
                entry.updateChangesOnFirebase();
            }

            for (int i = 0; i < calList.size(); i++) {

                switch (entry.entryType) {
                    case FUELENTRY:
                        FuelEntry fe = (FuelEntry) entry;
                        FuelEntry newFe = new FuelEntry(fe);
                        newFe.setLastEntry(i == calList.size() - 1);
                        newFe.editCreateTimeCalendar(calList.get(i));
                        newFe.push();
                        break;

                    case REPAIRENTRY:
                        RepairEntry re = (RepairEntry) entry;
                        RepairEntry newRe = new RepairEntry(re);
                        newRe.setLastEntry(i == calList.size() - 1);
                        newRe.editCreateTimeCalendar(calList.get(i));
                        newRe.push();
                        break;


                    case OTHERCOSTENTRY:
                        OtherCostEntry oe = (OtherCostEntry) entry;
                        OtherCostEntry newOe = new OtherCostEntry(oe);
                        newOe.setLastEntry(i == calList.size() - 1);
                        newOe.editCreateTimeCalendar(calList.get(i));
                        newOe.push();
                        break;

                }

            }
        }


    }

}
