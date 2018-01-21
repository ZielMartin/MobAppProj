package de.fhbi.mobappproj.carlogger.listEntryAdapter;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Random;

import de.fhbi.mobappproj.carlogger.R;
import de.fhbi.mobappproj.carlogger.dataAccess.allCars.AllCars;

/**
 * Created by Johannes on 20.01.2018.
 */

public class CarCursorAdapter extends RecyclerViewCursorAdapter<CarCursorAdapter.CarViewHolder>
{
    private static final String TAG = CarCursorAdapter.class.getSimpleName();
    private final Context mContext;


    public CarCursorAdapter(Context context, String locationSetting)
    {
        super(null);
        mContext = context;


        /* example
        Cursor cursor = mContext.getContentResolver()
                .query(productForLocationUri, null, null, null, sortOrder);

        swapCursor(cursor);
         */


    }

    @Override
    public CarViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.list_item_car, parent, false);
        return new CarViewHolder(itemView, mContext);
    }

    @Override
    protected void onBindViewHolder(CarViewHolder holder, Cursor cursor)
    {
        holder.setDataOnView(cursor);
    }


    public static class CarViewHolder extends RecyclerView.ViewHolder
    {
        public TextView TV_ListItemName, TV_ListItemFuel, TV_ListItemPower, TV_ListItemcm3, TV_ListItemProductionYears, TV_ListItemHsn, TV_ListItemTsn;

        private Context mContext;


        CarViewHolder(View itemView, Context mContext)
        {
            super(itemView);
            this.mContext = mContext;
            TV_ListItemName = (TextView) itemView.findViewById(R.id.TV_ListItemName);
            TV_ListItemFuel = (TextView) itemView.findViewById(R.id.TV_ListItemFuel);
            TV_ListItemPower = (TextView) itemView.findViewById(R.id.TV_ListItemPower);
            TV_ListItemcm3 = (TextView) itemView.findViewById(R.id.TV_ListItemcm3);
            TV_ListItemProductionYears = (TextView) itemView.findViewById(R.id.TV_ListItemProductionYears);
            TV_ListItemHsn = (TextView) itemView.findViewById(R.id.TV_ListItemHsn);
            TV_ListItemTsn = (TextView) itemView.findViewById(R.id.TV_ListItemTsn);
        }

        public void setDataOnView(Cursor cursor) {
            //TODO: replace entry with cursor
//            AllCars entry = cursor; //???
//            TV_ListItemName.setText(entry.getName());
//            TV_ListItemFuel.setText(mContext.getString(R.string.car_info_fuel) + " " + entry.getKraftstoff());
//            TV_ListItemPower.setText(mContext.getString(R.string.car_info_power) + " " + entry.getPs());
//            TV_ListItemcm3.setText(mContext.getString(R.string.car_info_cm3) + " " + entry.getCm3());
//            TV_ListItemProductionYears.setText(entry.getBaujahre());
//            TV_ListItemHsn.setText(mContext.getString(R.string.car_info_hsn) + " " + entry.getHsn());
//            TV_ListItemTsn.setText(mContext.getString(R.string.car_info_tsn) + " " + entry.getTsn());


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO: handle item-click
                }
            });
        }
    }
}

