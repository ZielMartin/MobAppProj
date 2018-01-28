package de.fhbi.mobappproj.carlogger.activities.AddActivities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import de.fhbi.mobappproj.carlogger.DataClasses.entry.Car;
import de.fhbi.mobappproj.carlogger.DataClasses.list.CarList;
import de.fhbi.mobappproj.carlogger.R;
import de.fhbi.mobappproj.carlogger.dataAccess.entryAccess.CarAccess;


public class CarAddActivity extends AddActivitySuper implements CompoundButton.OnCheckedChangeListener {


    private Car editEntry = null;
    private String name, key;

    private EditText ET_CarAddNameValue, ET_CarAddHsnTsnValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {


        } else {
            //when editButton is pressed
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                if (extras.getString("key") != null && extras.getString("name") != null) {
                    key = extras.getString("key");
                    name = extras.getString("name");
                } else {
                    editEntry = CarList.getInstance().getCars().get(extras.getInt("entryIndex"));
                }

            }
            setEditEntryValues();
        }
    }

    @Override
    protected void initGUIElements() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabCarCheck);
        fab.setOnClickListener(this);

        ET_CarAddNameValue = (EditText) findViewById(R.id.ET_CarAddNameValue);
        ET_CarAddNameValue.setOnFocusChangeListener(this);

        ET_CarAddHsnTsnValue = (EditText) findViewById(R.id.ET_CarAddHsnTsnValue);
        ET_CarAddHsnTsnValue.setOnFocusChangeListener(this);


    }

    @Override
    protected void contentView() {
        setContentView(R.layout.activity_car_add);
    }

    @Override
    protected boolean checkInput() {

        if (ET_CarAddHsnTsnValue.getText().toString().equals("")) {
            Toast.makeText(this, getString(R.string.input_error), Toast.LENGTH_SHORT).show();
            ET_CarAddHsnTsnValue.setError("bitte HSN/TSN eingeben");
            ET_CarAddHsnTsnValue.requestFocus();
            return false;
        }
        if (ET_CarAddNameValue.getText().toString().equals("")) {
            Toast.makeText(this, getString(R.string.input_error), Toast.LENGTH_SHORT).show();
            ET_CarAddNameValue.setError("bitte Name eingeben");
            ET_CarAddNameValue.requestFocus();
            return false;
        }

        return true;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.fabCarCheck):

                if (checkInput()) {
                    //if this is an edit: edit the given entry
                    if (editEntry != null) {
                        editEntry.setName(ET_CarAddNameValue.getText().toString());
                        editEntry.setHsntsn(ET_CarAddHsnTsnValue.getText().toString());
                        editEntry.update();
                    } else {
                        Car car = new Car();
                        CarList.getInstance().getCars().add(car);

                        car.setHsntsn(ET_CarAddHsnTsnValue.getText().toString());
                        car.setName(ET_CarAddNameValue.getText().toString());
                        car.pushToFirebase();

                        CarAccess.getInstance().setCurrentCar(car, this);
                    }
                    finish();
                }
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    protected void setEditEntryValues() {
        if (editEntry != null) {
            ET_CarAddNameValue.setText(editEntry.getName());
            ET_CarAddHsnTsnValue.setText(editEntry.getHsntsn());
        } else {
            ET_CarAddHsnTsnValue.setText(key);
            ET_CarAddNameValue.setText(name);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }
}




