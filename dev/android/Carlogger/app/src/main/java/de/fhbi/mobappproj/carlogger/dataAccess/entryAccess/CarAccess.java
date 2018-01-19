package de.fhbi.mobappproj.carlogger.dataAccess.entryAccess;

import de.fhbi.mobappproj.carlogger.DataClasses.Car;

/**
 * Created by martin on 18.01.18.
 */

public class CarAccess {
    private static CarAccess instance = null;
    private Car currentCar;

    private CarAccess() {
    }

    public static CarAccess getInstance() {
        if (instance == null) instance = new CarAccess();
        return instance;
    }

    public Car getCurrentCar() {
        return currentCar;
    }
}
