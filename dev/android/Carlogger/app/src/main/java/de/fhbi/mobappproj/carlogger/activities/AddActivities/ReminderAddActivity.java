package de.fhbi.mobappproj.carlogger.activities.AddActivities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import de.fhbi.mobappproj.carlogger.DataClasses.entry.ReminderEntry;
import de.fhbi.mobappproj.carlogger.R;
import de.fhbi.mobappproj.carlogger.activities.MainActivity;
import de.fhbi.mobappproj.carlogger.reminderNotification.AlarmReceiver;
import de.fhbi.mobappproj.carlogger.reminderNotification.AlarmUtil;
import de.fhbi.mobappproj.carlogger.reminderNotification.NotificationUtil;

public class ReminderAddActivity extends AddActivitySuper implements CompoundButton.OnCheckedChangeListener {

    private EditText ET_Description;
    private FloatingActionButton fab;
    private TimePicker TP_TimePicker;
    private CheckBox CB_PushNotification;
    private DatePicker DP_DatePicker;

    private ReminderEntry editEntry;


    //how many hours before the Push-Notification should be made
    private int hoursNotification;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            hoursNotification = savedInstanceState.getInt("hoursNotification");
            TP_TimePicker.setCurrentHour(savedInstanceState.getInt("currHour"));
            TP_TimePicker.setCurrentMinute(savedInstanceState.getInt("currMin"));

            if(savedInstanceState.getString("cbText")!=null){
                CB_PushNotification.setText(savedInstanceState.getString("cbText"));
            }
        }else{
            //when editButton is pressed
            Bundle extras = getIntent().getExtras();
            if(extras != null){
                editEntry = extras.getParcelable("entry");
                setEditEntryValues(editEntry);
            }
        }
    }

    @Override
    protected void initGUIElements() {
        fab = (FloatingActionButton) findViewById(R.id.fabReminderCheck);
        fab.setOnClickListener(this);

        //set 24h Format
        TP_TimePicker = (TimePicker) findViewById(R.id.TP_ReminderAdd);
        TP_TimePicker.setIs24HourView(true);

        ET_Description = (EditText) findViewById(R.id.ET_ReminderAddDescriptionValue);
        ET_Description.setOnFocusChangeListener(this);

        CB_PushNotification = (CheckBox) findViewById(R.id.CB_ReminderPushNot);
        CB_PushNotification.setOnCheckedChangeListener(this);

        DP_DatePicker = (DatePicker) findViewById(R.id.DP_ReminderAdd);



    }

    @Override
    protected void contentView() {
        setContentView(R.layout.activity_reminder_add);
    }

    @Override
    protected boolean checkInput() {
        String descriptionInput = ET_Description.getText().toString();
        if(descriptionInput == null || descriptionInput.trim().equals("")) {
            Toast.makeText(this, getString(R.string.input_error), Toast.LENGTH_SHORT).show();
            ET_Description.setError(getString(R.string.error_description));
            ET_Description.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("cbText", (String) CB_PushNotification.getText().toString());
        outState.putInt("hoursNotification",hoursNotification);
        outState.putInt("currHour", TP_TimePicker.getCurrentHour());
        outState.putInt("currMin", TP_TimePicker.getCurrentMinute());

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case(R.id.fabReminderCheck):

                if(checkInput()){
                    //if this is an edit: edit the given entry
                    if(editEntry != null){
                        editEntry.setDateTime(getDateFromDatePicker(DP_DatePicker, TP_TimePicker));
                        editEntry.setDescription(ET_Description.getText().toString());
                        editEntry.setHoursNotification(hoursNotification);
                        editEntry.setPushNotification(CB_PushNotification.isChecked());
                        editEntry.updateEntry();


                        if(editEntry.isPushNotification()) {
                            Calendar calendar = (Calendar) editEntry.getDateTime().clone();
                            calendar.add(Calendar.HOUR, -hoursNotification);

                            AlarmUtil.setAlarm(this, editEntry, calendar);
                        }else{
                            AlarmUtil.cancelAlarm(this, editEntry);
                        }

                    }else {
                        // Save created Data on Firebase using DataClasses
                        ReminderEntry re = new ReminderEntry();
                        re.setDateTime(getDateFromDatePicker(DP_DatePicker, TP_TimePicker));
                        re.setDescription(ET_Description.getText().toString());
                        re.setHoursNotification(hoursNotification);
                        re.setPushNotification(CB_PushNotification.isChecked());
                        re.push();

                        if(re.isPushNotification()) {
                            Calendar calendar = (Calendar) re.getDateTime().clone();
                            calendar.add(Calendar.HOUR, -hoursNotification);



                            //Intent alarmIntent = new Intent(this, AlarmReceiver.class);
                            //alarmIntent.putExtra("reminder", re);

                            AlarmUtil.setAlarm(this, re, calendar);

                        }

                    }

                    finish();
                }
                break;
        }
    }

    //Push-Notification AlertDialog
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if ( b && hoursNotification == 0 )
        {
            alertDialogNotificationPicker();

        }if(!b){
            CB_PushNotification.setText(getString(R.string.reminder_cb_push));
            hoursNotification = 0;
        }
    }

    private void alertDialogNotificationPicker() {
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
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                CB_PushNotification.setChecked(false);
                CB_PushNotification.setText(getString(R.string.reminder_cb_push));
            }
        });

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private Calendar getDateFromDatePicker(DatePicker datePicker, TimePicker timePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();
        int hour = timePicker.getCurrentHour();
        int minute = timePicker.getCurrentMinute();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute);

        return calendar;
    }

    private void setEditEntryValues(ReminderEntry entry){
        ET_Description.setText(entry.getDescription());
        DP_DatePicker.updateDate(entry.getDateTime().get(Calendar.YEAR), entry.getDateTime().get(Calendar.MONTH), entry.getDateTime().get(Calendar.DAY_OF_MONTH));
        TP_TimePicker.setIs24HourView(true);
        TP_TimePicker.setCurrentHour(entry.getDateTime().get(Calendar.HOUR_OF_DAY));
        TP_TimePicker.setCurrentMinute(entry.getDateTime().get(Calendar.MINUTE));
        if(entry.getHoursNotification()>0){
            CB_PushNotification.setChecked(true);
        }else{
            CB_PushNotification.setChecked(false);
        }
    }


}
