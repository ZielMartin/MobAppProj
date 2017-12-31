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

import java.util.Calendar;

import de.fhbi.mobappproj.carlogger.DataClasses.AutoEntryDates.AutoEntry;
import de.fhbi.mobappproj.carlogger.DataClasses.FuelEntry;
import de.fhbi.mobappproj.carlogger.DataClasses.FuelEntryList;
import de.fhbi.mobappproj.carlogger.DataClasses.ReminderEntry;
import de.fhbi.mobappproj.carlogger.R;

import static de.fhbi.mobappproj.carlogger.Helper.doubleToString;

public class FuelAddActivity extends AddActivitySuper implements CompoundButton.OnCheckedChangeListener{

    private EditText ET_FuelAddAmount;
    private EditText ET_FuelAddCostPerLitre;
    private EditText ET_FuelAddKM;
    private CheckBox CB_FuelAddFull;
    private CheckBox CB_FuelAddAutoEntry;

    private FuelEntry editEntry;
    private int entryIndex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {

            if(savedInstanceState.getString("cbText")!=null){
                CB_FuelAddAutoEntry.setText(savedInstanceState.getString("cbText"));
            }
        }else{
            //when editButton is pressed
            Bundle extras = getIntent().getExtras();
            if(extras != null){
                editEntry = extras.getParcelable("entry");
                setEditEntryValues(editEntry);
                entryIndex = extras.getInt("entryIndex");
            }
        }

    }

    @Override
    protected void initGUIElements() {
        //TODO Fill
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabFuelCheck);
        fab.setOnClickListener(this);

        ET_FuelAddCostPerLitre = (EditText) findViewById(R.id.ET_FuelAddCostPerLitre);
        ET_FuelAddCostPerLitre.setOnFocusChangeListener(this);

        ET_FuelAddAmount = (EditText) findViewById(R.id.ET_FuelAddAmount);
        ET_FuelAddAmount.setOnFocusChangeListener(this);

        ET_FuelAddKM = (EditText) findViewById(R.id.ET_FuelAddKM);
        ET_FuelAddKM.setOnFocusChangeListener(this);

        CB_FuelAddFull = (CheckBox) findViewById(R.id.CB_FuelAddFull);
        //CB_FuelAddFull.setOnCheckedChangeListener(this);

        CB_FuelAddAutoEntry = (CheckBox) findViewById(R.id.CB_FuelAddAutoEntry);
        CB_FuelAddAutoEntry.setOnCheckedChangeListener(this);


    }

    @Override
    protected void contentView() {
        setContentView(R.layout.activity_fuel_add);
    }

    @Override
    protected boolean checkInput() {


        Double quantityInput = editTextToDouble(ET_FuelAddAmount);
        if(quantityInput <= 0) {
            Toast.makeText(this, getString(R.string.input_error), Toast.LENGTH_SHORT).show();
            ET_FuelAddAmount.setError(getString(R.string.fuel_add_error_quantity));
            ET_FuelAddAmount.requestFocus();
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

                if(checkInput()){
                    //if this is an edit: edit the given entry
                    if(editEntry != null){
                        FuelEntryList.getInstance().set(entryIndex, editEntry);
                        editEntry.setAutoEntry(autoEntry);
                        editEntry.setCostPerLitre(editTextToDouble(ET_FuelAddCostPerLitre));
                        editEntry.setFull(CB_FuelAddFull.isChecked());
                        editEntry.setKm(editTextToDouble(ET_FuelAddKM));
                        editEntry.setAmount(editTextToDouble(ET_FuelAddAmount));
                        editEntry.push();
                    }else{
                        FuelEntry fe = new FuelEntry();
                        fe.setAutoEntry(autoEntry);
                        fe.setCostPerLitre(editTextToDouble(ET_FuelAddCostPerLitre));
                        fe.setFull(CB_FuelAddFull.isChecked());
                        fe.setKm(editTextToDouble(ET_FuelAddKM));
                        fe.setAmount(editTextToDouble(ET_FuelAddAmount));
                        fe.push();
                    }





                    finish();
                }
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("cbText", (String) CB_FuelAddAutoEntry.getText().toString());
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        //button pressed and autoEntry not set
        if(b && autoEntry == null){
            alertDialogAutoEntryPicker();


        }if(!b){
            autoEntry = null;
            CB_FuelAddAutoEntry.setText(getString(R.string.add_cb_auto_entry));
        }
    }

    private void alertDialogAutoEntryPicker() {
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
                        CB_FuelAddAutoEntry.setText(getString(R.string.add_cb_auto_entry_with_time,getString(R.string.ad_auto_entry_daily)));
                        autoEntry = AutoEntry.DAILY;
                        break;
                    case 1: // weekly
                        CB_FuelAddAutoEntry.setText(getString(R.string.add_cb_auto_entry_with_time,getString(R.string.ad_auto_entry_weekly)));
                        autoEntry = AutoEntry.WEEKLY;
                        break;
                    case 2: // monthly
                        CB_FuelAddAutoEntry.setText(getString(R.string.add_cb_auto_entry_with_time,getString(R.string.ad_auto_entry_monthly)));
                        autoEntry = AutoEntry.MONTHLY;
                        break;

                    default:
                        CB_FuelAddAutoEntry.setChecked(false);
                        CB_FuelAddAutoEntry.setText(getString(R.string.add_cb_auto_entry));
                        break;
                }
            }
        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                CB_FuelAddAutoEntry.setChecked(false);
                CB_FuelAddAutoEntry.setText(getString(R.string.add_cb_auto_entry));
            }
        });

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * sets values to the editing Entry
     * @param entry
     */
    public void setEditEntryValues(FuelEntry entry){
        ET_FuelAddAmount.setText(doubleToString(entry.getAmount()));
        ET_FuelAddCostPerLitre.setText(doubleToString(entry.getCostPerLitre()));
        ET_FuelAddKM.setText(doubleToString(entry.getKm()));
        CB_FuelAddFull.setChecked(entry.isFull());

        if(entry.getAutoEntry() != null){
            CB_FuelAddAutoEntry.setChecked(true);
        }else{
            CB_FuelAddAutoEntry.setChecked(false);
        }

    }


}
