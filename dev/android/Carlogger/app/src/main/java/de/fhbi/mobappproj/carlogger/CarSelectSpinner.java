package de.fhbi.mobappproj.carlogger;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import de.fhbi.mobappproj.carlogger.DataClasses.entry.Car;
import de.fhbi.mobappproj.carlogger.DataClasses.list.CarList;
import de.fhbi.mobappproj.carlogger.activities.MainActivity;
import de.fhbi.mobappproj.carlogger.dataAccess.entryAccess.CarAccess;
import de.fhbi.mobappproj.carlogger.fragments.AllFragment;
import de.fhbi.mobappproj.carlogger.fragments.FragmentSuper;

import static de.fhbi.mobappproj.carlogger.activities.MainActivity.FRAGMENTTAG;

/**
 * Created by Johannes on 28.01.2018.
 */

public class CarSelectSpinner extends android.support.v7.widget.AppCompatSpinner implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "CarSelectSpinner";

    private Context context;
    private boolean userIsInteracting = false;
    private ArrayAdapter<Car> spinnerDataAdapter;

    MainActivity caller;


    public CarSelectSpinner(Context context) {
        super(context);
        this.context = context;

    }

    public CarSelectSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

    }

    public void setUpSpinner(MainActivity caller){
        this.caller = caller;
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

    public void updateSpinner() {

/*
        int index = 0;
        for(Car car : CarList.getInstance().getCars()){
            if(car.equals(CarAccess.getInstance().getCurrentCar())){
                this.setSelection(index);
            }
            index++;
        }*/
        spinnerDataAdapter.notifyDataSetChanged();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (userIsInteracting) {
            Car car = CarList.getInstance().getCars().get(position);
            CarAccess.getInstance().setCurrentCar(car, context);

            android.app.FragmentManager fm = caller.getFragmentManager();
            FragmentSuper curFragment = (FragmentSuper) fm.findFragmentByTag(MainActivity.FRAGMENTTAG);
            curFragment.mAdapter.notifyDataSetChanged();

            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.detach(curFragment);
            fragmentTransaction.attach(curFragment);
            fragmentTransaction.commit();

            spinnerDataAdapter.notifyDataSetChanged();




            Log.d(TAG, "car selected");

        } else {
            Log.d(TAG, "user not interacting");
        }
        userIsInteracting = true;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        CarAccess.getInstance().setCurrentCar(null, context);

        Log.i(TAG, "onNothingSelected");



    }
}
