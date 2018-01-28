package de.fhbi.mobappproj.carlogger;

import android.app.Fragment;
import android.widget.Button;
import android.widget.TextView;

import com.borax12.materialdaterangepicker.date.DatePickerDialog;

import java.util.Calendar;

/**
 * Created by Johannes on 06.12.2017.
 * Creates DatePickerAlert Dialog and handles action
 * Must be called from a Fragment Instance
 */

public class DatePickerAlert implements DatePickerDialog.OnDateSetListener {

    private Button callingBTN;
    private Fragment callingFragment;
    private DatePickerDialogInterface callerFragmentInterface;
    private TextView callingTextview;

    /**
     * new DatePickerAlert
     * @param callingBTN            for clickListener
     * @param callingFragment       callback to access Data
     * @param callingTextview       TextView that should be updated
     */
    public DatePickerAlert(Button callingBTN, Fragment callingFragment, TextView callingTextview){
        this.callingFragment = callingFragment;
        this.callingTextview = callingTextview;
        callerFragmentInterface = (DatePickerDialogInterface) callingFragment;
        show();
    }

    /**
     * new DatePickerAlert
     * @param callingBTN            for clickListener
     * @param callingFragment       callback to access Data
     */
    public DatePickerAlert(Button callingBTN, Fragment callingFragment){
        this.callingFragment = callingFragment;
        show();
    }


    private void show() {
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

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
        if(this.callingTextview != null){

            Calendar start = Calendar.getInstance();
            start.set(year, monthOfYear, dayOfMonth);

            Calendar end = Calendar.getInstance();
            end.set(yearEnd, monthOfYearEnd, dayOfMonthEnd);

            if(start.getTimeInMillis() <= end.getTimeInMillis()){
                this.callerFragmentInterface.setPeriodCost(start, end);
                this.callingTextview.setText(callingFragment.getString(R.string.datePicker_periodWithDate, dayOfMonth, monthOfYear+1, year, dayOfMonthEnd, monthOfYearEnd+1, yearEnd));
            }else{
                this.callerFragmentInterface.setPeriodCost(end, start);
                this.callingTextview.setText(callingFragment.getString(R.string.datePicker_periodWithDate, dayOfMonthEnd, monthOfYearEnd+1, yearEnd, dayOfMonth, monthOfYear+1, year));
            }
        }
    }


}
