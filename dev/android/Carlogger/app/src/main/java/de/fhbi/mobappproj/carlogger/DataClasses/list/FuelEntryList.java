package de.fhbi.mobappproj.carlogger.DataClasses.list;

import java.util.ArrayList;
import java.util.Collections;

import de.fhbi.mobappproj.carlogger.DataClasses.entry.FuelEntry;

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
                //first Entry with Full
                tempIndex = allEntries.indexOf(entry);
                if(allEntries.size()-1 > tempIndex){
                    //next Entry is Full
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
