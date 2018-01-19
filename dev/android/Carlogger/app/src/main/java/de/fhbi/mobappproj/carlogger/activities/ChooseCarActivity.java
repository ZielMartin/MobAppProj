package de.fhbi.mobappproj.carlogger.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import de.fhbi.mobappproj.carlogger.DataClasses.CarStructure;
import de.fhbi.mobappproj.carlogger.R;
import de.fhbi.mobappproj.carlogger.dataAccess.allCars.AllCars;
import de.fhbi.mobappproj.carlogger.dataAccess.allCars.AllCarsAccess;
import de.fhbi.mobappproj.carlogger.listEntryAdapter.CarAdapter;

public class ChooseCarActivity extends AppCompatActivity {

    private static final String TAG = ChooseCarActivity.class.getSimpleName();

    private AllCarsAccess allCarsAccess = null;
    private RecyclerView recyclerView;
    private CarAdapter mAdapter;
    private LinkedList<AllCars> allCarsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_car);

        recyclerView = (RecyclerView) findViewById(R.id.RV_ChooseCar);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));


        allCarsAccess = new AllCarsAccess(getResources());
        new CreateContent().doInBackground();



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
                List<AllCars> list = new ArrayList<>(CollectionUtils.select(
                        allCarsList, o -> o.getName().toLowerCase().contains(newText.toLowerCase())
                ));

                updateListView(list);

                Log.i(TAG, "queryTextChange:\t" + newText);
                return false;
            }
        });

        return true;

//        return super.onCreateOptionsMenu(menu);
    }

    private class CreateContent extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                allCarsAccess.start().join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return null;
            }
            Log.i(TAG, "done waiting");


            allCarsList = new LinkedList<>();
            allCarsList.addAll(ChooseCarActivity.this.allCarsAccess.getAllCarsList());

            //Temporary!!-------------------------------------------------------------------------------------
            CarStructure structure = new CarStructure(ChooseCarActivity.this.allCarsAccess.getAllCarsList());
            structure.handleData();
            //------------------------------------------------------------------------------------------------

            Log.i(TAG, "created linkedlist of length: " + allCarsList.size());

            updateListView(allCarsList);

            Log.i(TAG, "done");

            return null;
        }
    }

    private void updateListView(List<AllCars> list) {
        mAdapter = new CarAdapter(this, list);
        recyclerView.setAdapter(mAdapter);
    }
}
