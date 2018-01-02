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
import de.fhbi.mobappproj.carlogger.DataClasses.OtherCostEntry;
import de.fhbi.mobappproj.carlogger.R;
import de.fhbi.mobappproj.carlogger.activities.AddActivities.FuelAddActivity;
import de.fhbi.mobappproj.carlogger.activities.AddActivities.OtherCostAddActivity;

import static de.fhbi.mobappproj.carlogger.Helper.doubleToString;

/**
 * Created by Johannes on 26.12.2017.
 */

public class OtherCostAdapter extends RecyclerView.Adapter<OtherCostAdapter.ViewHolder> {

    private ArrayList<OtherCostEntry> entries;
    private Context mContext;
    private int mExpandedPosition;
    private ViewGroup recyclerView;


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView TV_ListItemOtherCost, TV_ListItemOtherDateTime, TV_ListItemOtherDescription, TV_ListItemOtherAutoEntry;
        public Button BTN_OtherEdit, BTN_OtherDelete;

        public ViewHolder(View v) {
            super(v);
            TV_ListItemOtherCost = (TextView) v.findViewById(R.id.TV_ListItemOtherCost);
            TV_ListItemOtherDateTime = (TextView) v.findViewById(R.id.TV_ListItemOtherDateTime);
            TV_ListItemOtherDescription = (TextView) v.findViewById(R.id.TV_ListItemOtherDescription);
            TV_ListItemOtherAutoEntry = (TextView) v.findViewById(R.id.TV_ListItemOtherAutoEntry);

            // Get Edit Button
            BTN_OtherEdit = (Button) v.findViewById(R.id.BTN_OtherEdit);
            //get delete Button
            BTN_OtherDelete = (Button) v.findViewById(R.id.BTN_OtherDelete);
        }
    }





    public OtherCostAdapter(Context context, ArrayList<OtherCostEntry> items) {
        this.mContext = context;
        entries = items;
        mExpandedPosition = -1;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_other_cost, parent, false);

        recyclerView = parent;
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        OtherCostEntry entry = entries.get(position);

        holder.TV_ListItemOtherCost.setText(doubleToString(entry.getCost())+" "+mContext.getString(R.string.euro));
        holder.TV_ListItemOtherDescription.setText(entry.getDescription());
        setDateTimeText(holder, entry);


        setAutoEntryText(holder, entry);

        //configure expanding entry
        setUpExpandable(holder, position);


    }

    private void setDateTimeText(ViewHolder holder, OtherCostEntry entry) {
        //format date and time
        Calendar calendar = entry.getCreateTimeCalendar();
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd. MMM yyyy HH:mm");

        holder.TV_ListItemOtherDateTime.setText(dateFormat.format(calendar.getTime()));
    }



    private void setAutoEntryText(ViewHolder holder, OtherCostEntry entry) {
        if(entry.getAutoEntry()!=null) {
            switch (entry.getAutoEntry()) {
                case DAILY:
                    holder.TV_ListItemOtherAutoEntry.setText(mContext.getString(R.string.add_cb_auto_entry_with_time, mContext.getString(R.string.ad_auto_entry_daily)));
                    break;
                case WEEKLY:
                    holder.TV_ListItemOtherAutoEntry.setText(mContext.getString(R.string.add_cb_auto_entry_with_time, mContext.getString(R.string.ad_auto_entry_weekly)));
                    break;
                case MONTHLY:
                    holder.TV_ListItemOtherAutoEntry.setText(mContext.getString(R.string.add_cb_auto_entry_with_time, mContext.getString(R.string.ad_auto_entry_monthly)));
                    break;
                case YEARLY:
                    holder.TV_ListItemOtherAutoEntry.setText(mContext.getString(R.string.add_cb_auto_entry_with_time, mContext.getString(R.string.ad_auto_entry_yearly)));
                    break;
                case EVERYTWOYEAR:
                    holder.TV_ListItemOtherAutoEntry.setText(mContext.getString(R.string.add_cb_auto_entry_with_time, mContext.getString(R.string.ad_auto_entry_everytwoyear)));
                    break;
            }
        }else{
            holder.TV_ListItemOtherAutoEntry.setText(mContext.getString(R.string.add_cb_auto_entry)+": "+mContext.getString(R.string.no));
        }
    }

    private String getFuelCost(FuelEntry entry){
        double fuelCost = entry.getCostPerLitre() * entry.getAmount();
        return doubleToString(fuelCost);
    }



    private void setUpExpandable(ViewHolder holder, int position) {
        //expand entry
        final boolean isExpanded = position==mExpandedPosition;
        holder.BTN_OtherEdit.setVisibility(isExpanded? View.VISIBLE:View.GONE);
        holder.BTN_OtherDelete.setVisibility(isExpanded? View.VISIBLE:View.GONE);
        holder.TV_ListItemOtherDescription.setVisibility(isExpanded? View.VISIBLE:View.GONE);
        holder.TV_ListItemOtherAutoEntry.setVisibility(isExpanded? View.VISIBLE:View.GONE);

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
        holder.BTN_OtherEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), OtherCostAddActivity.class);
                intent.putExtra("entry", entries.get(position));
                intent.putExtra("entryIndex", position);
                view.getContext().startActivity(intent);
            }
        });

        //action for delete Button
        holder.BTN_OtherDelete.setOnClickListener(new View.OnClickListener() {
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
