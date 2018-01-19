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

import de.fhbi.mobappproj.carlogger.DataClasses.entry.EntrySuper;
import de.fhbi.mobappproj.carlogger.DataClasses.entry.ReminderEntry;
import de.fhbi.mobappproj.carlogger.DataClasses.list.ReminderEntryList;
import de.fhbi.mobappproj.carlogger.R;
import de.fhbi.mobappproj.carlogger.activities.AddActivities.ReminderAddActivity;

/**
 * Created by Johannes on 26.12.2017.
 */
public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder> {

    private ArrayList<ReminderEntry> entries;
    private Context mContext;
    private ViewGroup recyclerView;

    public ReminderAdapter(Context context, ArrayList<ReminderEntry> items) {
        GenericViewHolder.mExpandedPosition = -1;
        this.mContext = context;
        entries = items;
    }

    @Override
    public ReminderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.list_item_reminder, parent, false);
        return new ReminderViewHolder(itemView, mContext, this);

    }

    @Override
    public void onBindViewHolder(ReminderViewHolder holder, int position) {
        holder.setDataOnView(position, entries.get(position));

    }


    @Override
    public int getItemCount() {
        return entries.size();
    }




    public static class ReminderViewHolder extends GenericViewHolder {

        public TextView TV_ListItemReminderTitle, TV_ListItemReminderDate, TV_ListItemReminderTime, TV_ListItemReminderPushNoti;
        public Button BTN_ReminderEdit, BTN_ReminderDelete;

        private ReminderEntry entry;
        private Context mContext;

        private RecyclerView.Adapter callback;

        public ReminderViewHolder(View v, Context mContext, RecyclerView.Adapter callback) {
            super(v);
            this.mContext = mContext;
            this.callback = callback;
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

        @Override
        public void setDataOnView(int position, EntrySuper entrySuper) {

            entry = (ReminderEntry) entrySuper;

            TV_ListItemReminderTitle.setText(entry.getDescription());
            //format date and time
            Calendar calendar = entry.getDateTime();
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "dd. MMM yyyy");
            SimpleDateFormat timeFormat = new SimpleDateFormat(
                    "HH:mm");

            //set date
            TV_ListItemReminderDate.setText(dateFormat.format(calendar.getTime()));
            //set time
            TV_ListItemReminderTime.setText(timeFormat.format(calendar.getTime()));
            //set Push-Notification text
            setPushNotiText(entry);

            //configure expanding entry
            setUpExpandable(position);

        }

        private void setUpExpandable(int position) {
            //expand entry
            final boolean isExpanded = position == mExpandedPosition;
            BTN_ReminderEdit.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
            BTN_ReminderDelete.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
            TV_ListItemReminderPushNoti.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
            TV_ListItemReminderTime.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
            itemView.setActivated(isExpanded);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mExpandedPosition = isExpanded ? -1 : position;

                    //animation buggy on full list
                    //Transition changeBounds = new ChangeBounds();
                    //changeBounds.setDuration(300);
                    //changeBounds.setInterpolator(new AccelerateDecelerateInterpolator());
                    //TransitionManager.beginDelayedTransition(recyclerView, changeBounds);

                    //v.setBackgroundColor(v.getContext().getResources().getColor(R.color.light_grey));

                    callback.notifyDataSetChanged();
                }
            });
            setUpButtons(position);


        }

        private void setUpButtons(int position) {
            //action for edit button
            BTN_ReminderEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //view.getContext().startActivity(new Intent(view.getContext(), ReminderAddActivity.class));
                    Intent intent = new Intent(view.getContext(), ReminderAddActivity.class);
                    intent.putExtra("entryIndex", ReminderEntryList.getInstance().getAllEntries().indexOf(entry));
                    view.getContext().startActivity(intent);
                }
            });

            //action for delete Button
            BTN_ReminderDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    entry.removeEntry();
                    callback.notifyDataSetChanged();
                    mExpandedPosition = -1;
                }
            });
        }

        private void setPushNotiText(ReminderEntry entry) {
            if (entry.isPushNotification()) {
                int hours = entry.getHoursNotification();
                switch (hours) {
                    case 1: // 1h
                        TV_ListItemReminderPushNoti.setText(mContext.getString(R.string.reminder_cb_push_withTime, mContext.getString(R.string.ad_push_notification_1)));
                        break;
                    case 2: // 2h
                        TV_ListItemReminderPushNoti.setText(mContext.getString(R.string.reminder_cb_push_withTime, mContext.getString(R.string.ad_push_notification_2)));
                        break;
                    case 3: // 3h
                        TV_ListItemReminderPushNoti.setText(mContext.getString(R.string.reminder_cb_push_withTime, mContext.getString(R.string.ad_push_notification_3)));
                        break;
                    case 24: // 24h
                        TV_ListItemReminderPushNoti.setText(mContext.getString(R.string.reminder_cb_push_withTime, mContext.getString(R.string.ad_push_notification_24)));
                        break;
                    default:
                        break;
                }
            } else {
                TV_ListItemReminderPushNoti.setText(mContext.getString(R.string.reminder_cb_push) + ": " + mContext.getString(R.string.no));
            }
        }


    }
}



