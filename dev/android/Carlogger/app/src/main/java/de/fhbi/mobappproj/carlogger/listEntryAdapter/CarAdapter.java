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

import java.util.ArrayList;

import de.fhbi.mobappproj.carlogger.DataClasses.entry.Car;
import de.fhbi.mobappproj.carlogger.DataClasses.list.CarList;
import de.fhbi.mobappproj.carlogger.R;
import de.fhbi.mobappproj.carlogger.activities.AddActivities.CarAddActivity;

/**
 * Created by Johannes on 09.01.2018.
 */
public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {

    private ArrayList<Car> entries;
    private Context mContext;


    public CarAdapter(Context context, ArrayList<Car> items) {
        GenericViewHolder.mExpandedPosition = -1;
        this.mContext = context;
        entries = items;
    }
    @Override
    public CarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.list_item_car_edit, parent, false);
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
        static int mExpandedPosition = -1;

        public TextView TV_ListItemCarName, TV_ListItemCarKey;
        public Button BTN_CarEdit, BTN_CarDelete;


        private Context mContext;
        private RecyclerView.Adapter callback;

        private Car entry;

        public CarViewHolder(View v, Context mContext, RecyclerView.Adapter callback) {
            super(v);
            this.mContext = mContext;
            this.callback = callback;
            TV_ListItemCarName = (TextView) v.findViewById(R.id.TV_ListItemCarName);
            TV_ListItemCarKey = (TextView) v.findViewById(R.id.TV_ListItemCarKey);


            // Get Edit Button
            BTN_CarEdit = (Button) v.findViewById(R.id.BTN_CarEdit);
            //get delete Button
            BTN_CarDelete = (Button) v.findViewById(R.id.BTN_CarDelete);



        }


        public void setDataOnView(int position, Car entry) {
            this.entry = entry;
            TV_ListItemCarName.setText(entry.getName());
            TV_ListItemCarKey.setText(entry.getHsntsn());

            //configure expanding entry
            setUpExpandable(position);
        }


        private void setUpExpandable(int position) {
            //expand entry
            final boolean isExpanded = position == mExpandedPosition;
            BTN_CarEdit.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
            BTN_CarDelete.setVisibility(isExpanded ? View.VISIBLE : View.GONE);


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
            BTN_CarEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), CarAddActivity.class);
                    intent.putExtra("entryIndex", CarList.getInstance().getCars().indexOf(entry));
                    view.getContext().startActivity(intent);
                }
            });

            //action for delete Button
            BTN_CarDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    entry.remove();
                    callback.notifyDataSetChanged();
                    mExpandedPosition = -1;
                }
            });
        }


    }
}