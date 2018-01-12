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

import de.fhbi.mobappproj.carlogger.DataClasses.entry.EntrySuper;
import de.fhbi.mobappproj.carlogger.DataClasses.entry.RepairEntry;
import de.fhbi.mobappproj.carlogger.R;
import de.fhbi.mobappproj.carlogger.activities.AddActivities.RepairAddActivity;

import static de.fhbi.mobappproj.carlogger.Helper.doubleToString;

/**
 * Created by Johannes on 09.01.2018.
 */
public class RepairAdapter extends RecyclerView.Adapter<RepairAdapter.RepairViewHolder> {

    private ArrayList<RepairEntry> entries;
    private Context mContext;
    private ViewGroup recyclerView;

    public RepairAdapter(Context context, ArrayList<RepairEntry> items) {
        GenericViewHolder.mExpandedPosition = -1;
        this.mContext = context;
        entries = items;
    }

    @Override
    public RepairViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.list_item_repair, parent, false);
        return new RepairViewHolder(itemView, mContext, this);

    }

    @Override
    public void onBindViewHolder(RepairViewHolder holder, int position) {
        holder.setDataOnView(position, entries.get(position));

    }


    @Override
    public int getItemCount() {
        return entries.size();
    }




    public static class RepairViewHolder extends GenericViewHolder {

        public TextView TV_ListItemRepairType, TV_ListItemRepairCost, TV_ListItemRepairDateTime, TV_ListItemRepairLaborCost, TV_ListItemRepairPartCost, TV_ListItemRepairDescription, TV_ListItemRepairAutoEntry;
        public ImageView IV_ListItemRepairBill;
        public Button BTN_RepairEdit, BTN_RepairDelete;

        private Context mContext;

        private RecyclerView.Adapter callback;

        private RepairEntry entry;

        public RepairViewHolder(View v, Context mContext, RecyclerView.Adapter callback) {
            super(v);
            this.mContext = mContext;
            this.callback = callback;
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

        @Override
        public void setDataOnView(int position, EntrySuper entrySuper) {
            this.entry = (RepairEntry) entrySuper;

            TV_ListItemRepairType.setText(entry.getType());
            TV_ListItemRepairCost.setText(doubleToString(entry.getLaborCost() + entry.getPartCost()) + " " + mContext.getString(R.string.euro));
            setDateTimeText(entry);
            TV_ListItemRepairLaborCost.setText(mContext.getString(R.string.repair_add_labor_cost) + ": " + doubleToString(entry.getLaborCost()));
            TV_ListItemRepairPartCost.setText(mContext.getString(R.string.repair_add_part_cost) + ": " + doubleToString(entry.getPartCost()));
            TV_ListItemRepairDescription.setText(entry.getDescription());
            setPic(IV_ListItemRepairBill, entry);
            setAutoEntryText(entry);

            //configure expanding entry
            setUpExpandable(position);
        }

        private void setPic(ImageView iv, RepairEntry entry) {

            if (entry.getBill() != null) {
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
            } else {
                iv.setImageResource(0);
            }


        }

        private void setDateTimeText(RepairEntry entry) {
            //format date and time
            Calendar calendar = entry.getCreateTimeCalendar();
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "dd. MMM yyyy HH:mm");

            TV_ListItemRepairDateTime.setText(dateFormat.format(calendar.getTime()));
        }


        private void setAutoEntryText(RepairEntry entry) {
            if (entry.getAutoEntry() != null) {
                switch (entry.getAutoEntry()) {
                    case DAILY:
                        TV_ListItemRepairAutoEntry.setText(mContext.getString(R.string.add_cb_auto_entry_with_time, mContext.getString(R.string.ad_auto_entry_daily)));
                        break;
                    case WEEKLY:
                        TV_ListItemRepairAutoEntry.setText(mContext.getString(R.string.add_cb_auto_entry_with_time, mContext.getString(R.string.ad_auto_entry_weekly)));
                        break;
                    case MONTHLY:
                        TV_ListItemRepairAutoEntry.setText(mContext.getString(R.string.add_cb_auto_entry_with_time, mContext.getString(R.string.ad_auto_entry_monthly)));
                        break;
                    case YEARLY:
                        TV_ListItemRepairAutoEntry.setText(mContext.getString(R.string.add_cb_auto_entry_with_time, mContext.getString(R.string.ad_auto_entry_yearly)));
                        break;
                    case EVERYTWOYEAR:
                        TV_ListItemRepairAutoEntry.setText(mContext.getString(R.string.add_cb_auto_entry_with_time, mContext.getString(R.string.ad_auto_entry_everytwoyear)));
                        break;
                }
            } else {
                TV_ListItemRepairAutoEntry.setText(mContext.getString(R.string.add_cb_auto_entry) + ": " + mContext.getString(R.string.no));
            }
        }


        private void setUpExpandable(int position) {
            //expand entry
            final boolean isExpanded = position == mExpandedPosition;
            BTN_RepairEdit.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
            BTN_RepairDelete.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
            TV_ListItemRepairLaborCost.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
            TV_ListItemRepairPartCost.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
            TV_ListItemRepairDescription.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
            TV_ListItemRepairAutoEntry.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
            IV_ListItemRepairBill.setVisibility(isExpanded ? View.VISIBLE : View.GONE);


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
            BTN_RepairEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), RepairAddActivity.class);
                    intent.putExtra("entry", entry);
                    view.getContext().startActivity(intent);
                }
            });

            //action for delete Button
            BTN_RepairDelete.setOnClickListener(new View.OnClickListener() {
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
