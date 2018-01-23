package de.fhbi.mobappproj.carlogger.DataClasses;

import java.util.Calendar;

/**
 * Created by martin on 18.01.18.
 */

public class Car {

    private String HSNTSN;
    private String name;
    private long timeKey;

    public Car() {
        timeKey = Calendar.getInstance().getTimeInMillis();
        CarList.getInstance().getCars().add(this);
    }

    public void removeCar(){
        CarList.getInstance().getCars().remove(this);
    }

    public String getHSNTSN() {
        return HSNTSN;
    }

    public void setHSNTSN(String HSNTSN) {
        this.HSNTSN = HSNTSN;
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
        //TODO
    }

    public void update(){
        //TODO firebase update
    }

    public void remove(){
        //TODO firebase remove
        CarList.getInstance().getCars().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Car)) {
            return false;
        }
        Car other = (Car) o;
        //return this.HSNTSN == other.HSNTSN;
        return this.getHSNTSN() == other.getHSNTSN();
    }

    @Override
    public int hashCode() {
        return (int) getHSNTSN().hashCode();
    }

    @Override
    public String toString(){
        return this.getName();
    }
}
