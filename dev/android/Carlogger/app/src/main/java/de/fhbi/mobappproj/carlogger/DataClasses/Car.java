package de.fhbi.mobappproj.carlogger.DataClasses;

import com.google.firebase.database.Exclude;

import java.util.Calendar;

import de.fhbi.mobappproj.carlogger.dataAccess.DataAccess;
import de.fhbi.mobappproj.carlogger.dataAccess.FirebaseAccess;

/**
 * Created by martin on 18.01.18.
 */

public class Car {

    private String hsntsn;
    private String name;
    private long timeKey;
    private String key;
    private Integer allcars_id = null;
    private DataAccess dataAccess = null;

    public Car() {
        timeKey = Calendar.getInstance().getTimeInMillis();
        CarList.getInstance().getCars().add(this);
        dataAccess = FirebaseAccess.getInstance();
    }

    public void removeCar(){
        CarList.getInstance().getCars().remove(this);
    }

    public long getTimeKey() {
        return timeKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void pushToFirebase(){
        String path = String.format(DataAccess.CARS_PATH, dataAccess.getUid(), "");
        dataAccess.push(path, this);
    }

    public void update(){
        String path = String.format(DataAccess.CARS_PATH, dataAccess.getUid(), this.getKey());
        dataAccess.update(path, this);
    }

    public void remove(){
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
        return this.hsntsn == other.hsntsn;
//        return this.getKey() == other.getKey();
    }

    @Override
    public int hashCode() {
        return (int) this.getKey().hashCode();
    }

    @Override
    public String toString(){
        return this.getName();
    }

    public Integer getAllcars_id() {
        return allcars_id;
    }

    public void setAllcars_id(Integer allcars_id) {
        this.allcars_id = allcars_id;
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
