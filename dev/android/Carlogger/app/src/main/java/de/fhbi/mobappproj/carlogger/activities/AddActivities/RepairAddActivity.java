package de.fhbi.mobappproj.carlogger.activities.AddActivities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.fhbi.mobappproj.carlogger.DataClasses.AutoEntryDates;
import de.fhbi.mobappproj.carlogger.DataClasses.RepairEntry;
import de.fhbi.mobappproj.carlogger.R;

import static de.fhbi.mobappproj.carlogger.Helper.buttonEffect;

public class RepairAddActivity extends AddActivitySuper implements CompoundButton.OnCheckedChangeListener {

    private EditText ET_RepairAddCost, ET_RepairAddPartCost, ET_RepairAddLaborCost, ET_RepairAddDescription;
    private Button BTN_RepairAddBill, BTN_RepairAddDeleteBill;
    private ImageView IV_RepairAddBill;

    private AutoEntryDates.AutoEntry autoEntry;
    private CheckBox CB_ReminderAddAutoEntry;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            if(savedInstanceState.getString("image") != null){
                image = new File(savedInstanceState.getString("image"));
                setPic(IV_RepairAddBill);
            }
        }
        if(image != null){
            BTN_RepairAddDeleteBill.setVisibility(View.VISIBLE);
        }
    }


    @Override
    protected void initGUIElements() {

        ET_RepairAddCost = (EditText) findViewById(R.id.ET_RepairAddCost);
        ET_RepairAddCost.setOnFocusChangeListener(this);

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

        CB_ReminderAddAutoEntry = (CheckBox) findViewById(R.id.CB_ReminderAddAutoEntry);
        CB_ReminderAddAutoEntry.setOnCheckedChangeListener(this);

        autoEntry = null;

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
        Double costInput = editTextToDouble(ET_RepairAddCost);
        if(costInput < 0) {
            Toast.makeText(this, getString(R.string.input_error), Toast.LENGTH_SHORT).show();
            ET_RepairAddCost.setError(getString(R.string.repair_add_error_cost));
            ET_RepairAddCost.requestFocus();
            return false;
        }
        Double partCostInput = editTextToDouble(ET_RepairAddPartCost);
        if(partCostInput < 0) {
            Toast.makeText(this, getString(R.string.input_error), Toast.LENGTH_SHORT).show();
            ET_RepairAddPartCost.setError(getString(R.string.repair_add_error_cost));
            ET_RepairAddPartCost.requestFocus();
            return false;
        }
        Double laborCostInput = editTextToDouble(ET_RepairAddLaborCost);
        if(laborCostInput < 0) {
            Toast.makeText(this, getString(R.string.input_error), Toast.LENGTH_SHORT).show();
            ET_RepairAddLaborCost.setError(getString(R.string.repair_add_error_cost));
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
        super.onSaveInstanceState(outState);
    }





    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case(R.id.fabRepairCheck):

                RepairEntry entry = new RepairEntry();
                entry.setAutoEntry(autoEntry);
                entry.setBill(image);
                entry.setCost(editTextToDouble(ET_RepairAddCost));
                entry.setDescription(ET_RepairAddDescription.getText().toString());
                entry.setLaborCost(editTextToDouble(ET_RepairAddLaborCost));
                entry.setPartCost(editTextToDouble(ET_RepairAddPartCost));
                entry.push();


                if(checkInput()){
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
        if(b){

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
                            CB_ReminderAddAutoEntry.setText(getString(R.string.fuel_add_cb_auto_entry_with_time,getString(R.string.ad_auto_entry_daily)));
                            autoEntry = AutoEntryDates.AutoEntry.DAILY;
                            break;
                        case 1: // weekly
                            CB_ReminderAddAutoEntry.setText(getString(R.string.fuel_add_cb_auto_entry_with_time,getString(R.string.ad_auto_entry_weekly)));
                            autoEntry = AutoEntryDates.AutoEntry.WEEKLY;
                            break;
                        case 2: // monthly
                            CB_ReminderAddAutoEntry.setText(getString(R.string.fuel_add_cb_auto_entry_with_time,getString(R.string.ad_auto_entry_monthly)));
                            autoEntry = AutoEntryDates.AutoEntry.MONTHLY;
                            break;
                        case 3: // yearly
                            CB_ReminderAddAutoEntry.setText(getString(R.string.fuel_add_cb_auto_entry_with_time,getString(R.string.ad_auto_entry_yearly)));
                            autoEntry = AutoEntryDates.AutoEntry.YEARLY;
                            break;
                        case 4: // every two year
                            CB_ReminderAddAutoEntry.setText(getString(R.string.fuel_add_cb_auto_entry_with_time,getString(R.string.ad_auto_entry_everytwoyear)));
                            autoEntry = AutoEntryDates.AutoEntry.EVERYTWOYEAR;
                            break;

                        default:
                            CB_ReminderAddAutoEntry.setChecked(false);
                            break;
                    }
                }
            });

            // create and show the alert dialog
            AlertDialog dialog = builder.create();
            dialog.show();
        }else{
            CB_ReminderAddAutoEntry.setText(getString(R.string.fuel_add_cb_auto_entry));
        }
    }
}
