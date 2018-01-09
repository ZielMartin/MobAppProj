package de.fhbi.mobappproj.carlogger.fragments;

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


import java.util.ArrayList;

import de.fhbi.mobappproj.carlogger.DataClasses.AllEntryList;
import de.fhbi.mobappproj.carlogger.DataClasses.EntrySuper;
import de.fhbi.mobappproj.carlogger.R;
import de.fhbi.mobappproj.carlogger.listEntryAdapter.AllAdapter;


public class AllFragment extends Fragment implements View.OnClickListener {



    private OnFragmentInteractionListener mListener;

    private RecyclerView recyclerView;
    private AllAdapter mAdapter;

    private ArrayList<EntrySuper> entryList;

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
    }

    @Override
    public void onClick(View view) {

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
