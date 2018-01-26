package de.fhbi.mobappproj.carlogger.dataAccess.entryAccess;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.fhbi.mobappproj.carlogger.DataClasses.Car;

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
    }
}
