package de.fhbi.mobappproj.carlogger.activities;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.fhbi.mobappproj.carlogger.R;
import de.fhbi.mobappproj.carlogger.dataAccess.allCars.AllCarsAccess;

public class ChooseCarActivity extends AppCompatActivity {

    private static final String TAG = ChooseCarActivity.class.getSimpleName();

    private AllCarsAccess allCarsAccess = null;
    private ListViewCompat listView = null;
    private CursorAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_car);
        allCarsAccess = new AllCarsAccess(this);
        Cursor cursor = allCarsAccess.rawQuery(AllCarsAccess.QUERY_ALL);
        listView = findViewById(R.id.LV_chooseCar);
        adapter = new MyCursorAdapter(this, cursor);
        listView.setAdapter(new MyCursorAdapter(this, cursor));
    }

    @Override
    protected void onStart() {
        super.onStart();
        allCarsAccess = new AllCarsAccess(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        allCarsAccess.close();
        allCarsAccess = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.car_search, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Cursor cursor = allCarsAccess.getNameLike(newText);
                adapter.changeCursor(cursor);
                listView.setAdapter(adapter);
                Log.i(TAG, "queryTextChange:\t" + newText + "\tcursorCount: " + cursor.getCount());
                return false;
            }
        });

        return true;

//        return super.onCreateOptionsMenu(menu);
    }

    private class MyCursorAdapter extends CursorAdapter {
        public MyCursorAdapter(Context context, Cursor c) {
            super(context, c, 0);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            View itemView = LayoutInflater.from(context).inflate(R.layout.list_item_car, parent, false);
//            itemView.setOnClickListener(view -> {
//                Log.i(TAG, "Clicked item");
//            });
            return itemView;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            TextView TV_ListItemName, TV_ListItemFuel, TV_ListItemPower, TV_ListItemcm3, TV_ListItemProductionYears, TV_ListItemHsn, TV_ListItemTsn;

            TV_ListItemName = view.findViewById(R.id.TV_ListItemName);
            TV_ListItemFuel = view.findViewById(R.id.TV_ListItemFuel);
            TV_ListItemPower = view.findViewById(R.id.TV_ListItemPower);
            TV_ListItemcm3 = view.findViewById(R.id.TV_ListItemcm3);
            TV_ListItemProductionYears = view.findViewById(R.id.TV_ListItemProductionYears);
            TV_ListItemHsn = view.findViewById(R.id.TV_ListItemHsn);
            TV_ListItemTsn = view.findViewById(R.id.TV_ListItemTsn);

            String baujahre = cursor.getString(cursor.getColumnIndexOrThrow("baujahre"));
            String cm3 = cursor.getString(cursor.getColumnIndexOrThrow("cm3"));
            Integer hsn = cursor.getInt(cursor.getColumnIndexOrThrow("hsn"));
            String kraftstoff = cursor.getString(cursor.getColumnIndexOrThrow("kraftstoff"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String ps = cursor.getString(cursor.getColumnIndexOrThrow("ps"));
            String tsn = cursor.getString(cursor.getColumnIndexOrThrow("tsn"));

            TV_ListItemName.setText(name);
            TV_ListItemFuel.setText(context.getString(R.string.car_info_fuel) + kraftstoff);
            TV_ListItemPower.setText(context.getString(R.string.car_info_cm3) + ps);
            TV_ListItemcm3.setText(context.getString(R.string.car_info_power) + cm3);
            TV_ListItemProductionYears.setText(context.getString(R.string.car_info_production_years) + baujahre);
            TV_ListItemHsn.setText(context.getString(R.string.car_info_hsn) + hsn);
            TV_ListItemTsn.setText(context.getString(R.string.car_info_tsn) + tsn);

            view.setTag(R.integer.tag_allCars_id, cursor.getInt(cursor.getColumnIndex("_id")));

            view.setOnClickListener(v -> {
                Log.i(TAG, "Clicked Car with _id: " + v.getTag(R.integer.tag_allCars_id));
            });

        }
    }
}
