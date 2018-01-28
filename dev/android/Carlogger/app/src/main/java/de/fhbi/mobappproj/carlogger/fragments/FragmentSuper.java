package de.fhbi.mobappproj.carlogger.fragments;

import android.app.Fragment;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import de.fhbi.mobappproj.carlogger.listEntryAdapter.GenericViewHolder;

/**
 * Created by Johannes on 28.01.2018.
 */

public abstract class FragmentSuper <T extends RecyclerView.Adapter<? extends GenericViewHolder>> extends Fragment {
    public T mAdapter;

    protected void setStatValues(){};


}
