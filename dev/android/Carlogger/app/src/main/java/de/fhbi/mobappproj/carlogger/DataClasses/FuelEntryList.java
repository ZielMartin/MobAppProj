package de.fhbi.mobappproj.carlogger.DataClasses;

import java.util.ArrayList;

/**
 * Created by Johannes on 16.12.2017.
 */

public class FuelEntryList extends EntryListSuper<FuelEntry> {


    //Singleton Instance
    private static final FuelEntryList ourInstance = new FuelEntryList();


    public static FuelEntryList getInstance() {
        return ourInstance;
    }

    //Singleton Constructor
    private FuelEntryList(){
        super();
    }



    @Override
    public boolean getAllEntriesFromFirebase() {
        //create FuelEntry instances from Firebase-Result. Entries will be auto-inserted to FuelEntryList on Constructor-call

        //TODO - fill me

        return false;
    }


}
