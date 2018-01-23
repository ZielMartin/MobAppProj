package de.fhbi.mobappproj.carlogger.DataClasses;

import java.util.ArrayList;

/**
 * Created by Johannes on 23.01.2018.
 */

public class CarList {

    private ArrayList<Car> cars;

    //Singleton Instance
    private static final CarList ourInstance = new CarList();

    public static CarList getInstance() {
        return ourInstance;
    }

    private CarList() {
        cars = new ArrayList<Car>();
    }



    public ArrayList<Car> getCars() {
        return cars;
    }

    public boolean getAllEntriesFromFirebase() {

        //TODO - fill me

        return false;
    }


}
