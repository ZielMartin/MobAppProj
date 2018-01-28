package de.fhbi.mobappproj.carlogger.dataAccess.entryAccess;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import de.fhbi.mobappproj.carlogger.DataClasses.entry.Car;
import de.fhbi.mobappproj.carlogger.DataClasses.list.AllEntryList;
import de.fhbi.mobappproj.carlogger.MyApplication;

/**
 * Created by martin on 18.01.18.
 */

public class CarAccess {
    private static CarAccess instance = null;
    private Car currentCar;

    public CarAccess() {
    }

    public static CarAccess getInstance() {
        if (instance == null) instance = new CarAccess();
        return instance;
    }

    public Car getCurrentCar() {
        return currentCar;
    }

    public void setCurrentCar(Car currentCar, Context context) {
        this.currentCar = currentCar;
        //AllEntryList.getInstance().clear();
        AllEntryList.getInstance().getAllEntriesFromFirebase();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        if(currentCar != null){
            editor.putString("SELECTEDCARKEY", currentCar.getKey());
            Log.d("SELECTEDCARKEY", currentCar.getName());
        }else{
            editor.putString("SELECTEDCARKEY", "");
        }
        editor.apply();



    }
}
