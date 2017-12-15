package de.fhbi.mobappproj.carlogger.DataClasses;

import android.os.Bundle;

/**
 * all sub-classes has to call their EntryList-singleton-instance in the contructor
 * and pushToFirebase() in push(), after checking the variables are setted.
 *
 * variables should be setted with setters to avoid long parameter-lists
 *
 * example: ReminderEntry
 * usage example: ReminderAdd
 *
 * Created by Johannes on 15.12.2017.
 */

public abstract class EntrySuper {



    protected abstract void pushToFirebase();

    /**
     * check all variables and call pushToFirebase()
     */
    public abstract void push();


}
