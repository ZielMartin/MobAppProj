package de.fhbi.mobappproj.carlogger.DataClasses;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.fhbi.mobappproj.carlogger.dataAccess.allCars.AllCars;

/**
 * Created by Johannes on 19.01.2018.
 */

public class CarStructure {

    private static final String TAG = "CarStructure";

    private List<AllCars> allCars;

    public Map<String, CarManufacturer> getAllManufacturer() {
        return allManufacturer;
    }

    public void setAllManufacturer(Map<String, CarManufacturer> allManufacturer) {
        this.allManufacturer = allManufacturer;
    }

    private Map<String, CarManufacturer> allManufacturer = new HashMap<>();

    public CarStructure(List<AllCars> allCars) {
        this.allCars = allCars;
        setUp();
    }


    /**
     * do here database stuff
     */
    public void handleData(){
        for(CarManufacturer manufacturer : allManufacturer.values()){
            Log.d(TAG, manufacturer.getName());
            for(CarModell modell : manufacturer.getAllModells().values()){
                Log.d(TAG, modell.getName());
                for(CarVariant variant : modell.getAllCarVariant().values()){
                    Log.d(TAG, variant.getName());
                    for(AllCars car : variant.getAllCars()){
                        Log.d(TAG, car.toString());
                    }
                }
            }
        }
    }



    private void setUp() {
        for (AllCars car : allCars) {
            boolean loop = false;
            do {
                loop = false;
                String manufacturer = car.getName().split(" ")[0];
                if (allManufacturer != null && allManufacturer.containsKey(manufacturer)) {
                    String modell = car.getName().split(" ")[1];
                    if (allManufacturer.get(manufacturer).getAllModells() != null && allManufacturer.get(manufacturer).getAllModells().containsKey(modell)) {
                        if (car.getName().split(" ").length > 2) {
                            String variant = car.getName().split(" ")[2];
                            if (allManufacturer.get(manufacturer).getAllModells().get(modell).getAllCarVariant() != null && allManufacturer.get(manufacturer).getAllModells().get(modell).getAllCarVariant().containsKey(variant)) {
                                allManufacturer.get(manufacturer).getAllModells().get(modell).getAllCarVariant().get(variant).getAllCars().add(car);
                            } else {
                                CarVariant newVariant = new CarVariant();
                                newVariant.setName(variant);
                                allManufacturer.get(manufacturer).getAllModells().get(modell).getAllCarVariant().put(variant, newVariant);
                                loop = true;
                            }
                        }
                    } else {
                        CarModell newModell = new CarModell();
                        newModell.setName(modell);
                        allManufacturer.get(manufacturer).getAllModells().put(modell, newModell);
                        loop = true;
                    }
                } else {
                    CarManufacturer newManufacturer = new CarManufacturer();
                    newManufacturer.setName(manufacturer);
                    allManufacturer.put(manufacturer, newManufacturer);
                    loop = true;
                }
            } while (loop);
        }

    }

    private class CarModell {

        private Map<String, CarVariant> allCarVariant = new HashMap<>();
        private String name;

        public Map<String, CarVariant> getAllCarVariant() {
            return allCarVariant;
        }

        public void setAllCarVariant(Map<String, CarVariant> allCarVariant) {
            this.allCarVariant = allCarVariant;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    private class CarVariant {

        private List<AllCars> allCars = new ArrayList<>();
        ;
        private String name;

        public List<AllCars> getAllCars() {
            return allCars;
        }

        public void setAllCars(List<AllCars> allCars) {
            this.allCars = allCars;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    private class CarManufacturer {

        private Map<String, CarModell> allModells = new HashMap<>();
        ;
        private String name;

        public Map<String, CarModell> getAllModells() {
            return allModells;
        }

        public void setAllModells(Map<String, CarModell> allModells) {
            this.allModells = allModells;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
