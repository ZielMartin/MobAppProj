package de.fhbi.mobappproj.carlogger.listEntryAdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import de.fhbi.mobappproj.carlogger.DataClasses.FuelEntry;
import de.fhbi.mobappproj.carlogger.DataClasses.RepairEntry;
import de.fhbi.mobappproj.carlogger.R;
import de.fhbi.mobappproj.carlogger.activities.AddActivities.FuelAddActivity;
import de.fhbi.mobappproj.carlogger.activities.AddActivities.RepairAddActivity;

import static de.fhbi.mobappproj.carlogger.Helper.doubleToString;

/**
 * Created by Johannes on 26.12.2017.
 */

public class RepairAdapter extends RecyclerView.Adapter<RepairAdapter.ViewHolder> {

    private ArrayList<RepairEntry> entries;
    private Context mContext;
    private int mExpandedPosition;
    private ViewGroup recyclerView;


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView TV_ListItemRepairType, TV_ListItemRepairCost, TV_ListItemRepairDateTime, TV_ListItemRepairLaborCost, TV_ListItemRepairPartCost, TV_ListItemRepairDescription, TV_ListItemRepairAutoEntry;
        public ImageView IV_ListItemRepairBill;
        public Button BTN_RepairEdit, BTN_RepairDelete;

        public ViewHolder(View v) {
            super(v);
            TV_ListItemRepairType = (TextView) v.findViewById(R.id.TV_ListItemRepairType);
            TV_ListItemRepairCost = (TextView) v.findViewById(R.id.TV_ListItemRepairCost);
            TV_ListItemRepairDateTime = (TextView) v.findViewById(R.id.TV_ListItemRepairDateTime);
            TV_ListItemRepairLaborCost = (TextView) v.findViewById(R.id.TV_ListItemRepairLaborCost);
            TV_ListItemRepairPartCost = (TextView) v.findViewById(R.id.TV_ListItemRepairPartCost);
            TV_ListItemRepairDescription = (TextView) v.findViewById(R.id.TV_ListItemRepairDescription);
            TV_ListItemRepairAutoEntry = (TextView) v.findViewById(R.id.TV_ListItemRepairAutoEntry);

            IV_ListItemRepairBill = (ImageView) v.findViewById(R.id.IV_ListItemRepairBill);

            // Get Edit Button
            BTN_RepairEdit = (Button) v.findViewById(R.id.BTN_RepairEdit);
            //get delete Button
            BTN_RepairDelete = (Button) v.findViewById(R.id.BTN_RepairDelete);

        }
    }





    public RepairAdapter(Context context, ArrayList<RepairEntry> items) {
        this.mContext = context;
        entries = items;
        mExpandedPosition = -1;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_repair, parent, false);

        recyclerView = parent;
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        RepairEntry entry = entries.get(position);

        holder.TV_ListItemRepairType.setText(entry.getType());
        holder.TV_ListItemRepairCost.setText(doubleToString(entry.getLaborCost()+entry.getPartCost()) + " " + mContext.getString(R.string.euro));
        setDateTimeText(holder, entry);
        holder.TV_ListItemRepairLaborCost.setText(mContext.getString(R.string.repair_add_labor_cost) + ": " + doubleToString(entry.getLaborCost()));
        holder.TV_ListItemRepairPartCost.setText(mContext.getString(R.string.repair_add_part_cost) + ": " + doubleToString(entry.getPartCost()));
        holder.TV_ListItemRepairDescription.setText(entry.getDescription());
        setPic(holder.IV_ListItemRepairBill, entry);
        setAutoEntryText(holder, entry);

        //configure expanding entry
        setUpExpandable(holder, position);


    }

    private void setPic(ImageView iv, RepairEntry entry) {

        if(entry.getBill()!=null) {
            // Get the dimensions of the View
            int targetW = iv.getMaxWidth();
            int targetH = iv.getMaxHeight();

            // Get the dimensions of the bitmap
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(entry.getBill().getAbsolutePath(), bmOptions);
            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;

            // Determine how much to scale down the image
            int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

            // Decode the image file into a Bitmap sized to fill the View
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inSampleSize = scaleFactor;
            bmOptions.inPurgeable = true;

            Bitmap bitmap = BitmapFactory.decodeFile(entry.getBill().getAbsolutePath(), bmOptions);
            iv.setImageBitmap(bitmap);
        }else{
            iv.setImageResource(0);
        }


    }

    private void setDateTimeText(ViewHolder holder, RepairEntry entry) {
        //format date and time
        Calendar calendar = entry.getCreateTimeCalendar();
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd. MMM yyyy HH:mm");

        holder.TV_ListItemRepairDateTime.setText(dateFormat.format(calendar.getTime()));
    }



    private void setAutoEntryText(ViewHolder holder, RepairEntry entry) {
        if(entry.getAutoEntry()!=null) {
            switch (entry.getAutoEntry()) {
                case DAILY:
                    holder.TV_ListItemRepairAutoEntry.setText(mContext.getString(R.string.add_cb_auto_entry_with_time, mContext.getString(R.string.ad_auto_entry_daily)));
                    break;
                case WEEKLY:
                    holder.TV_ListItemRepairAutoEntry.setText(mContext.getString(R.string.add_cb_auto_entry_with_time, mContext.getString(R.string.ad_auto_entry_weekly)));
                    break;
                case MONTHLY:
                    holder.TV_ListItemRepairAutoEntry.setText(mContext.getString(R.string.add_cb_auto_entry_with_time, mContext.getString(R.string.ad_auto_entry_monthly)));
                    break;
                case YEARLY:
                    holder.TV_ListItemRepairAutoEntry.setText(mContext.getString(R.string.add_cb_auto_entry_with_time, mContext.getString(R.string.ad_auto_entry_yearly)));
                    break;
                case EVERYTWOYEAR:
                    holder.TV_ListItemRepairAutoEntry.setText(mContext.getString(R.string.add_cb_auto_entry_with_time, mContext.getString(R.string.ad_auto_entry_everytwoyear)));
                    break;
            }
        }else{
            holder.TV_ListItemRepairAutoEntry.setText(mContext.getString(R.string.add_cb_auto_entry)+": "+mContext.getString(R.string.no));
        }
    }




    private void setUpExpandable(ViewHolder holder, int position) {
        //expand entry
        final boolean isExpanded = position==mExpandedPosition;
        holder.BTN_RepairEdit.setVisibility(isExpanded? View.VISIBLE:View.GONE);
        holder.BTN_RepairDelete.setVisibility(isExpanded? View.VISIBLE:View.GONE);
        holder.TV_ListItemRepairLaborCost.setVisibility(isExpanded? View.VISIBLE:View.GONE);
        holder.TV_ListItemRepairPartCost.setVisibility(isExpanded? View.VISIBLE:View.GONE);
        holder.TV_ListItemRepairDescription.setVisibility(isExpanded? View.VISIBLE:View.GONE);
        holder.TV_ListItemRepairAutoEntry.setVisibility(isExpanded? View.VISIBLE:View.GONE);
        holder.IV_ListItemRepairBill.setVisibility(isExpanded? View.VISIBLE:View.GONE);


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
        holder.BTN_RepairEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), RepairAddActivity.class);
                intent.putExtra("entry", entries.get(position));
                intent.putExtra("entryIndex", position);
                view.getContext().startActivity(intent);
            }
        });

        //action for delete Button
        holder.BTN_RepairDelete.setOnClickListener(new View.OnClickListener() {
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
