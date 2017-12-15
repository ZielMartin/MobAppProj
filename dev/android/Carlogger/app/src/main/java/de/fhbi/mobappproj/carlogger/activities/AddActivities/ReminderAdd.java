package de.fhbi.mobappproj.carlogger.activities.AddActivities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import de.fhbi.mobappproj.carlogger.DataClasses.ReminderEntry;
import de.fhbi.mobappproj.carlogger.DataClasses.ReminderEntryList;
import de.fhbi.mobappproj.carlogger.R;

public class ReminderAdd extends AddActivitySuper implements CompoundButton.OnCheckedChangeListener {

    private EditText ET_Description;
    private FloatingActionButton fab;
    private TimePicker TP_TimePicker;
    private CheckBox CB_PushNotification;
    private DatePicker DP_DatePicker;

    //how many hours before the Push-Notification should be made
    private int hoursNotification;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initGUIElements() {
        fab = (FloatingActionButton) findViewById(R.id.fabReminderCheck);
        fab.setOnClickListener(this);

        //set 24h Format
        TP_TimePicker = (TimePicker) findViewById(R.id.reminderAddTimePicker);
        TP_TimePicker.setIs24HourView(true);

        ET_Description = (EditText) findViewById(R.id.ET_ReminderAddDescriptionValue);
        ET_Description.setOnFocusChangeListener(this);

        CB_PushNotification = (CheckBox) findViewById(R.id.CB_ReminderPushNot);
        CB_PushNotification.setOnCheckedChangeListener(this);

        DP_DatePicker = (DatePicker) findViewById(R.id.reminderAddDatePicker);



    }

    @Override
    protected void setContentView() {
        super.setContentView(R.layout.activity_reminder_add);
    }

    @Override
    protected boolean checkInput() {
        String input = ET_Description.getText().toString();
        if(input == null || input.trim().equals("")) {
            Toast.makeText(this, getString(R.string.input_error), Toast.LENGTH_SHORT).show();
            ET_Description.setError("Bitte Beschreibung eingeben");
            ET_Description.requestFocus();
            return false;
        }
        return true;
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case(R.id.fabReminderCheck):
                //TODO: Save created Data on Firebase
                ReminderEntry re = new ReminderEntry();
                re.setDateTime(getDateFromDatePicker(DP_DatePicker, TP_TimePicker));
                re.setDescription(ET_Description.getText().toString());
                re.setHoursNotification(hoursNotification);
                re.setPushNotification(CB_PushNotification.isChecked());


                if(checkInput()){
                    finish();
                }
                break;
        }
    }

    //Push-Notification
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if ( b )
        {
            // setup the alert builder
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.ad_push_notification_title);

            // add a list
            String[] values = {getString(R.string.ad_push_notification_1),
                                getString(R.string.ad_push_notification_2),
                                getString(R.string.ad_push_notification_3),
                                getString(R.string.ad_push_notification_24)};
            builder.setItems(values, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0: // 1h
                            CB_PushNotification.setText(getString(R.string.reminder_cb_push_withTime,getString(R.string.ad_push_notification_1)));
                            hoursNotification = 1;
                            break;
                        case 1: // 2h
                            CB_PushNotification.setText(getString(R.string.reminder_cb_push_withTime,getString(R.string.ad_push_notification_2)));
                            hoursNotification = 2;
                            break;
                        case 2: // 3h
                            CB_PushNotification.setText(getString(R.string.reminder_cb_push_withTime,getString(R.string.ad_push_notification_3)));
                            hoursNotification = 3;
                            break;
                        case 3: // 24h
                            CB_PushNotification.setText(getString(R.string.reminder_cb_push_withTime,getString(R.string.ad_push_notification_24)));
                            hoursNotification = 24;
                            break;
                        default:
                            CB_PushNotification.setChecked(false);
                            break;
                    }
                }
            });

            // create and show the alert dialog
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    public static java.util.Date getDateFromDatePicker(DatePicker datePicker, TimePicker timePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();
        int hour = timePicker.getCurrentHour();
        int minute = timePicker.getCurrentMinute();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute);

        return calendar.getTime();
    }


}
