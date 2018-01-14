package de.fhbi.mobappproj.carlogger.dataAccess;

/**
 * Created by martin on 08.01.18.
 */

public class DatabasePath {
    private String[] path;

    public DatabasePath(String path) {
        this.path = path.split("/");
    }

    public String[] getPath() {
        return this.path;
    }
}
