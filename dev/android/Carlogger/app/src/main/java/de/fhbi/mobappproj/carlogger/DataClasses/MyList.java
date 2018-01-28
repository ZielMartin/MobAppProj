package de.fhbi.mobappproj.carlogger.DataClasses;

/**
 * Created by martin on 26.01.18.
 */

public interface MyList<ItemType> {
    void add(ItemType item);
    void notifyAdapter();
}
