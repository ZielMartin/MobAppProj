package de.fhbi.mobappproj.carlogger.DataClasses;

/**
 * Created by martin on 18.01.18.
 */

public class Car {

    private String key;
    private String name;

    public Car() {
        CarList.getInstance().getCars().add(this);
    }

    public void removeCar(){
        CarList.getInstance().getCars().remove(this);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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
        //return this.key == other.key;
        return this.getKey() == other.getKey();
    }

    @Override
    public int hashCode() {
        return (int) getKey().hashCode();
    }

    @Override
    public String toString(){
        return this.getName();
    }
}
