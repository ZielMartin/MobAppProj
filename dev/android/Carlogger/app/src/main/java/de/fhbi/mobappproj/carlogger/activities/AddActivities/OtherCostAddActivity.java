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

import de.fhbi.mobappproj.carlogger.DataClasses.AutoEntryDates;
import de.fhbi.mobappproj.carlogger.DataClasses.OtherCostEntry;
import de.fhbi.mobappproj.carlogger.R;

public class OtherCostAddActivity extends AddActivitySuper implements CompoundButton.OnCheckedChangeListener {

    private EditText ET_OtherCostDescription, ET_OtherCostCost;
    private CheckBox CB_OtherCostAddAutoEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {

            if(savedInstanceState.getString("cbText")!=null){
                CB_OtherCostAddAutoEntry.setText(savedInstanceState.getString("cbText"));
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("cbText", (String) CB_OtherCostAddAutoEntry.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void initGUIElements() {

        ET_OtherCostCost = (EditText) findViewById(R.id.ET_OtherCostAddCost);
        ET_OtherCostCost.setOnFocusChangeListener(this);

        ET_OtherCostDescription = (EditText) findViewById(R.id.ET_OtherCostAddDescription);
        ET_OtherCostDescription.setOnFocusChangeListener(this);

        CB_OtherCostAddAutoEntry = findViewById(R.id.CB_OtherCostAddAutoEntry);
        CB_OtherCostAddAutoEntry.setOnCheckedChangeListener(this);

        //addButton
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabOtherCostCheck);
        fab.setOnClickListener(this);
    }

    @Override
    protected void contentView() {
        setContentView(R.layout.activity_other_cost_add);
    }

    @Override
    protected boolean checkInput() {

        String descriptionInput = ET_OtherCostDescription.getText().toString();
        if( descriptionInput.equals("")) {
            Toast.makeText(this, getString(R.string.input_error), Toast.LENGTH_SHORT).show();
            ET_OtherCostDescription.setError(getString(R.string.error_description));
            ET_OtherCostDescription.requestFocus();
            return false;
        }

        Double costInput = editTextToDouble(ET_OtherCostCost);
        if( costInput <= 0) {
            Toast.makeText(this, getString(R.string.input_error), Toast.LENGTH_SHORT).show();
            ET_OtherCostCost.setError(getString(R.string.error_cost));
            ET_OtherCostCost.requestFocus();
            return false;
        }

        return true;
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case(R.id.fabOtherCostCheck):
                if(checkInput()){

                    OtherCostEntry entry = new OtherCostEntry();
                    entry.setCost(editTextToDouble(ET_OtherCostCost));
                    entry.setDescription(ET_OtherCostDescription.getText().toString());
                    entry.setAutoEntry(autoEntry);
                    entry.push();

                    finish();
                }

                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        //button pressed and autoEntry not set
        if(b && autoEntry == null){
            alertDialogAutoEntryPicker();


        }if(!b){
            autoEntry = null;
            CB_OtherCostAddAutoEntry.setText(getString(R.string.add_cb_auto_entry));
        }
    }


    private void alertDialogAutoEntryPicker() {
        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.ad_auto_entry_title);

        // add a list
        String[] values = {getString(R.string.ad_auto_entry_daily),
                getString(R.string.ad_auto_entry_weekly),
                getString(R.string.ad_auto_entry_monthly),
                getString(R.string.ad_auto_entry_yearly),
                getString(R.string.ad_auto_entry_everytwoyear)
        };
        builder.setItems(values, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0: // daily
                        CB_OtherCostAddAutoEntry.setText(getString(R.string.add_cb_auto_entry_with_time,getString(R.string.ad_auto_entry_daily)));
                        autoEntry = AutoEntryDates.AutoEntry.DAILY;
                        break;
                    case 1: // weekly
                        CB_OtherCostAddAutoEntry.setText(getString(R.string.add_cb_auto_entry_with_time,getString(R.string.ad_auto_entry_weekly)));
                        autoEntry = AutoEntryDates.AutoEntry.WEEKLY;
                        break;
                    case 2: // monthly
                        CB_OtherCostAddAutoEntry.setText(getString(R.string.add_cb_auto_entry_with_time,getString(R.string.ad_auto_entry_monthly)));
                        autoEntry = AutoEntryDates.AutoEntry.MONTHLY;
                        break;
                    case 3: // yearly
                        CB_OtherCostAddAutoEntry.setText(getString(R.string.add_cb_auto_entry_with_time,getString(R.string.ad_auto_entry_yearly)));
                        autoEntry = AutoEntryDates.AutoEntry.YEARLY;
                        break;
                    case 4: // every two year
                        CB_OtherCostAddAutoEntry.setText(getString(R.string.add_cb_auto_entry_with_time,getString(R.string.ad_auto_entry_everytwoyear)));
                        autoEntry = AutoEntryDates.AutoEntry.EVERYTWOYEAR;
                        break;

                    default:
                        CB_OtherCostAddAutoEntry.setChecked(false);
                        CB_OtherCostAddAutoEntry.setText(getString(R.string.add_cb_auto_entry));
                        break;
                }
            }
        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                CB_OtherCostAddAutoEntry.setChecked(false);
                CB_OtherCostAddAutoEntry.setText(getString(R.string.add_cb_auto_entry));
            }
        });

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
