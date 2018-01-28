package de.fhbi.mobappproj.carlogger;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import de.fhbi.mobappproj.carlogger.DataClasses.entry.Car;
import de.fhbi.mobappproj.carlogger.DataClasses.list.CarList;
import de.fhbi.mobappproj.carlogger.activities.ChooseCarActivity;
import de.fhbi.mobappproj.carlogger.dataAccess.allCars.AllCars;
import de.fhbi.mobappproj.carlogger.dataAccess.entryAccess.CarAccess;

/**
 * Created by Johannes on 28.01.2018.
 */

public class CarSelectSpinner extends android.support.v7.widget.AppCompatSpinner implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "CarSelectSpinner";

    private Context context;
    private boolean userIsInteracting;
    private ArrayAdapter<Car> spinnerDataAdapter;


    public CarSelectSpinner(Context context) {
        super(context);
        this.context = context;

    }

    public CarSelectSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

    }

    public void setUpSpinner(){
        spinnerDataAdapter = new ArrayAdapter<Car>(context,
                android.R.layout.simple_spinner_item, CarList.getInstance().getCars());
        spinnerDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.setAdapter(spinnerDataAdapter);
        CarList.getInstance().setAdapterToUpdate(spinnerDataAdapter);

        this.setOnItemSelectedListener(this);

        /*
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String selected = prefs.getString("SELECTEDCARKEY", "");
        int index = 0;

        for(Car car : CarList.getInstance().getCars()){
            Log.d("kjdfus", car.getName());
            if(car.getKey().equals(selected)){
                this.setSelection(index);
            }
            index++;
        }
        */

    }

    public void updateSpinner(boolean userIsInteracting) {
        this.userIsInteracting = userIsInteracting;
        spinnerDataAdapter.notifyDataSetChanged();




    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (userIsInteracting) {
            Car car = CarList.getInstance().getCars().get(position);
            CarAccess.getInstance().setCurrentCar(car, context);
            Log.d(TAG, "car selected");

        } else {
            Log.d(TAG, "user not interacting");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        CarAccess.getInstance().setCurrentCar(null, context);

        Log.i(TAG, "onNothingSelected");



    }
}
