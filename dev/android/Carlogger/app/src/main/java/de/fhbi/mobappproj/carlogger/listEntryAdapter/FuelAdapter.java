package de.fhbi.mobappproj.carlogger.listEntryAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import de.fhbi.mobappproj.carlogger.DataClasses.FuelEntry;
import de.fhbi.mobappproj.carlogger.R;
import de.fhbi.mobappproj.carlogger.activities.AddActivities.FuelAddActivity;

import static de.fhbi.mobappproj.carlogger.Helper.doubleToString;

/**
 * Created by Johannes on 26.12.2017.
 */

public class FuelAdapter extends RecyclerView.Adapter<FuelAdapter.ViewHolder> {

    private ArrayList<FuelEntry> entries;
    private Context mContext;
    private int mExpandedPosition;
    private ViewGroup recyclerView;


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView TV_ListItemFuelCost, TV_ListItemFuelAmount, TV_ListItemFuelDateTime, TV_ListItemFuelCostPerLitre, TV_ListItemFuelKM, TV_ListItemFuelFull, TV_ListItemFuelAutoEntry;
        public Button BTN_FuelEdit, BTN_FuelDelete;

        public ViewHolder(View v) {
            super(v);
            TV_ListItemFuelCost = (TextView) v.findViewById(R.id.TV_ListItemFuelCost);
            TV_ListItemFuelAmount = (TextView) v.findViewById(R.id.TV_ListItemFuelAmount);
            TV_ListItemFuelDateTime = (TextView) v.findViewById(R.id.TV_ListItemFuelDateTime);
            TV_ListItemFuelCostPerLitre = (TextView) v.findViewById(R.id.TV_ListItemFuelCostPerLitre);
            TV_ListItemFuelKM = (TextView) v.findViewById(R.id.TV_ListItemFuelKM);
            TV_ListItemFuelFull = (TextView) v.findViewById(R.id.TV_ListItemFuelFull);
            TV_ListItemFuelAutoEntry = (TextView) v.findViewById(R.id.TV_ListItemFuelAutoEntry);

            // Get Edit Button
            BTN_FuelEdit = (Button) v.findViewById(R.id.BTN_FuelEdit);
            //get delete Button
            BTN_FuelDelete = (Button) v.findViewById(R.id.BTN_FuelDelete);

        }
    }





    public FuelAdapter(Context context, ArrayList<FuelEntry> items) {
        this.mContext = context;
        entries = items;
        mExpandedPosition = -1;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_fuel, parent, false);

        recyclerView = parent;
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        FuelEntry entry = entries.get(position);

        holder.TV_ListItemFuelCost.setText(getFuelCost(entry)+" "+mContext.getString(R.string.euro));
        holder.TV_ListItemFuelAmount.setText(doubleToString(entry.getAmount())+" "+mContext.getString(R.string.litre));
        setDateTimeText(holder, entry);
        holder.TV_ListItemFuelCostPerLitre.setText(doubleToString(entry.getCostPerLitre())+" "+mContext.getString(R.string.euro_per_litre));
        holder.TV_ListItemFuelKM.setText(doubleToString(entry.getKm()));
        setFullText(holder, entry);
        setAutoEntryText(holder, entry);

        //configure expanding entry
        setUpExpandable(holder, position);


    }

    private void setDateTimeText(ViewHolder holder, FuelEntry entry) {
        //format date and time
        Calendar calendar = entry.getCreateTimeCalendar();
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd. MMM yyyy HH:mm");

        holder.TV_ListItemFuelDateTime.setText(dateFormat.format(calendar.getTime()));
    }

    private void setFullText(ViewHolder holder, FuelEntry entry) {
        if(entry.isFull()){
            holder.TV_ListItemFuelFull.setText(mContext.getString(R.string.fuel_add_cb_full)+": "+mContext.getString(R.string.yes));
        }else{
            holder.TV_ListItemFuelFull.setText(mContext.getString(R.string.fuel_add_cb_full)+": "+mContext.getString(R.string.no));
        }
    }

    private void setAutoEntryText(ViewHolder holder, FuelEntry entry) {
        if(entry.getAutoEntry()!=null) {
            switch (entry.getAutoEntry()) {
                case DAILY:
                    holder.TV_ListItemFuelAutoEntry.setText(mContext.getString(R.string.add_cb_auto_entry_with_time, mContext.getString(R.string.ad_auto_entry_daily)));
                    break;
                case WEEKLY:
                    holder.TV_ListItemFuelAutoEntry.setText(mContext.getString(R.string.add_cb_auto_entry_with_time, mContext.getString(R.string.ad_auto_entry_weekly)));
                    break;
                case MONTHLY:
                    holder.TV_ListItemFuelAutoEntry.setText(mContext.getString(R.string.add_cb_auto_entry_with_time, mContext.getString(R.string.ad_auto_entry_monthly)));
                    break;
            }
        }else{
            holder.TV_ListItemFuelAutoEntry.setText(mContext.getString(R.string.add_cb_auto_entry)+": "+mContext.getString(R.string.no));
        }
    }

    private String getFuelCost(FuelEntry entry){
        double fuelCost = entry.getCostPerLitre() * entry.getAmount();
        return doubleToString(fuelCost);
    }



    private void setUpExpandable(ViewHolder holder, int position) {
        //expand entry
        final boolean isExpanded = position==mExpandedPosition;
        holder.BTN_FuelEdit.setVisibility(isExpanded? View.VISIBLE:View.GONE);
        holder.BTN_FuelDelete.setVisibility(isExpanded? View.VISIBLE:View.GONE);
        holder.TV_ListItemFuelCostPerLitre.setVisibility(isExpanded? View.VISIBLE:View.GONE);
        holder.TV_ListItemFuelKM.setVisibility(isExpanded? View.VISIBLE:View.GONE);
        holder.TV_ListItemFuelFull.setVisibility(isExpanded? View.VISIBLE:View.GONE);
        holder.TV_ListItemFuelAutoEntry.setVisibility(isExpanded? View.VISIBLE:View.GONE);


        holder.itemView.setActivated(isExpanded);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpandedPosition = isExpanded ? -1:position;

                notifyDataSetChanged();
            }
        });
        setUpButtons(holder, position);


    }

    private void setUpButtons(ViewHolder holder, int position) {
        //action for edit button
        holder.BTN_FuelEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), FuelAddActivity.class);
                intent.putExtra("entry", entries.get(position));
                intent.putExtra("entryIndex", position);
                view.getContext().startActivity(intent);
            }
        });

        //action for delete Button
        holder.BTN_FuelDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                entries.remove(position);
                notifyDataSetChanged();
            }
        });
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
