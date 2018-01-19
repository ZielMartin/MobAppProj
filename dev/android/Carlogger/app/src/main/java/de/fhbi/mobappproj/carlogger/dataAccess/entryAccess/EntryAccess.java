package de.fhbi.mobappproj.carlogger.dataAccess.entryAccess;

import de.fhbi.mobappproj.carlogger.DataClasses.entry.EntrySuper;
import de.fhbi.mobappproj.carlogger.dataAccess.DataAccess;

/**
 * Created by martin on 18.01.18.
 */

public abstract class EntryAccess {
    protected DataAccess dataAccess;

    public abstract void push(EntrySuper entry);

    public abstract void delete(EntrySuper entry);

    public abstract void update(EntrySuper entry);
}
