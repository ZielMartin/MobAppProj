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

import de.fhbi.mobappproj.carlogger.DataClasses.entry.EntrySuper;
import de.fhbi.mobappproj.carlogger.DataClasses.entry.FuelEntry;
import de.fhbi.mobappproj.carlogger.DataClasses.entry.OtherCostEntry;
import de.fhbi.mobappproj.carlogger.DataClasses.list.OtherCostEntryList;
import de.fhbi.mobappproj.carlogger.R;
import de.fhbi.mobappproj.carlogger.activities.AddActivities.OtherCostAddActivity;

import static de.fhbi.mobappproj.carlogger.Helper.doubleToString;

/**
 * Created by Johannes on 26.12.2017.
 */

public class OtherCostAdapter extends RecyclerView.Adapter<OtherCostAdapter.OtherCostViewHolder> {

    private ArrayList<OtherCostEntry> entries;
    private Context mContext;
    private ViewGroup recyclerView;

    public OtherCostAdapter(Context context, ArrayList<OtherCostEntry> items) {
        GenericViewHolder.mExpandedPosition = -1;
        this.mContext = context;
        entries = items;
    }

    @Override
    public OtherCostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.list_item_other_cost, parent, false);
        return new OtherCostViewHolder(itemView, mContext, this);

    }

    @Override
    public void onBindViewHolder(OtherCostViewHolder holder, int position) {
        holder.setDataOnView(position, entries.get(position));
    }



    @Override
    public int getItemCount() {
        return entries.size();
    }

    public static class OtherCostViewHolder extends GenericViewHolder {

        public TextView TV_ListItemOtherCost, TV_ListItemOtherDateTime, TV_ListItemOtherDescription, TV_ListItemOtherAutoEntry;
        public Button BTN_OtherEdit, BTN_OtherDelete;

        private OtherCostEntry entry;
        private Context mContext;

        private RecyclerView.Adapter callback;

        public OtherCostViewHolder(View v, Context mContext, RecyclerView.Adapter callback) {
            super(v);
            this.mContext = mContext;
            this.callback = callback;
            TV_ListItemOtherCost = (TextView) v.findViewById(R.id.TV_ListItemOtherCost);
            TV_ListItemOtherDateTime = (TextView) v.findViewById(R.id.TV_ListItemOtherDateTime);
            TV_ListItemOtherDescription = (TextView) v.findViewById(R.id.TV_ListItemOtherDescription);
            TV_ListItemOtherAutoEntry = (TextView) v.findViewById(R.id.TV_ListItemOtherAutoEntry);

            // Get Edit Button
            BTN_OtherEdit = (Button) v.findViewById(R.id.BTN_OtherEdit);
            //get delete Button
            BTN_OtherDelete = (Button) v.findViewById(R.id.BTN_OtherDelete);
        }

        @Override
        public void setDataOnView(int position, EntrySuper entrySuper) {

            this.entry = (OtherCostEntry) entrySuper;

            TV_ListItemOtherCost.setText(doubleToString(entry.getCost()) + " " + mContext.getString(R.string.euro));
            TV_ListItemOtherDescription.setText(entry.getDescription());
            setDateTimeText(entry);


            setAutoEntryText(entry);

            //configure expanding entry
            setUpExpandable(position);
        }

        private void setDateTimeText(OtherCostEntry entry) {
            //format date and time
            Calendar calendar = entry.getCreateTimeCalendar();
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "dd. MMM yyyy HH:mm");

            TV_ListItemOtherDateTime.setText(dateFormat.format(calendar.getTime()));
        }

        private void setAutoEntryText(OtherCostEntry entry) {
            if (entry.getAutoEntry() != null) {
                switch (entry.getAutoEntry()) {
                    case DAILY:
                        TV_ListItemOtherAutoEntry.setText(mContext.getString(R.string.add_cb_auto_entry_with_time, mContext.getString(R.string.ad_auto_entry_daily)));
                        break;
                    case WEEKLY:
                        TV_ListItemOtherAutoEntry.setText(mContext.getString(R.string.add_cb_auto_entry_with_time, mContext.getString(R.string.ad_auto_entry_weekly)));
                        break;
                    case MONTHLY:
                        TV_ListItemOtherAutoEntry.setText(mContext.getString(R.string.add_cb_auto_entry_with_time, mContext.getString(R.string.ad_auto_entry_monthly)));
                        break;
                    case YEARLY:
                        TV_ListItemOtherAutoEntry.setText(mContext.getString(R.string.add_cb_auto_entry_with_time, mContext.getString(R.string.ad_auto_entry_yearly)));
                        break;
                    case EVERYTWOYEAR:
                        TV_ListItemOtherAutoEntry.setText(mContext.getString(R.string.add_cb_auto_entry_with_time, mContext.getString(R.string.ad_auto_entry_everytwoyear)));
                        break;
                }
            } else {
                TV_ListItemOtherAutoEntry.setText(mContext.getString(R.string.add_cb_auto_entry) + ": " + mContext.getString(R.string.no));
            }
        }

        private String getFuelCost(FuelEntry entry) {
            double fuelCost = entry.getCostPerLitre() * entry.getAmount();
            return doubleToString(fuelCost);
        }


        private void setUpExpandable(int position) {
            //expand entry
            final boolean isExpanded = position == mExpandedPosition;
            BTN_OtherEdit.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
            BTN_OtherDelete.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
            TV_ListItemOtherDescription.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
            TV_ListItemOtherAutoEntry.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

            itemView.setActivated(isExpanded);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mExpandedPosition = isExpanded ? -1 : position;

                    callback.notifyDataSetChanged();
                }
            });
            setUpButtons(position);


        }

        private void setUpButtons(int position) {
            //action for edit button
            BTN_OtherEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), OtherCostAddActivity.class);
                    intent.putExtra("entryIndex", OtherCostEntryList.getInstance().getAllEntries().indexOf(entry));
                    view.getContext().startActivity(intent);
                }
            });

            //action for delete Button
            BTN_OtherDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    entry.removeEntry();
                    callback.notifyDataSetChanged();
                    mExpandedPosition = -1;
                }
            });
        }
    }
}