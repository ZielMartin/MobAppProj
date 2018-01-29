package de.fhbi.mobappproj.carlogger.dataAccess;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import java.lang.reflect.Type;
import java.util.HashMap;

import de.fhbi.mobappproj.carlogger.DataClasses.entry.Car;
import de.fhbi.mobappproj.carlogger.DataClasses.entry.EntrySuper;
import de.fhbi.mobappproj.carlogger.DataClasses.MyList;
import de.fhbi.mobappproj.carlogger.DataClasses.entry.FuelEntry;
import de.fhbi.mobappproj.carlogger.DataClasses.list.AllEntryList;
import de.fhbi.mobappproj.carlogger.DataClasses.list.CarList;
import de.fhbi.mobappproj.carlogger.DataClasses.list.FuelEntryList;

/**
 * Created by martin on 08.01.18.
 */

public class FirebaseAccess implements DataAccess {

    private static final String TAG = FirebaseAccess.class.getSimpleName();
    private static FirebaseAccess instance = null;
    private DatabaseReference databaseInstance = FirebaseDatabase.getInstance().getReference();

    private FirebaseAccess() {
    }

    public static FirebaseAccess getInstance() {
        if (instance == null)
            instance = new FirebaseAccess();

        return instance;
    }

    @Override
    public void update(String path, Object object) {
        Log.i(TAG, "update: " + path);
        DatabaseReference target = getReferenceTo(new DatabasePath(path));

        if (object instanceof EntrySuper) {
            target = target.child(((EntrySuper) object).getKey());
        } else if (object instanceof Car) {
            target = target.child(((Car) object).getKey());
        } else {
            Log.e(TAG, "Updating something, that's not supposed to be updated");
            return;
        }

        target.setValue(object);
    }

    @Override
    public void push(String path, Object object) {
        Log.i(TAG, "push: " + path);
        DatabaseReference target = getReferenceTo(new DatabasePath(path));
        target = target.push();

        if (object instanceof EntrySuper) {
            ((EntrySuper) object).setKey(target.getKey());
        } else if (object instanceof Car) {
            ((Car) object).setKey(target.getKey());
        } else {
            Log.e(TAG, "Pushing something, that's not supposed to be pushed");
            return;
        }

        target.setValue(object);
    }

    @Override
    public void delete(String path) {
        Log.i(TAG, "delete: " + path);
        DatabaseReference target = getReferenceTo(new DatabasePath(path));
        target.removeValue();
    }

    @Override
    public <T> void getAll(String path, MyList<T> list, Type typeOfT) {
        Log.i(TAG, "getAll: " + path);


        DatabaseReference target = getReferenceTo(new DatabasePath(path));
        target.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.i(TAG, "onDataChange: " + path);

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Log.i(TAG, child.getValue().toString());

                    Gson gson = new GsonBuilder().create();
                    HashMap<String, Object> val;
                    try {
                        val = (HashMap<String, Object>) child.getValue();
                        val.put("key", child.getKey());
                    } catch (ClassCastException ex) {
                        Log.e(TAG, "could not cast " + child.getValue(), ex);
                        return;
                    }

                    JsonElement jsonElement = gson.toJsonTree(val);
                    T instance = gson.fromJson(jsonElement, typeOfT);
                    list.add(instance);
                    list.notifyAdapter();
                    AllEntryList.getInstance().getInstance().notifyAdapter();

                    CarList.getInstance().notifyAdapter();
                }
                target.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled");
            }
        });

    }

    private DatabaseReference getReferenceTo(DatabasePath path) {
        DatabaseReference target = databaseInstance;
        for (String sub : path.getPath()) {
            target = target.child(sub);
        }
        return target;
    }

    @Override
    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
}
