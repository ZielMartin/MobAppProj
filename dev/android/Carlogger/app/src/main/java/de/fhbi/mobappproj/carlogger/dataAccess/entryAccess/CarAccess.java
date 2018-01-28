package de.fhbi.mobappproj.carlogger.dataAccess.entryAccess;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;

import de.fhbi.mobappproj.carlogger.DataClasses.entry.Car;
import de.fhbi.mobappproj.carlogger.DataClasses.list.AllEntryList;
import de.fhbi.mobappproj.carlogger.MyApplication;

import static android.content.Context.MODE_PRIVATE;

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
        SharedPreferences.Editor prefsEditor = preferences.edit();

        if(currentCar != null){
            Gson gson = new Gson();
            String json = gson.toJson(currentCar);
            prefsEditor.putString("SelectedCar", json);
            Log.d("selectedCar", currentCar.getName());
        }else{
            prefsEditor.putString("SelectedCar", "");
            Log.d("selectedCar", "none");

        }
        prefsEditor.commit();



    }
}
