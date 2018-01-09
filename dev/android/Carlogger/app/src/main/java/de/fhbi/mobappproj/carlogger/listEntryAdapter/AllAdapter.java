package de.fhbi.mobappproj.carlogger.listEntryAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import de.fhbi.mobappproj.carlogger.DataClasses.AllEntryList;
import de.fhbi.mobappproj.carlogger.DataClasses.EntrySuper;
import de.fhbi.mobappproj.carlogger.DataClasses.FuelEntry;
import de.fhbi.mobappproj.carlogger.DataClasses.OtherCostEntry;
import de.fhbi.mobappproj.carlogger.DataClasses.ReminderEntry;
import de.fhbi.mobappproj.carlogger.DataClasses.RepairEntry;
import de.fhbi.mobappproj.carlogger.R;

/**
 * Created by Johannes on 26.12.2017.
 */

public class AllAdapter extends RecyclerView.Adapter<GenericViewHolder> {

    private ArrayList<? extends EntrySuper> entries;
    private Context mContext;
    private ViewGroup recyclerView;

    private static final int TYPE_REMINDER = 1;
    private static final int TYPE_REPAIR = 2;
    private static final int TYPE_OTHER = 3;
    private static final int TYPE_FUEL = 4;




    public AllAdapter(Context context, ArrayList<? extends EntrySuper> items) {
        GenericViewHolder.mExpandedPosition = -1;
        this.mContext = context;
        entries = items;
    }

    protected AllAdapter() {
    }

    @Override
    public int getItemViewType(int position) {
        entries = AllEntryList.getInstance().getAllEntries();
        if(entries.get(position) instanceof ReminderEntry){return TYPE_REMINDER;}
        if(entries.get(position) instanceof FuelEntry){return TYPE_FUEL;}
        if(entries.get(position) instanceof OtherCostEntry){return TYPE_OTHER;}
        if(entries.get(position) instanceof RepairEntry){return TYPE_REPAIR;}
        return 0;
    }


    @Override
    public GenericViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        //recyclerView = parent;

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());


        switch(viewType){
            case TYPE_FUEL :
                itemView = inflater.inflate(R.layout.list_item_fuel, parent, false);
                return new FuelAdapter.FuelViewHolder(itemView, mContext, this);
            case TYPE_OTHER :
                itemView = inflater.inflate(R.layout.list_item_other_cost, parent, false);
                return new OtherCostAdapter.OtherCostViewHolder(itemView, mContext, this);
            case TYPE_REMINDER :
                itemView = inflater.inflate(R.layout.list_item_reminder, parent, false);
                return new ReminderAdapter.ReminderViewHolder(itemView, mContext, this);
            case TYPE_REPAIR :
                itemView = inflater.inflate(R.layout.list_item_repair, parent, false);
                return new RepairAdapter.RepairViewHolder(itemView, mContext, this);
        }
        Log.d("onCreateViewHolderErr"," "+viewType);
        return null;



    }

    @Override
    public void onBindViewHolder(GenericViewHolder holder, int position) {
        holder.setDataOnView(position, entries.get(position));
    }




    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }



}
