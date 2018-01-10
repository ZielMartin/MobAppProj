package de.fhbi.mobappproj.carlogger.DataClasses;

import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

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

        //TODO - fill me

        return false;
    }




}
