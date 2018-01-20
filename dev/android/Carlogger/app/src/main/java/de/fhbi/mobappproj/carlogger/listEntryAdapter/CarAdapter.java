package de.fhbi.mobappproj.carlogger.listEntryAdapter;

import android.content.Context;
import android.content.Intent;
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
import java.util.List;

import de.fhbi.mobappproj.carlogger.DataClasses.entry.EntrySuper;
import de.fhbi.mobappproj.carlogger.DataClasses.entry.FuelEntry;
import de.fhbi.mobappproj.carlogger.DataClasses.list.FuelEntryList;
import de.fhbi.mobappproj.carlogger.R;
import de.fhbi.mobappproj.carlogger.activities.AddActivities.FuelAddActivity;
import de.fhbi.mobappproj.carlogger.dataAccess.allCars.AllCars;

import static de.fhbi.mobappproj.carlogger.Helper.doubleToString;

/**
 * Created by Johannes on 09.01.2018.
 */
public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {

    private List<AllCars> entries;
    private Context mContext;



    public CarAdapter(Context context, List<AllCars> items) {
        this.mContext = context;
        entries = items;
    }
    @Override
    public CarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.list_item_car, parent, false);
        return new CarViewHolder(itemView, mContext, this);

    }

    @Override
    public void onBindViewHolder(CarViewHolder holder, int position) {
        holder.setDataOnView(position, entries.get(position));
    }


    @Override
    public int getItemCount() {
        return entries.size();
    }




    public static class CarViewHolder extends RecyclerView.ViewHolder {

        public TextView TV_ListItemName, TV_ListItemFuel, TV_ListItemPower, TV_ListItemcm3, TV_ListItemProductionYears, TV_ListItemHsn, TV_ListItemTsn;



        private Context mContext;
        private RecyclerView.Adapter callback;

        protected static int mExpandedPosition;

        private AllCars entry;

        public CarViewHolder(View v, Context mContext, RecyclerView.Adapter callback) {
            super(v);
            this.mContext = mContext;
            this.callback = callback;
            TV_ListItemName = (TextView) v.findViewById(R.id.TV_ListItemName);
            TV_ListItemFuel = (TextView) v.findViewById(R.id.TV_ListItemFuel);
            TV_ListItemPower = (TextView) v.findViewById(R.id.TV_ListItemPower);
            TV_ListItemcm3 = (TextView) v.findViewById(R.id.TV_ListItemcm3);
            TV_ListItemProductionYears = (TextView) v.findViewById(R.id.TV_ListItemProductionYears);
            TV_ListItemHsn = (TextView) v.findViewById(R.id.TV_ListItemHsn);
            TV_ListItemTsn = (TextView) v.findViewById(R.id.TV_ListItemTsn);

        }


        public void setDataOnView(int position, AllCars entry) {
            this.entry = entry;
            TV_ListItemName.setText(entry.getName());
            TV_ListItemFuel.setText(mContext.getString(R.string.car_info_fuel) + " " + entry.getKraftstoff());
            TV_ListItemPower.setText(mContext.getString(R.string.car_info_power) + " " + entry.getPs());
            TV_ListItemcm3.setText(mContext.getString(R.string.car_info_cm3) + " " + entry.getCm3());
            TV_ListItemProductionYears.setText(entry.getBaujahre());
            TV_ListItemHsn.setText(mContext.getString(R.string.car_info_hsn) + " " + entry.getHsn());
            TV_ListItemTsn.setText(mContext.getString(R.string.car_info_tsn) + " " + entry.getTsn());


            //configure expanding entry
            setUpExpandable(position);
        }




        private void setUpExpandable(int position) {
            //expand entry
            final boolean isExpanded = position == mExpandedPosition;
            TV_ListItemTsn.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
            TV_ListItemHsn.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
            TV_ListItemProductionYears.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

            itemView.setActivated(isExpanded);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mExpandedPosition = isExpanded ? -1 : position;
                    Log.d("expandedPosition", "" + mExpandedPosition);
                    callback.notifyDataSetChanged();
                }
            });


        }



    }
}