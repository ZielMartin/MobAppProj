package de.fhbi.mobappproj.carlogger.fragments;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.app.AlertDialog;


import java.util.ArrayList;
import java.util.Calendar;

import de.fhbi.mobappproj.carlogger.DataClasses.entry.FuelEntry;
import de.fhbi.mobappproj.carlogger.DataClasses.list.FuelEntryList;
import de.fhbi.mobappproj.carlogger.DatePickerAlert;
import de.fhbi.mobappproj.carlogger.DatePickerDialogUserInterface;
import de.fhbi.mobappproj.carlogger.R;
import de.fhbi.mobappproj.carlogger.listEntryAdapter.FuelAdapter;

import static de.fhbi.mobappproj.carlogger.Helper.buttonEffect;
import static de.fhbi.mobappproj.carlogger.Helper.doubleToString;

public class FuelFragment extends Fragment implements OnClickListener, DatePickerDialogUserInterface {

    private OnFragmentInteractionListener mListener;

    private RecyclerView recyclerView;
    private FuelAdapter mAdapter;

    private ArrayList<FuelEntry> entryList;

    private TextView TV_FuelTotalCostValue, TV_FuelMonthCostValue, TV_FuelPeriodCostValue, TV_FuelConsumptionValue;



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
        buttonEffect(btn_DatePicker);

        //InfoButton
        Button btn_FuelInfo = (Button) v.findViewById(R.id.BTN_FuelConsumptionInfo);
        btn_FuelInfo.setOnClickListener(this);
        buttonEffect(btn_FuelInfo);

        TV_FuelPeriodCostValue = v.findViewById(R.id.TV_FuelPeriodCostValue);
        TV_FuelTotalCostValue = v.findViewById(R.id.TV_FuelTotalCostValue);
        TV_FuelMonthCostValue = v.findViewById(R.id.TV_FuelMonthCostValue);
        TV_FuelConsumptionValue = v.findViewById(R.id.TV_FuelConsumptionValue);

        initRecyclerView(v);



        return v;
    }

    private void setStatValues() {

        TV_FuelTotalCostValue.setText(doubleToString(FuelEntryList.getInstance().getAllCosts()) + getString(R.string.euro));
        TV_FuelMonthCostValue.setText(doubleToString(FuelEntryList.getInstance().getCostPerMonth(Calendar.getInstance())) + getString(R.string.euro));
        TV_FuelConsumptionValue.setText(doubleToString(FuelEntryList.getInstance().getConsumtion()) + getString(R.string.euro));


    }
    public void setPeriodCost(Calendar start, Calendar end){
        TV_FuelPeriodCostValue.setText(doubleToString(FuelEntryList.getInstance().getCostTime(start, end)) + getString(R.string.euro));
    }

    private void initRecyclerView(View v){
        recyclerView = (RecyclerView) v.findViewById(R.id.RV_FuelFragment);

        entryList = FuelEntryList.getInstance().getAllEntries();

        // specify an adapter
        mAdapter = new FuelAdapter(getActivity(),entryList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));


        recyclerView.setAdapter(mAdapter);
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

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
        setStatValues();
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