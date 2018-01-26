package de.fhbi.mobappproj.carlogger.dataAccess;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by martin on 08.01.18.
 */

public interface DataAccess {
    public static final String USER_PATH = "user/%s";
    public static final String CARS_PATH = USER_PATH + "/cars/%s";
    public static final String ENTRY_PATH = CARS_PATH + "/entries/%s";


    void update(String path, Object object);
    void push(String path, Object object);
    void delete(String path);

    <T> void getAll(String path, List list, Type typeOfT);

    String getUid();
}
