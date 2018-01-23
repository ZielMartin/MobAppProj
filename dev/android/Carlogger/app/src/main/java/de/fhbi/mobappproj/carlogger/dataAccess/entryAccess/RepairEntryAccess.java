package de.fhbi.mobappproj.carlogger.dataAccess.entryAccess;

import de.fhbi.mobappproj.carlogger.DataClasses.Car;
import de.fhbi.mobappproj.carlogger.DataClasses.entry.EntrySuper;
import de.fhbi.mobappproj.carlogger.dataAccess.FirebaseAccess;

/**
 * Created by martin on 18.01.18.
 */

public class RepairEntryAccess extends EntryAccess {

    private static RepairEntryAccess instance = null;

    private RepairEntryAccess() {
        this.dataAccess = FirebaseAccess.getInstance();
    }

    public RepairEntryAccess getInstance() {
        if (instance == null)
            instance = new RepairEntryAccess();
        return instance;
    }

    @Override
    public void push(EntrySuper entry) {
        String id = dataAccess.getUid();
        Car currentCar = CarAccess.getInstance().getCurrentCar();
        dataAccess.push("users/" + id + "/cars/" + currentCar.getHSNTSN(), entry);
    }

    @Override
    public void delete(EntrySuper entry) {
        if (entry == null) {
            throw new IllegalArgumentException("Entry can't be null");
        } else if (entry.getKey() == null) {
            throw new IllegalArgumentException("Missing Key in Entry");
        }


    }

    @Override
    public void update(EntrySuper entry) {

    }
}
