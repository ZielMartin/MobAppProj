package de.fhbi.mobappproj.carlogger;

import android.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.borax12.materialdaterangepicker.date.DatePickerDialog;

import java.util.Calendar;

/**
 * Created by Johannes on 06.12.2017.
 * Creates DatePicker Dialog and handles action
 */

public class DatePicker implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private Button callingBTN;
    private Fragment callingFragment;
    private TextView callingTextview;
    private int callingLabelID;

    /**
     * new DatePicker
     * @param callingBTN            for clickListener
     * @param callingFragment       callback to access Data
     * @param callingTextview       TextView that should be updated
     */
    public DatePicker(Button callingBTN, Fragment callingFragment, TextView callingTextview){
        this.callingBTN = callingBTN;
        this.callingFragment = callingFragment;
        this.callingTextview = callingTextview;
        callingBTN.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == callingBTN.getId()) {
            Calendar now = Calendar.getInstance();
            DatePickerDialog dpd = com.borax12.materialdaterangepicker.date.DatePickerDialog.newInstance(
                    (DatePickerDialog.OnDateSetListener) this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            );
            dpd.setAutoHighlight(true);
            dpd.setStartTitle(callingFragment.getString(R.string.datePicker_from));
            dpd.setEndTitle(callingFragment.getString(R.string.datePicker_to));
            dpd.show(callingFragment.getFragmentManager(), "Datepickerdialog");


            }

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
        callingTextview.setText(callingFragment.getString(R.string.datePicker_periodWithDate, dayOfMonth, monthOfYear, year, dayOfMonthEnd, monthOfYearEnd, yearEnd));
    }


}
