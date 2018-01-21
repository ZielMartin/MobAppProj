package de.fhbi.mobappproj.carlogger.dataAccess.allCars;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by martin on 19.01.18.
 */
public class AllCarsAccess {

    public static final String QUERY_ALL = "SELECT * FROM cars";
    public static final String QUERY_NAME = "SELECT * FROM cars WHERE name LIKE '%' || ? || '%'";

    private SQLiteDatabase database = null;

    public AllCarsAccess(Context context) {
        String pathToDB = context.getDatabasePath("cars.sqlite").getPath();
        database = SQLiteDatabase.openDatabase(pathToDB, null, SQLiteDatabase.OPEN_READONLY);
    }

    public Cursor rawQuery(String query) {
        Cursor cursor = database.rawQuery(query, null);
        return cursor;
    }

    public Cursor rawQuery(String query, String[] selectionArgs) {
        Cursor cursor = database.rawQuery(query, selectionArgs);
        return cursor;
    }

    public Cursor getNameLike(String name) {
        Cursor cursor = database.rawQuery(QUERY_NAME, new String[]{name});
        return cursor;
    }

    public void close() {
        database.close();
    }
}
