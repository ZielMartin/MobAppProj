package de.fhbi.mobappproj.carlogger.fragments;

import android.content.Context;
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
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Calendar;

import de.fhbi.mobappproj.carlogger.DataClasses.OtherCostEntry;
import de.fhbi.mobappproj.carlogger.DataClasses.OtherCostEntryList;
import de.fhbi.mobappproj.carlogger.DatePickerAlert;
import de.fhbi.mobappproj.carlogger.DatePickerDialogUserInterface;
import de.fhbi.mobappproj.carlogger.R;
import de.fhbi.mobappproj.carlogger.listEntryAdapter.OtherCostAdapter;

import static de.fhbi.mobappproj.carlogger.Helper.buttonEffect;
import static de.fhbi.mobappproj.carlogger.Helper.doubleToString;


public class OtherCostFragment extends Fragment implements View.OnClickListener, DatePickerDialogUserInterface {

    private OnFragmentInteractionListener mListener;

    private RecyclerView recyclerView;
    private OtherCostAdapter mAdapter;

    private ArrayList<OtherCostEntry> entryList;

    private TextView TV_OtherTotalCostValue, TV_OtherMonthCostValue, TV_OtherPeriodCostValue;

    public OtherCostFragment() {
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
        View v = inflater.inflate(R.layout.fragment_other_cost, container, false);

        Button btn_DatePicker = (Button) v.findViewById(R.id.BTN_OtherDatePicker);
        btn_DatePicker.setOnClickListener(this);
        buttonEffect(btn_DatePicker);

        TV_OtherTotalCostValue = v.findViewById(R.id.TV_OtherTotalCostValue);
        TV_OtherMonthCostValue = v.findViewById(R.id.TV_OtherMonthCostValue);
        TV_OtherPeriodCostValue = v.findViewById(R.id.TV_OtherPeriodCostValue);

        initRecyclerView(v);

        return v;
    }

    private void initRecyclerView(View v){
        recyclerView = (RecyclerView) v.findViewById(R.id.RV_OtherFragment);

        entryList = OtherCostEntryList.getInstance().getAllEntries();

        // specify an adapter
        mAdapter = new OtherCostAdapter(getActivity(),entryList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));


        recyclerView.setAdapter(mAdapter);
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

            case R.id.BTN_OtherDatePicker:
            //DatePickerAlert
            new DatePickerAlert(this.getView().findViewById(R.id.BTN_OtherDatePicker),
                this,
                (TextView) this.getView().findViewById(R.id.TV_OtherPeriodCost));
            break;
    }

    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
        setStatValues();

    }

    private void setStatValues() {
        TV_OtherTotalCostValue.setText(doubleToString(OtherCostEntryList.getInstance().getAllCosts()) + getString(R.string.euro));
        TV_OtherMonthCostValue.setText(doubleToString(OtherCostEntryList.getInstance().getCostPerMonth(Calendar.getInstance())) + getString(R.string.euro));
    }

    @Override
    public void setPeriodCost(Calendar start, Calendar end) {
        TV_OtherPeriodCostValue.setText(doubleToString(OtherCostEntryList.getInstance().getCostTime(start, end)) + getString(R.string.euro));
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




