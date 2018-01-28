package de.fhbi.mobappproj.carlogger;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import de.fhbi.mobappproj.carlogger.DataClasses.entry.Car;
import de.fhbi.mobappproj.carlogger.DataClasses.list.CarList;
import de.fhbi.mobappproj.carlogger.DataClasses.list.AllEntryList;
import de.fhbi.mobappproj.carlogger.activities.ChooseCarActivity;
import de.fhbi.mobappproj.carlogger.dataAccess.entryAccess.CarAccess;

/**
 * Created by Johannes on 23.01.2018.
 */

public class MyApplication extends Application {

    private static final String TAG = "MyApplication";

    @Override
    public void onCreate() {
        super.onCreate();




        CarList.getInstance().getAllEntriesFromFirebase();
        AllEntryList.getInstance().getAllEntriesFromFirebase();


/*
        for(Car car : CarList.getInstance().getCars()){
            Log.d("asdgrdsgr", car.getName());
        }


        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String selected = prefs.getString("SELECTEDCARKEY", "");

        boolean carSelected = false;
        for(Car car : CarList.getInstance().getCars()){
            if(car.getKey().equals(selected)){
                CarAccess.getInstance().setCurrentCar(car, this);
                carSelected = true;
            }
        }

        if(!carSelected){
            Intent intent = new Intent(this, ChooseCarActivity.class);
            startActivity(intent);
        }
        */



        File file = getDatabasePath("cars.sqlite");
        File dir = file.getParentFile();
        try {
            if (dir.mkdirs() || dir.isDirectory()) {
                CopyRAWtoSDCard(R.raw.all_cars, file.getAbsolutePath());
                Log.v(TAG, "successfully copied " + file.getAbsolutePath());
            } else {
                Log.e(TAG, "!mkdirs()" + dir.getAbsolutePath());
            }
        } catch (IOException e) {
            Log.e(TAG, "failed copying " + file.getAbsolutePath(), e);
        }

    }

    private void CopyRAWtoSDCard(int id, String path) throws IOException {
        InputStream in = getResources().openRawResource(id);
        FileOutputStream out = new FileOutputStream(path);
        byte[] buff = new byte[1024];
        int read = 0;
        try {
            while ((read = in.read(buff)) > 0) {
                out.write(buff, 0, read);
            }
        } finally {
            in.close();
            out.close();
        }
    }
}