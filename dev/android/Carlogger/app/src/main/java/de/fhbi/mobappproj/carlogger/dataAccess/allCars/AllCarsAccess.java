package de.fhbi.mobappproj.carlogger.dataAccess.allCars;

import android.content.res.Resources;

import java.util.List;

import de.fhbi.mobappproj.carlogger.R;

/**
 * Created by martin on 19.01.18.
 */

public class AllCarsAccess {
    private List<AllCars> allCarsList = null;

    public AllCarsAccess(Resources resources) {
        new Thread(() ->{
            JSONResourceReader reader = new JSONResourceReader(resources, R.raw.all_cars);
            allCarsList = reader.constructUsingGson(AllCarsList.class).getCars();
        }).run();

    }

    public List<AllCars> getAllCarsList() {
        return allCarsList;
    }
}
