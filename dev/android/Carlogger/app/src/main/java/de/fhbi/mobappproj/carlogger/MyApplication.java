package de.fhbi.mobappproj.carlogger;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import de.fhbi.mobappproj.carlogger.DataClasses.list.CarList;
import de.fhbi.mobappproj.carlogger.DataClasses.list.AllEntryList;
import de.fhbi.mobappproj.carlogger.activities.ChooseCarActivity;

/**
 * Created by Johannes on 23.01.2018.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();



        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        long selected = prefs.getLong("SELECTEDCARKEY", 0);

        //select car if none is selected
        if(selected == 0){
            Intent intent = new Intent(this, ChooseCarActivity.class);
            this.startActivity(intent);
        }

        AllEntryList.getInstance().getAllEntriesFromFirebase();
        CarList.getInstance().getAllEntriesFromFirebase();
    }
}