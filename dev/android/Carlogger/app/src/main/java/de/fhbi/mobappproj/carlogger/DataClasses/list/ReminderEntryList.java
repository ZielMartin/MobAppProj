package de.fhbi.mobappproj.carlogger.DataClasses.list;

import de.fhbi.mobappproj.carlogger.DataClasses.entry.ReminderEntry;

/**
 * Singleton Pattern
 * Created by Johannes on 15.12.2017.
 */

public class ReminderEntryList extends EntryListSuper<ReminderEntry> {

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

        //TODO - fill me

        return false;
    }





}
