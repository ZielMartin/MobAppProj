package de.fhbi.mobappproj.carlogger.DataClasses.list;

import android.widget.ArrayAdapter;

import java.util.ArrayList;

import de.fhbi.mobappproj.carlogger.DataClasses.MyList;
import de.fhbi.mobappproj.carlogger.DataClasses.entry.Car;
import de.fhbi.mobappproj.carlogger.dataAccess.DataAccess;
import de.fhbi.mobappproj.carlogger.dataAccess.FirebaseAccess;

/**
 * Created by Johannes on 23.01.2018.
 */

public class CarList implements MyList<Car> {

    private ArrayList<Car> cars;
    private ArrayAdapter adapterToUpdate = null;

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

        DataAccess dataAccess = FirebaseAccess.getInstance();
        String path = String.format(DataAccess.CARS_PATH, dataAccess.getUid(), "");

        dataAccess.getAll(path, this, Car.class);

        return false;
    }


    @Override
    public void add(Car item) {
        if (!this.cars.contains(item)) {
            this.cars.add(item);
            if (adapterToUpdate != null)
                adapterToUpdate.notifyDataSetChanged();
        }
    }

    public void setAdapterToUpdate(ArrayAdapter adapterToUpdate) {
        this.adapterToUpdate = adapterToUpdate;
    }
}
