package de.fhbi.mobappproj.carlogger.fragments;

import android.app.AlertDialog;
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
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Calendar;

import de.fhbi.mobappproj.carlogger.DataClasses.AllEntryList;
import de.fhbi.mobappproj.carlogger.DataClasses.EntrySuper;
import de.fhbi.mobappproj.carlogger.DatePickerAlert;
import de.fhbi.mobappproj.carlogger.DatePickerDialogUserInterface;
import de.fhbi.mobappproj.carlogger.R;
import de.fhbi.mobappproj.carlogger.listEntryAdapter.AllAdapter;

import static de.fhbi.mobappproj.carlogger.Helper.buttonEffect;
import static de.fhbi.mobappproj.carlogger.Helper.doubleToString;


public class AllFragment extends Fragment implements View.OnClickListener, DatePickerDialogUserInterface {



    private OnFragmentInteractionListener mListener;

    private RecyclerView recyclerView;
    private AllAdapter mAdapter;

    private ArrayList<EntrySuper> entryList;

    private TextView TV_AllTotalCostValue, TV_AllMonthCostValue, TV_AllPeriodCostValue;

    public AllFragment() {
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
        View v = inflater.inflate(R.layout.fragment_all, container, false);

        Button btn_DatePicker = (Button) v.findViewById(R.id.BTN_AllDatePicker);
        btn_DatePicker.setOnClickListener(this);
        buttonEffect(btn_DatePicker);

        TV_AllTotalCostValue = v.findViewById(R.id.TV_AllTotalCostValue);
        TV_AllMonthCostValue = v.findViewById(R.id.TV_AllMonthCostValue);
        TV_AllPeriodCostValue = v.findViewById(R.id.TV_AllPeriodCostValue);


        initRecyclerView(v);

        return v;
    }

    private void initRecyclerView(View v){
        recyclerView = (RecyclerView) v.findViewById(R.id.RV_AllFragment);

        entryList = AllEntryList.getInstance().getAllEntries();

        // specify an adapter
        mAdapter = new AllAdapter(getActivity(), entryList);

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
    public void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
        setStatValues();
    }

    private void setStatValues() {
        TV_AllTotalCostValue.setText(doubleToString(AllEntryList.getInstance().getAllCosts()) + getString(R.string.euro));
        TV_AllMonthCostValue.setText(doubleToString(AllEntryList.getInstance().getCostPerMonth()) + getString(R.string.euro));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.BTN_AllDatePicker:
                //DatePickerAlert

                new DatePickerAlert(this.getView().findViewById(R.id.BTN_AllDatePicker),
                        this,
                        (TextView) this.getView().findViewById(R.id.TV_AllPeriodCost));
                break;
        }
    }

    @Override
    public void setPeriodCost(Calendar start, Calendar end) {
        TV_AllPeriodCostValue.setText(doubleToString(AllEntryList.getInstance().getCostTime(start, end)) +getString(R.string.euro));

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
