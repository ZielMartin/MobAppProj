package de.fhbi.mobappproj.carlogger.dataAccess;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by martin on 08.01.18.
 */

public class FirebaseAccess implements DataAccess {

    private static FirebaseAccess instance = null;
    private DatabaseReference databaseInstance = FirebaseDatabase.getInstance().getReference();

    private FirebaseAccess() {}

    public static FirebaseAccess getInstance() {
        if (instance == null)
            instance = new FirebaseAccess();

        return instance;
    }

    @Override
    public void update(String path, Object object) {
        DatabaseReference target = getReferenceTo(new DatabasePath(path));
        target.setValue(object);
    }

    @Override
    public void push(String path, Object object) {
        DatabaseReference target = getReferenceTo(new DatabasePath(path));
        target.push().setValue(object);
    }

    @Override
    public void delete(String path, Object object) {
        DatabaseReference target = getReferenceTo(new DatabasePath(path));
        target.removeValue();
    }

    private DatabaseReference getReferenceTo(DatabasePath path) {
        DatabaseReference target = databaseInstance;
        for(String sub : path.getPath()) {
            target = target.child(sub);
        }
        return target;
    }
}
