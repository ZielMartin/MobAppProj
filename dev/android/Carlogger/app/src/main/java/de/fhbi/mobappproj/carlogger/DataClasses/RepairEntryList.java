package de.fhbi.mobappproj.carlogger.DataClasses;

import java.util.ArrayList;

/**
 * Created by Johannes on 21.12.2017.
 */

public class RepairEntryList extends EntryListSuper<RepairEntry> {

    //Singleton Instance
    private static final RepairEntryList ourInstance = new RepairEntryList();

    public static RepairEntryList getInstance() {
        return ourInstance;
    }

    private RepairEntryList(){
        super();
    }

    @Override
    public ArrayList<RepairEntry> getAllEntriesFromFirebase() {
        //TODO fill me
        return null;
    }
}
