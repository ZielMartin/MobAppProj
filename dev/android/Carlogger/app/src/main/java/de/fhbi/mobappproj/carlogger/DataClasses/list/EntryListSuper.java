package de.fhbi.mobappproj.carlogger.DataClasses.list;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import de.fhbi.mobappproj.carlogger.DataClasses.entry.EntrySuper;
import de.fhbi.mobappproj.carlogger.DataClasses.MyList;
import de.fhbi.mobappproj.carlogger.listEntryAdapter.GenericViewHolder;

/**
 * Each Class that represents an Entry-list has to use these Methods
 * sub-classes should be singleton and give their EntryType to this class - example: ReminderEntryList
 * <p>
 * one list instance for one session, get data from firebase at the beginning of the session and write data on each update of the list
 * <p>
 * Created by Johannes on 15.12.2017.
 */


public abstract class EntryListSuper<EntryType extends EntrySuper, AdapterType extends RecyclerView.Adapter<? extends GenericViewHolder>> implements MyList<EntryType> {


    protected ArrayList<EntryType> allEntries;

    AdapterType adapterToUpdate = null;



    public EntryListSuper() {
        allEntries = new ArrayList<EntryType>();
    }


    //different code in each data-class
    public abstract boolean getAllEntriesFromFirebase();


    public void addEntry(EntryType entry) {
        allEntries.add(entry);
        AllEntryList.getInstance().addEntry(entry);
    }


    public ArrayList<EntryType> getAllEntries() {
        return allEntries;
    }


    public double getAllCosts() {
        double cost = 0;

        for (EntryType entry : allEntries) {
            cost += entry.getCost();
        }

        return cost;
    }

    public double getCostPerMonth(Calendar today) {
        double cost = 0;

        if (!allEntries.isEmpty()) {
            Calendar first = Collections.min(allEntries).getCreateTimeCalendar();


            int diffYear = today.get(Calendar.YEAR) - first.get(Calendar.YEAR);
            int diffMonth = diffYear * 12 + today.get(Calendar.MONTH) - first.get(Calendar.MONTH);

            cost = getAllCosts() / ((diffMonth == 0) ? 1 : diffMonth);
        }
        return cost;
    }

    public double getCostTime(Calendar start, Calendar end) {
        double cost = 0;

        Collections.sort(allEntries);

        for (EntryType entry : allEntries) {
            if (entry.getCreateTimeCalendar().compareTo(start) >= 0 && entry.getCreateTimeCalendar().compareTo(end) <= 0) {
                cost += entry.getCost();
            }
        }

        return cost;
    }


    public void clear() {
        allEntries.clear();
    }

    public void setAdapterToUpdate(AdapterType adapterToUpdate) {
        this.adapterToUpdate = adapterToUpdate;
    }

    @Override
    public void add(EntryType item) {
        if (!this.allEntries.contains(item)) {
            this.addEntry(item);

        }
    }

    @Override
    public void notifyAdapter(){
        if(adapterToUpdate != null){
            adapterToUpdate.notifyDataSetChanged();
        }
    }
}
