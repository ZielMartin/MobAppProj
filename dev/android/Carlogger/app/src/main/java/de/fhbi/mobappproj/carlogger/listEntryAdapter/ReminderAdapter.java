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

import de.fhbi.mobappproj.carlogger.DataClasses.ReminderEntry;
import de.fhbi.mobappproj.carlogger.R;
import de.fhbi.mobappproj.carlogger.activities.AddActivities.ReminderAddActivity;

/**
 * Created by Johannes on 26.12.2017.
 */

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ViewHolder> {

    private ArrayList<ReminderEntry> entries;
    private Context mContext;
    private int mExpandedPosition;
    private ViewGroup recyclerView;


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView TV_ListItemReminderTitle, TV_ListItemReminderDate, TV_ListItemReminderTime, TV_ListItemReminderPushNoti;
        public Button BTN_ReminderEdit, BTN_ReminderDelete;

        public ViewHolder(View v) {
            super(v);
            // Get title element
            TV_ListItemReminderTitle = (TextView) v.findViewById(R.id.TV_ListItemReminderTitle);
            // Get date element
            TV_ListItemReminderDate = (TextView) v.findViewById(R.id.TV_ListItemReminderDate);
            // Get time element
            TV_ListItemReminderTime = (TextView) v.findViewById(R.id.TV_ListItemReminderTime);
            // Get AutoEntry element
            TV_ListItemReminderPushNoti = (TextView) v.findViewById(R.id.TV_ListItemReminderPushNoti);
            // Get Edit Button
            BTN_ReminderEdit = (Button) v.findViewById(R.id.BTN_ReminderEdit);
            //get delete Button
            BTN_ReminderDelete = (Button) v.findViewById(R.id.BTN_ReminderDelete);

        }
    }





    public ReminderAdapter(Context context, ArrayList<ReminderEntry> items) {
        this.mContext = context;
        entries = items;
        mExpandedPosition = -1;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_reminder, parent, false);

        recyclerView = parent;
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        ReminderEntry entry = entries.get(position);
        holder.TV_ListItemReminderTitle.setText(entry.getDescription());
        //format date and time
        Calendar calendar = entry.getDateTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd. MMM yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat(
                "HH:mm");

        //set date
        holder.TV_ListItemReminderDate.setText(dateFormat.format(calendar.getTime()));
        //set time
        holder.TV_ListItemReminderTime.setText(timeFormat.format(calendar.getTime()));
        //set Push-Notification text
        setPushNotiText(holder.TV_ListItemReminderPushNoti, entry);

        //configure expanding entry
        setUpExpandable(holder, position);


    }

    private void setUpExpandable(ViewHolder holder, int position) {
        //expand entry
        final boolean isExpanded = position==mExpandedPosition;
        holder.BTN_ReminderEdit.setVisibility(isExpanded? View.VISIBLE:View.GONE);
        holder.BTN_ReminderDelete.setVisibility(isExpanded? View.VISIBLE:View.GONE);
        holder.itemView.setActivated(isExpanded);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpandedPosition = isExpanded ? -1:position;

                //animation buggy on full list
                //Transition changeBounds = new ChangeBounds();
                //changeBounds.setDuration(300);
                //changeBounds.setInterpolator(new AccelerateDecelerateInterpolator());
                //TransitionManager.beginDelayedTransition(recyclerView, changeBounds);

                //v.setBackgroundColor(v.getContext().getResources().getColor(R.color.light_grey));

                notifyDataSetChanged();
            }
        });
        setUpButtons(holder, position);


    }

    private void setUpButtons(ViewHolder holder, int position) {
        //action for edit button
        holder.BTN_ReminderEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //view.getContext().startActivity(new Intent(view.getContext(), ReminderAddActivity.class));
                Intent intent = new Intent(view.getContext(), ReminderAddActivity.class);
                intent.putExtra("entry", entries.get(position));
                intent.putExtra("entryIndex", position);
                view.getContext().startActivity(intent);
            }
        });

        //action for delete Button
        holder.BTN_ReminderDelete.setOnClickListener(new View.OnClickListener() {
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



    private void setPushNotiText(TextView TV_ListItemReminderPushNoti, ReminderEntry entry) {
        if(entry.isPushNotification()){
            int hours = entry.getHoursNotification();
            switch (hours) {
                case 1: // 1h
                    TV_ListItemReminderPushNoti.setText(mContext.getString(R.string.reminder_cb_push_withTime,mContext.getString(R.string.ad_push_notification_1)));
                    break;
                case 2: // 2h
                    TV_ListItemReminderPushNoti.setText(mContext.getString(R.string.reminder_cb_push_withTime,mContext.getString(R.string.ad_push_notification_2)));
                    break;
                case 3: // 3h
                    TV_ListItemReminderPushNoti.setText(mContext.getString(R.string.reminder_cb_push_withTime,mContext.getString(R.string.ad_push_notification_3)));
                    break;
                case 24: // 24h
                    TV_ListItemReminderPushNoti.setText(mContext.getString(R.string.reminder_cb_push_withTime,mContext.getString(R.string.ad_push_notification_24)));
                    break;
                default:
                    break;
            }
        }else{
            TV_ListItemReminderPushNoti.setText(mContext.getString(R.string.reminder_cb_push) + ": " +mContext.getString(R.string.no));
        }
    }




}
