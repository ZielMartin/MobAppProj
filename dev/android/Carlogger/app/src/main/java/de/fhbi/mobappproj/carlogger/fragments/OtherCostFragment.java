package de.fhbi.mobappproj.carlogger.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import de.fhbi.mobappproj.carlogger.DatePickerAlert;
import de.fhbi.mobappproj.carlogger.R;


public class OtherCostFragment extends Fragment implements View.OnClickListener{

    private OnFragmentInteractionListener mListener;

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

        return v;
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




