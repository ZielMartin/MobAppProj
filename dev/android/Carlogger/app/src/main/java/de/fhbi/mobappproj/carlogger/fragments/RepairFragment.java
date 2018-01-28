package de.fhbi.mobappproj.carlogger.fragments;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Calendar;

import de.fhbi.mobappproj.carlogger.DataClasses.entry.RepairEntry;
import de.fhbi.mobappproj.carlogger.DataClasses.list.RepairEntryList;
import de.fhbi.mobappproj.carlogger.DatePickerAlert;
import de.fhbi.mobappproj.carlogger.DatePickerDialogInterface;
import de.fhbi.mobappproj.carlogger.R;
import de.fhbi.mobappproj.carlogger.listEntryAdapter.RepairAdapter;

import static de.fhbi.mobappproj.carlogger.Helper.buttonEffect;
import static de.fhbi.mobappproj.carlogger.Helper.doubleToString;


public class RepairFragment extends Fragment implements View.OnClickListener, DatePickerDialogInterface {

    private OnFragmentInteractionListener mListener;

    private RecyclerView recyclerView;
    private RepairAdapter mAdapter;

    private ArrayList<RepairEntry> entryList;

    private TextView TV_RepairTotalCostValue, TV_RepairMonthCostValue, TV_RepairPeriodCostValue;

    public RepairFragment() {
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
        View view = inflater.inflate(R.layout.fragment_repair, container, false);

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryRepair));
        getActivity().setTitle(R.string.repairFragment_title);

        //DatePickerAlert
        Button btn_DatePicker = (Button) view.findViewById(R.id.BTN_RepairDatePicker);
        btn_DatePicker.setOnClickListener(this);
        buttonEffect(btn_DatePicker);

        TV_RepairTotalCostValue = view.findViewById(R.id.TV_RepairTotalCostValue);
        TV_RepairMonthCostValue = view.findViewById(R.id.TV_RepairMonthCostValue);
        TV_RepairPeriodCostValue = view.findViewById(R.id.TV_RepairPeriodCostValue);


        initRecyclerView(view);

        return view;
    }

    private void initRecyclerView(View v){
        recyclerView = (RecyclerView) v.findViewById(R.id.RV_RepairFragment);

        entryList = RepairEntryList.getInstance().getAllEntries();

        // specify an adapter
        mAdapter = new RepairAdapter(getActivity(),entryList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));


        recyclerView.setAdapter(mAdapter);



    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
        setStatValues();

    }

    private void setStatValues() {
        TV_RepairTotalCostValue.setText(doubleToString(RepairEntryList.getInstance().getAllCosts()) + getString(R.string.euro));
        TV_RepairMonthCostValue.setText(doubleToString(RepairEntryList.getInstance().getCostPerMonth(Calendar.getInstance())) + getString(R.string.euro));
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.BTN_RepairDatePicker:
                //DatePickerAlert
                new DatePickerAlert(this.getView().findViewById(R.id.BTN_RepairDatePicker),
                        this,
                        (TextView) this.getView().findViewById(R.id.TV_RepairPeriodCost));
                break;
        }

    }

    @Override
    public void setPeriodCost(Calendar start, Calendar end) {
        TV_RepairPeriodCostValue.setText(doubleToString(RepairEntryList.getInstance().getCostTime(start, end)) + getString(R.string.euro));
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
