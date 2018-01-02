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
    public boolean getAllEntriesFromFirebase() {
        //create RepairEntry instances from Firebase-Result. Entries will be auto-inserted to RepairEntryList on Constructor-call

        //TODO - fill me

        return false;
    }
}
