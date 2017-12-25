package de.fhbi.mobappproj.carlogger.DataClasses;

import java.util.ArrayList;

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
    public ArrayList<OtherCostEntry> getAllEntriesFromFirebase() {
        //TODO fill me
        return null;
    }
}
