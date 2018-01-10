package de.fhbi.mobappproj.carlogger.DataClasses;

import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by Johannes on 16.12.2017.
 */

public class FuelEntryList extends EntryListSuper<FuelEntry>{


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




    public double getConsumtion(){
        double consumption = 0;
        int counter = 0;

        ArrayList<Double> conList = new ArrayList<Double>();

        Collections.sort(allEntries);

        int tempIndex = -1;

        for(FuelEntry entry : allEntries){
            if(entry.isFull()){
                tempIndex = allEntries.indexOf(entry);
                if(allEntries.size()-1 > tempIndex){
                    if(allEntries.get(tempIndex+1).isFull()){
                        double diffKM = allEntries.get(tempIndex+1).getKm() - allEntries.get(tempIndex).getKm();
                        double diffLitre = allEntries.get(tempIndex + 1).getAmount();

                        if(diffKM > 0) {
                            consumption += diffLitre * (100 / diffKM);
                            counter++;
                        }

                    }
                }
            }
        }
        if(counter > 0){
            consumption = consumption / counter;
        }

        return consumption;
    }


}
