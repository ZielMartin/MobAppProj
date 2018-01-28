package de.fhbi.mobappproj.carlogger.DataClasses.list;

import java.util.ArrayList;
import java.util.Calendar;

import de.fhbi.mobappproj.carlogger.DataClasses.AutoEntryDates;
import de.fhbi.mobappproj.carlogger.DataClasses.entry.EntrySuper;
import de.fhbi.mobappproj.carlogger.DataClasses.entry.FuelEntry;
import de.fhbi.mobappproj.carlogger.DataClasses.entry.OtherCostEntry;
import de.fhbi.mobappproj.carlogger.DataClasses.entry.RepairEntry;

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
        clear();
        fuelEntries.getAllEntriesFromFirebase();
        reminderEntries.getAllEntriesFromFirebase();
        repairEntries.getAllEntriesFromFirebase();
        otherEntries.getAllEntriesFromFirebase();
        setAutoEntries();
        if(adapterToUpdate != null){
            adapterToUpdate.notifyDataSetChanged();
        }
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
        //list of Entries with attribute last == true
        ArrayList<EntrySuper> lastAutoEntries = new ArrayList<>();
        //fill list
        for (Object o : allEntries) {
            EntrySuper entry = (EntrySuper) o;
            if (entry.isLastEntry() && entry.getAutoEntry() != null) {
                lastAutoEntries.add(entry);
            }
        }

        //loop through all Entries with last == true
        for (EntrySuper entry : lastAutoEntries) {
            ArrayList<Calendar> calList = AutoEntryDates.getList(entry.getCreateTimeCalendar(), entry.getAutoEntry());

            if(calList.size() > 0){
                // not anymore last
                entry.setLastEntry(false);
                entry.updateChangesOnFirebase();
            }

            //create Entry for each Date
            for (int i = 0; i < calList.size(); i++) {

                switch (entry.getEntryType()) {
                    case FUELENTRY:
                        FuelEntry fe = (FuelEntry) entry;
                        FuelEntry newFe = new FuelEntry(fe);
                        FuelEntryList.getInstance().addEntry(newFe);
                        newFe.setLastEntry(i == calList.size() - 1);
                        newFe.editCreateTimeCalendar(calList.get(i));
                        newFe.push();
                        break;

                    case REPAIRENTRY:
                        RepairEntry re = (RepairEntry) entry;
                        RepairEntry newRe = new RepairEntry(re);
                        RepairEntryList.getInstance().addEntry(newRe);
                        newRe.setLastEntry(i == calList.size() - 1);
                        newRe.editCreateTimeCalendar(calList.get(i));
                        newRe.push();
                        break;


                    case OTHERCOSTENTRY:
                        OtherCostEntry oe = (OtherCostEntry) entry;
                        OtherCostEntry newOe = new OtherCostEntry(oe);
                        OtherCostEntryList.getInstance().addEntry(newOe);
                        newOe.setLastEntry(i == calList.size() - 1);
                        newOe.editCreateTimeCalendar(calList.get(i));
                        newOe.push();
                        break;

                }

            }
        }
    }

}
