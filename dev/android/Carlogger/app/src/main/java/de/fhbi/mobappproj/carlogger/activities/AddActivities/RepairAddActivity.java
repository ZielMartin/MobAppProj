package de.fhbi.mobappproj.carlogger.activities.AddActivities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.Arrays;

import de.fhbi.mobappproj.carlogger.DataClasses.AutoEntryDates;
import de.fhbi.mobappproj.carlogger.DataClasses.FuelEntry;
import de.fhbi.mobappproj.carlogger.DataClasses.ReminderEntryList;
import de.fhbi.mobappproj.carlogger.DataClasses.RepairEntry;
import de.fhbi.mobappproj.carlogger.DataClasses.RepairEntryList;
import de.fhbi.mobappproj.carlogger.R;

import static de.fhbi.mobappproj.carlogger.Helper.buttonEffect;
import static de.fhbi.mobappproj.carlogger.Helper.doubleToString;

public class RepairAddActivity extends AddActivitySuper implements CompoundButton.OnCheckedChangeListener {

    private EditText ET_RepairAddPartCost, ET_RepairAddLaborCost, ET_RepairAddDescription;
    private Spinner SP_RepairAddType;
    private Button BTN_RepairAddBill, BTN_RepairAddDeleteBill;
    private ImageView IV_RepairAddBill;

    private CheckBox CB_RepairAddAutoEntry;


    private RepairEntry editEntry;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            if(savedInstanceState.getString("image") != null){
                image = new File(savedInstanceState.getString("image"));
                setPic(IV_RepairAddBill);
            }
            if(savedInstanceState.getString("cbText")!=null){
                CB_RepairAddAutoEntry.setText(savedInstanceState.getString("cbText"));
            }
        }else{
            //when editButton is pressed
            Bundle extras = getIntent().getExtras();
            if(extras != null){
                editEntry = extras.getParcelable("entry");
                setEditEntryValues(editEntry);
                if(editEntry.getBill()!=null){
                    image = editEntry.getBill();
                    setPic(IV_RepairAddBill);
                }
            }
        }
        if(image != null){
            BTN_RepairAddDeleteBill.setVisibility(View.VISIBLE);
        }

    }


    @Override
    protected void initGUIElements() {

        SP_RepairAddType = (Spinner) findViewById(R.id.SP_RepairAddType);


        ET_RepairAddPartCost = (EditText) findViewById(R.id.ET_RepairAddPartCost);
        ET_RepairAddPartCost.setOnFocusChangeListener(this);

        ET_RepairAddLaborCost = (EditText) findViewById(R.id.ET_RepairAddLaborCost);
        ET_RepairAddLaborCost.setOnFocusChangeListener(this);

        ET_RepairAddDescription = (EditText) findViewById(R.id.ET_RepairAddDescription);
        ET_RepairAddDescription.setOnFocusChangeListener(this);

        BTN_RepairAddBill = (Button) findViewById(R.id.BTN_RepairAddBill);
        BTN_RepairAddBill.setOnClickListener(this);
        buttonEffect(BTN_RepairAddBill);

        BTN_RepairAddDeleteBill = (Button) findViewById(R.id.BTN_RepairAddDeleteBill);
        BTN_RepairAddDeleteBill.setOnClickListener(this);
        buttonEffect(BTN_RepairAddDeleteBill);

        IV_RepairAddBill = (ImageView) findViewById(R.id.IV_RepairAddBill);

        CB_RepairAddAutoEntry = (CheckBox) findViewById(R.id.CB_RepairAddAutoEntry);
        CB_RepairAddAutoEntry.setOnCheckedChangeListener(this);


        //addButton
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabRepairCheck);
        fab.setOnClickListener(this);
    }

    @Override
    protected void contentView() {
        setContentView(R.layout.activity_repair_add);
    }


    @Override
    protected boolean checkInput() {

        Double partCostInput = editTextToDouble(ET_RepairAddPartCost);
        if(partCostInput < 0) {
            Toast.makeText(this, getString(R.string.input_error), Toast.LENGTH_SHORT).show();
            ET_RepairAddPartCost.setError(getString(R.string.error_cost));
            ET_RepairAddPartCost.requestFocus();
            return false;
        }
        Double laborCostInput = editTextToDouble(ET_RepairAddLaborCost);
        if(laborCostInput < 0) {
            Toast.makeText(this, getString(R.string.input_error), Toast.LENGTH_SHORT).show();
            ET_RepairAddLaborCost.setError(getString(R.string.error_cost));
            ET_RepairAddLaborCost.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            setPic(IV_RepairAddBill);
            BTN_RepairAddDeleteBill.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if(image != null){
            outState.putString("image", image.getAbsolutePath());
        }
        outState.putString("cbText", (String) CB_RepairAddAutoEntry.getText().toString());
        super.onSaveInstanceState(outState);
    }





    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case(R.id.fabRepairCheck):

                if(checkInput()){
                    //if this is an edit: edit the given entry
                    if(editEntry != null){
                        editEntry.setType(SP_RepairAddType.getSelectedItem().toString());
                        editEntry.setAutoEntry(autoEntry);
                        editEntry.setBill(image);
                        editEntry.setDescription(ET_RepairAddDescription.getText().toString());
                        editEntry.setLaborCost(editTextToDouble(ET_RepairAddLaborCost));
                        editEntry.setPartCost(editTextToDouble(ET_RepairAddPartCost));
                        editEntry.updateEntry();

                    }else {
                        //Create Entry and fill in Data
                        RepairEntry entry = new RepairEntry();
                        entry.setType(SP_RepairAddType.getSelectedItem().toString());
                        entry.setAutoEntry(autoEntry);
                        entry.setBill(image);
                        entry.setDescription(ET_RepairAddDescription.getText().toString());
                        entry.setLaborCost(editTextToDouble(ET_RepairAddLaborCost));
                        entry.setPartCost(editTextToDouble(ET_RepairAddPartCost));
                        entry.push();

                    }

                    finish();
                }
                break;
            case(R.id.BTN_RepairAddBill):
                dispatchTakePictureIntent();

                break;
            case(R.id.BTN_RepairAddDeleteBill):
                BTN_RepairAddDeleteBill.setVisibility(View.INVISIBLE);
                deletImage(IV_RepairAddBill);
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
            CB_RepairAddAutoEntry.setText(getString(R.string.add_cb_auto_entry));

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
                getString(R.string.ad_auto_entry_everytwoyear)};
        builder.setItems(values, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0: // daily
                        CB_RepairAddAutoEntry.setText(getString(R.string.add_cb_auto_entry_with_time,getString(R.string.ad_auto_entry_daily)));
                        autoEntry = AutoEntryDates.AutoEntry.DAILY;
                        break;
                    case 1: // weekly
                        CB_RepairAddAutoEntry.setText(getString(R.string.add_cb_auto_entry_with_time,getString(R.string.ad_auto_entry_weekly)));
                        autoEntry = AutoEntryDates.AutoEntry.WEEKLY;
                        break;
                    case 2: // monthly
                        CB_RepairAddAutoEntry.setText(getString(R.string.add_cb_auto_entry_with_time,getString(R.string.ad_auto_entry_monthly)));
                        autoEntry = AutoEntryDates.AutoEntry.MONTHLY;
                        break;
                    case 3: // yearly
                        CB_RepairAddAutoEntry.setText(getString(R.string.add_cb_auto_entry_with_time,getString(R.string.ad_auto_entry_yearly)));
                        autoEntry = AutoEntryDates.AutoEntry.YEARLY;
                        break;
                    case 4: // every two year
                        CB_RepairAddAutoEntry.setText(getString(R.string.add_cb_auto_entry_with_time,getString(R.string.ad_auto_entry_everytwoyear)));
                        autoEntry = AutoEntryDates.AutoEntry.EVERYTWOYEAR;
                        break;

                    default:
                        CB_RepairAddAutoEntry.setChecked(false);
                        break;
                }
            }
        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                CB_RepairAddAutoEntry.setChecked(false);
                CB_RepairAddAutoEntry.setText(getString(R.string.add_cb_auto_entry));

            }
        });

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void setEditEntryValues(RepairEntry entry){
        //SP_RepairAddType.se  .setText(entry.getType());
        SP_RepairAddType.setSelection (Arrays.asList(this.getResources().getStringArray(R.array.repair_type)).indexOf(entry.getType()) );
        ET_RepairAddLaborCost.setText(doubleToString(entry.getLaborCost()));
        ET_RepairAddPartCost.setText(doubleToString(entry.getPartCost()));
        ET_RepairAddDescription.setText(entry.getDescription());

        image = entry.getBill();

        if(entry.getAutoEntry() != null){
            CB_RepairAddAutoEntry.setChecked(true);
        }else{
            CB_RepairAddAutoEntry.setChecked(false);
        }

    }
}
