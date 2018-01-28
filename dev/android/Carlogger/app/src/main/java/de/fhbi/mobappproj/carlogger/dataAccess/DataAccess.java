package de.fhbi.mobappproj.carlogger.dataAccess;

import java.lang.reflect.Type;

import de.fhbi.mobappproj.carlogger.DataClasses.MyList;

/**
 * Created by martin on 08.01.18.
 */

public interface DataAccess {
    String USER_PATH = "user/%s";
    String CARS_PATH = USER_PATH + "/cars/%s";
    String FUELENTRY_PATH = CARS_PATH + "/fuel_entries/%s";
    String REPAIRENTRY_PATH = CARS_PATH + "/repair_entries/%s";
    String OTHERENTRY_PATH = CARS_PATH + "/other_entries/%s";
    String REMINDERENTRY_PATH = USER_PATH + "/reminders/%s";


    void update(String path, Object object);
    void push(String path, Object object);
    void delete(String path);

    <T> void getAll(String path, MyList<T> list, Type typeOfT);

    String getUid();
}
