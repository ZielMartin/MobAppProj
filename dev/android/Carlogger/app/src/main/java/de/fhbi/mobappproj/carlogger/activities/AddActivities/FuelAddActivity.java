package de.fhbi.mobappproj.carlogger.activities.AddActivities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import de.fhbi.mobappproj.carlogger.DataClasses.AutoEntryDates.AutoEntry;
import de.fhbi.mobappproj.carlogger.DataClasses.FuelEntry;
import de.fhbi.mobappproj.carlogger.R;

public class FuelAddActivity extends AddActivitySuper implements CompoundButton.OnCheckedChangeListener{

    private EditText ET_FuelAddQuantity;
    private EditText ET_FuelAddCostPerLitre;
    private EditText ET_FuelAddKM;
    private CheckBox CB_FuelAddFull;
    private CheckBox CB_FuelAddAutoEntry;

    private AutoEntry autoEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initGUIElements() {
        //TODO Fill
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabFuelCheck);
        fab.setOnClickListener(this);

        ET_FuelAddCostPerLitre = (EditText) findViewById(R.id.ET_FuelAddCostPerLitre);
        ET_FuelAddCostPerLitre.setOnFocusChangeListener(this);

        ET_FuelAddQuantity = (EditText) findViewById(R.id.ET_FuelAddQuantity);
        ET_FuelAddQuantity.setOnFocusChangeListener(this);

        ET_FuelAddKM = (EditText) findViewById(R.id.ET_FuelAddKM);
        ET_FuelAddKM.setOnFocusChangeListener(this);

        CB_FuelAddFull = (CheckBox) findViewById(R.id.CB_FuelAddFull);
        //CB_FuelAddFull.setOnCheckedChangeListener(this);

        CB_FuelAddAutoEntry = (CheckBox) findViewById(R.id.CB_FuelAddAutoEntry);
        CB_FuelAddAutoEntry.setOnCheckedChangeListener(this);

        autoEntry = null;
    }

    @Override
    protected void setContentView() {
        super.setContentView(R.layout.activity_fuel_add);
    }

    @Override
    protected boolean checkInput() {


        Double quantityInput = editTextToDouble(ET_FuelAddQuantity);
        if(quantityInput <= 0) {
            Toast.makeText(this, getString(R.string.input_error), Toast.LENGTH_SHORT).show();
            ET_FuelAddQuantity.setError(getString(R.string.fuel_add_error_quantity));
            ET_FuelAddQuantity.requestFocus();
            return false;
        }
        Double costInput = editTextToDouble(ET_FuelAddCostPerLitre);
        if( costInput <= 0) {
            Toast.makeText(this, getString(R.string.input_error), Toast.LENGTH_SHORT).show();
            ET_FuelAddCostPerLitre.setError(getString(R.string.fuel_add_error_cost));
            ET_FuelAddCostPerLitre.requestFocus();
            return false;
        }
        return true;
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case(R.id.fabFuelCheck):

                FuelEntry fe = new FuelEntry();
                fe.setAutoEntry(autoEntry);
                fe.setCostPerLitre(editTextToDouble(ET_FuelAddCostPerLitre));
                fe.setFull(CB_FuelAddFull.isChecked());
                fe.setKm(editTextToDouble(ET_FuelAddKM));
                fe.setQuantity(editTextToDouble(ET_FuelAddQuantity));
                fe.push();

                if(checkInput()){
                    finish();
                }
                break;
        }
    }




    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        if(b){

            // setup the alert builder
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.ad_auto_entry_title);

            // add a list
            String[] values = {getString(R.string.ad_auto_entry_daily),
                    getString(R.string.ad_auto_entry_weekly),
                    getString(R.string.ad_auto_entry_monthly)
            };
            builder.setItems(values, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0: // daily
                            CB_FuelAddAutoEntry.setText(getString(R.string.fuel_add_cb_auto_entry_with_time,getString(R.string.ad_auto_entry_daily)));
                            autoEntry = AutoEntry.DAILY;
                            break;
                        case 1: // weekly
                            CB_FuelAddAutoEntry.setText(getString(R.string.fuel_add_cb_auto_entry_with_time,getString(R.string.ad_auto_entry_weekly)));
                            autoEntry = AutoEntry.WEEKLY;
                            break;
                        case 2: // monthly
                            CB_FuelAddAutoEntry.setText(getString(R.string.fuel_add_cb_auto_entry_with_time,getString(R.string.ad_auto_entry_monthly)));
                            autoEntry = AutoEntry.MONTHLY;
                            break;

                        default:
                            CB_FuelAddAutoEntry.setChecked(false);
                            break;
                    }
                }
            });

            // create and show the alert dialog
            AlertDialog dialog = builder.create();
            dialog.show();
        }else{
            CB_FuelAddAutoEntry.setText(getString(R.string.fuel_add_cb_auto_entry));
        }
    }




}
