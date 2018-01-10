package de.fhbi.mobappproj.carlogger.DataClasses;

import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

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



    public double getAllCosts(){
        double cost = 0;

        for(EntryType entry : allEntries){
            cost += entry.getCost();
        }

        return cost;
    }

    public double getCostPerMonth(){
        double cost = 0;

        if(!allEntries.isEmpty()) {
            Calendar first = Collections.min(allEntries).getCreateTimeCalendar();
            Calendar today = Calendar.getInstance();

            int diffYear = today.get(Calendar.YEAR) - first.get(Calendar.YEAR);
            int diffMonth = diffYear * 12 + today.get(Calendar.MONTH) - first.get(Calendar.MONTH);

            cost = getAllCosts() / ((diffMonth == 0) ? 1 : diffMonth);
        }
        return cost;
    }

    public double getCostTime(Calendar start, Calendar end) {
        double cost = 0;

        long diff = end.getTimeInMillis() - start.getTimeInMillis();

        Collections.sort(allEntries);

        for(EntryType entry : allEntries){
            if(entry.getCreateTimeCalendar().compareTo(start) >= 0 && entry.getCreateTimeCalendar().compareTo(start) <= diff){
                cost += entry.getCost();
            }
        }

        Log.d("getCostPerMonth", cost+"");
        return cost;
    }




}
