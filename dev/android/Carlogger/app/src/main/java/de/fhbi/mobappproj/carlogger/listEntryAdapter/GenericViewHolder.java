package de.fhbi.mobappproj.carlogger.listEntryAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import de.fhbi.mobappproj.carlogger.DataClasses.entry.EntrySuper;

/**
 * Created by Johannes on 09.01.2018.
 */


public abstract class GenericViewHolder extends RecyclerView.ViewHolder{

    protected static int mExpandedPosition;


    public GenericViewHolder(View itemView) {
        super(itemView);
    }
    public abstract  void setDataOnView(int position, EntrySuper entry);

}
