package de.fhbi.mobappproj.carlogger.activities.AddActivities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;

import de.fhbi.mobappproj.carlogger.R;
import de.fhbi.mobappproj.carlogger.activities.AddActivities.AddActivitySuper;

public class RepairAdd extends AddActivitySuper {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void initGUIElements() {
        //TODO Fill
        //addButton
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabRepairCheck);
        fab.setOnClickListener(this);
    }

    @Override
    protected void setContentView() {
        super.setContentView(R.layout.activity_repair_add);
    }


    @Override
    protected boolean checkInput() {
        //TODO Fill
        return false;
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case(R.id.fabRepairCheck):
                //TODO: Save created Data on Firebase
                finish();
                break;
        }
    }
}
