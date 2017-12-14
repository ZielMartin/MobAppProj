package de.fhbi.mobappproj.carlogger.activities.AddActivities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import de.fhbi.mobappproj.carlogger.R;

public class ReminderAdd extends AddActivitySuper {

    private EditText description;
    private FloatingActionButton fab;
    TimePicker timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initGUIElements() {
        fab = (FloatingActionButton) findViewById(R.id.fabReminderCheck);
        fab.setOnClickListener(this);

        //set 24h Format
        timePicker = (TimePicker) findViewById(R.id.reminderAddTimePicker);
        timePicker.setIs24HourView(true);

        description = (EditText) findViewById(R.id.ET_ReminderAddDescriptionValue);
        description.setOnFocusChangeListener(this);
    }

    @Override
    protected void setContentView() {
        super.setContentView(R.layout.activity_reminder_add);
    }

    @Override
    protected boolean checkInput() {
        String input = description.getText().toString();
        if(input == null || input.trim().equals("")) {
            Toast.makeText(this, getString(R.string.input_error), Toast.LENGTH_SHORT).show();
            description.setError("Bitte Beschreibung eingeben");
            description.requestFocus();
            return false;
        }
        return true;
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case(R.id.fabReminderCheck):
                //TODO: Save created Data on Firebase

                if(checkInput()){
                    finish();
                }
                break;
        }
    }
}
