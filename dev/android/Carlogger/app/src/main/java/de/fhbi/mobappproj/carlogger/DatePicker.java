package de.fhbi.mobappproj.carlogger;

import android.app.Fragment;
import android.content.res.Resources;
import android.view.View;
import android.widget.Button;

import de.fhbi.mobappproj.carlogger.R;

import com.borax12.materialdaterangepicker.date.DatePickerDialog;

import java.util.Calendar;

/**
 * Created by Johannes on 06.12.2017.
 * Creates DatePicker Dialog
 */

public class DatePicker implements View.OnClickListener {

    private Button callingBTN;
    private Fragment callingFragment;

    public DatePicker(Button callingBTN, Fragment callingFragment){
        this.callingBTN = callingBTN;
        this.callingFragment = callingFragment;
        callingBTN.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == callingBTN.getId()) {
            Calendar now = Calendar.getInstance();
            DatePickerDialog dpd = com.borax12.materialdaterangepicker.date.DatePickerDialog.newInstance(
                    (DatePickerDialog.OnDateSetListener) callingFragment,
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


}
