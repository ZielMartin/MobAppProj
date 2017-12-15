package de.fhbi.mobappproj.carlogger.fragments;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.app.AlertDialog;


import de.fhbi.mobappproj.carlogger.DatePickerAlert;
import de.fhbi.mobappproj.carlogger.R;

public class FuelFragment extends Fragment implements OnClickListener {

    private OnFragmentInteractionListener mListener;

    private TextView TV_PeriodCost;


    public FuelFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_fuel, container, false);


        Button btn_DatePicker = (Button) v.findViewById(R.id.BTN_FuelDatePicker);
        btn_DatePicker.setOnClickListener(this);

        //InfoButton
        Button btn_FuelInfo = (Button) v.findViewById(R.id.BTN_FuelConsumptionInfo);
        btn_FuelInfo.setOnClickListener(this);


        return v;
    }




    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.BTN_FuelConsumptionInfo:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(R.string.fuelDialogFragment_info)
                        .setPositiveButton(R.string.fuelDialogFragment_ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });

                // Create the AlertDialog object and return it
                builder.create();
                builder.show();
                break;
            case R.id.BTN_FuelDatePicker:
                //DatePickerAlert

                new DatePickerAlert(this.getView().findViewById(R.id.BTN_FuelDatePicker),
                        this,
                        (TextView) this.getView().findViewById(R.id.TV_FuelPeriodCost));
                break;
        }
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}