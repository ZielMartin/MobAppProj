package de.fhbi.mobappproj.carlogger.activities.AddActivities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;

import de.fhbi.mobappproj.carlogger.R;
import de.fhbi.mobappproj.carlogger.activities.AddActivities.AddActivitySuper;

public class FuelAdd extends AddActivitySuper {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initGUIElements() {
        //TODO Fill
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabFuelCheck);
        fab.setOnClickListener(this);

    }

    @Override
    protected void setContentView() {
        super.setContentView(R.layout.activity_fuel_add);
    }

    @Override
    protected boolean checkInput() {
        //TODO Fill
        return false;
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case(R.id.fabFuelCheck):
                //TODO: Save created Data on Firebase
                finish();
                break;
        }
    }


}
