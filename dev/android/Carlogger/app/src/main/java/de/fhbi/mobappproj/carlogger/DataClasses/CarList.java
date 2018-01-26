package de.fhbi.mobappproj.carlogger.DataClasses;

import java.util.ArrayList;

import de.fhbi.mobappproj.carlogger.dataAccess.DataAccess;
import de.fhbi.mobappproj.carlogger.dataAccess.FirebaseAccess;

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
        DataAccess dataAccess = FirebaseAccess.getInstance();
        String path = String.format(DataAccess.CARS_PATH, dataAccess.getUid(), "");

        dataAccess.getAll(path, this.cars, Car.class);
    }


    public ArrayList<Car> getCars() {
        return cars;
    }

    public boolean getAllEntriesFromFirebase() {

        return false;
    }


}
