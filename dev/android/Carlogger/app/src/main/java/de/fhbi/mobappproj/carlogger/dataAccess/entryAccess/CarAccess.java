package de.fhbi.mobappproj.carlogger.dataAccess.entryAccess;

import de.fhbi.mobappproj.carlogger.DataClasses.entry.Car;
import de.fhbi.mobappproj.carlogger.DataClasses.list.AllEntryList;

/**
 * Created by martin on 18.01.18.
 */

public class CarAccess {
    private static CarAccess instance = null;
    private Car currentCar;

    public CarAccess() {
    }

    public static CarAccess getInstance() {
        if (instance == null) instance = new CarAccess();
        return instance;
    }

    public Car getCurrentCar() {
        return currentCar;
    }

    public void setCurrentCar(Car currentCar) {
        this.currentCar = currentCar;
        AllEntryList.getInstance().getAllEntriesFromFirebase();
    }
}
