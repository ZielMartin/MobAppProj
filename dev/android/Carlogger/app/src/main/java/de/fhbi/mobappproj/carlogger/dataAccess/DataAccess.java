package de.fhbi.mobappproj.carlogger.dataAccess;

/**
 * Created by martin on 08.01.18.
 */

public interface DataAccess {
    void update(String path, Object object);
    void push(String path, Object object);
    void delete(String path, Object object);
}
