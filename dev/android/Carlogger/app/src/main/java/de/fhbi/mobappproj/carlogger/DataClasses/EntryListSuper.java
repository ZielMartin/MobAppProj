package de.fhbi.mobappproj.carlogger.DataClasses;

import java.util.ArrayList;

/**Each Class that represents an Entry-list has to use these Methods
 * sub-classes should be singleton and give their EntryType to this class - example: ReminderEntryList
 *
 * one list instance for one session, get data from firebase at the beginning of the session and write data on each update of the list
 *
 * Created by Johannes on 15.12.2017.
 */


public abstract class EntryListSuper<EntryType extends EntrySuper> {

    //TODO Methods that have same code, save on firebase etc..


    protected ArrayList<EntryType> allEntries;



    public EntryListSuper(){
        allEntries = new ArrayList<EntryType>();
    }


    //different code in each data-class
    public abstract boolean getAllEntriesFromFirebase();



    public void addEntry(EntryType entry){
        allEntries.add(entry);
        AllEntryList.getInstance().addEntry(entry);
    }



    public ArrayList<EntryType> getAllEntries(){
        return allEntries;
    }




}
