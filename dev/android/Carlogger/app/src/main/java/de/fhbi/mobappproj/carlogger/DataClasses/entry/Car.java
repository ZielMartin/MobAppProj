package de.fhbi.mobappproj.carlogger.DataClasses.entry;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.firebase.database.Exclude;
import com.google.gson.Gson;

import java.util.Calendar;

import de.fhbi.mobappproj.carlogger.DataClasses.list.CarList;
import de.fhbi.mobappproj.carlogger.MyApplication;
import de.fhbi.mobappproj.carlogger.dataAccess.DataAccess;
import de.fhbi.mobappproj.carlogger.dataAccess.FirebaseAccess;
import de.fhbi.mobappproj.carlogger.dataAccess.entryAccess.CarAccess;

/**
 * Created by martin on 18.01.18.
 */

public class Car {

    private String hsntsn;
    private String name;
    private String key;

    public Car() {

    }

    public void removeCar() {
        CarList.getInstance().getCars().remove(this);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void pushToFirebase() {
        DataAccess dataAccess = FirebaseAccess.getInstance();
        String path = String.format(DataAccess.CARS_PATH, dataAccess.getUid(), "");
        dataAccess.push(path, this);
    }

    public void update() {
        DataAccess dataAccess = FirebaseAccess.getInstance();
        String path = String.format(DataAccess.CARS_PATH, dataAccess.getUid(), this.getKey());
        dataAccess.update(path, this);
    }

    public void remove() {
        DataAccess dataAccess = FirebaseAccess.getInstance();
        String path = String.format(DataAccess.CARS_PATH, dataAccess.getUid(), this.getKey());
        dataAccess.delete(path);


        CarList.getInstance().getCars().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Car)) {
            return false;
        }
        Car other = (Car) o;
        return this.key == other.key;
//        return this.getKey() == other.getKey();
    }

    @Override
    public int hashCode() {
        return (int) this.getKey().hashCode();
    }

    @Override
    public String toString() {
        return this.getName();
    }

    public String getHsntsn() {
        return hsntsn;
    }

    public void setHsntsn(String hsntsn) {
        this.hsntsn = hsntsn;
    }

    @Exclude
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
