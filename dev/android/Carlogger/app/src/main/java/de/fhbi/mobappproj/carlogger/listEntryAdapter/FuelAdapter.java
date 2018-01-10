package de.fhbi.mobappproj.carlogger.listEntryAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import de.fhbi.mobappproj.carlogger.DataClasses.EntrySuper;
import de.fhbi.mobappproj.carlogger.DataClasses.FuelEntry;
import de.fhbi.mobappproj.carlogger.R;
import de.fhbi.mobappproj.carlogger.activities.AddActivities.FuelAddActivity;

import static de.fhbi.mobappproj.carlogger.Helper.doubleToString;

/**
 * Created by Johannes on 09.01.2018.
 */
public class FuelAdapter extends RecyclerView.Adapter<FuelAdapter.FuelViewHolder> {

    private ArrayList<FuelEntry> entries;
    private Context mContext;

    public FuelAdapter(Context context, ArrayList<FuelEntry> items) {
        GenericViewHolder.mExpandedPosition = -1;
        this.mContext = context;
        entries = items;
    }
    @Override
    public FuelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.list_item_fuel, parent, false);
        return new FuelViewHolder(itemView, mContext, this);

    }

    @Override
    public void onBindViewHolder(FuelViewHolder holder, int position) {
        holder.setDataOnView(position, entries.get(position));
    }


    @Override
    public int getItemCount() {
        return entries.size();
    }

    public static class FuelViewHolder extends GenericViewHolder {

        public TextView TV_ListItemFuelCost, TV_ListItemFuelAmount, TV_ListItemFuelDateTime, TV_ListItemFuelCostPerLitre, TV_ListItemFuelKM, TV_ListItemFuelFull, TV_ListItemFuelAutoEntry;
        public Button BTN_FuelEdit, BTN_FuelDelete;


        private Context mContext;
        private RecyclerView.Adapter callback;

        private FuelEntry entry;

        public FuelViewHolder(View v, Context mContext, RecyclerView.Adapter callback) {
            super(v);
            this.mContext = mContext;
            this.callback = callback;
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

        @Override
        public void setDataOnView(int position, EntrySuper entrySuper) {
            this.entry = (FuelEntry) entrySuper;
            TV_ListItemFuelCost.setText(getFuelCost(entry) + " " + mContext.getString(R.string.euro));
            TV_ListItemFuelAmount.setText(doubleToString(entry.getAmount()) + " " + mContext.getString(R.string.litre));
            setDateTimeText(entry);
            TV_ListItemFuelCostPerLitre.setText(doubleToString(entry.getCostPerLitre()) + " " + mContext.getString(R.string.euro_per_litre));
            TV_ListItemFuelKM.setText(doubleToString(entry.getKm()));
            setFullText(entry);
            setAutoEntryText(entry);

            //configure expanding entry
            setUpExpandable(position);
        }

        private void setDateTimeText(FuelEntry entry) {
            //format date and time
            Calendar calendar = entry.getCreateTimeCalendar();
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "dd. MMM yyyy HH:mm");

            TV_ListItemFuelDateTime.setText(dateFormat.format(calendar.getTime()));
        }

        private void setFullText(FuelEntry entry) {
            if (entry.isFull()) {
                TV_ListItemFuelFull.setText(mContext.getString(R.string.fuel_add_cb_full) + ": " + mContext.getString(R.string.yes));
            } else {
                TV_ListItemFuelFull.setText(mContext.getString(R.string.fuel_add_cb_full) + ": " + mContext.getString(R.string.no));
            }
        }

        private void setAutoEntryText(FuelEntry entry) {
            if (entry.getAutoEntry() != null) {
                switch (entry.getAutoEntry()) {
                    case DAILY:
                        TV_ListItemFuelAutoEntry.setText(mContext.getString(R.string.add_cb_auto_entry_with_time, mContext.getString(R.string.ad_auto_entry_daily)));
                        break;
                    case WEEKLY:
                        TV_ListItemFuelAutoEntry.setText(mContext.getString(R.string.add_cb_auto_entry_with_time, mContext.getString(R.string.ad_auto_entry_weekly)));
                        break;
                    case MONTHLY:
                        TV_ListItemFuelAutoEntry.setText(mContext.getString(R.string.add_cb_auto_entry_with_time, mContext.getString(R.string.ad_auto_entry_monthly)));
                        break;
                }
            } else {
                TV_ListItemFuelAutoEntry.setText(mContext.getString(R.string.add_cb_auto_entry) + ": " + mContext.getString(R.string.no));
            }
        }

        private String getFuelCost(FuelEntry entry) {
            double fuelCost = entry.getCostPerLitre() * entry.getAmount();
            return doubleToString(fuelCost);
        }


        private void setUpExpandable(int position) {
            //expand entry
            final boolean isExpanded = position == mExpandedPosition;
            BTN_FuelEdit.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
            BTN_FuelDelete.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
            TV_ListItemFuelCostPerLitre.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
            TV_ListItemFuelKM.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
            TV_ListItemFuelFull.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
            TV_ListItemFuelAutoEntry.setVisibility(isExpanded ? View.VISIBLE : View.GONE);


            itemView.setActivated(isExpanded);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mExpandedPosition = isExpanded ? -1 : position;
                    Log.d("expandedPosition", "" + mExpandedPosition);
                    callback.notifyDataSetChanged();
                }
            });
            setUpButtons(position);


        }

        private void setUpButtons(int position) {
            //action for edit button
            BTN_FuelEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), FuelAddActivity.class);
                    intent.putExtra("entry", entry);
                    view.getContext().startActivity(intent);
                }
            });

            //action for delete Button
            BTN_FuelDelete.setOnClickListener(new View.OnClickListener() {
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