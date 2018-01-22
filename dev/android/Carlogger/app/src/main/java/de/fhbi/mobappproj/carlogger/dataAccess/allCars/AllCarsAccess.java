package de.fhbi.mobappproj.carlogger.dataAccess.allCars;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by martin on 19.01.18.
 */
public class AllCarsAccess {

    public static final String QUERY_ALL = "SELECT * FROM cars";
    public static final String QUERY_NAME = "SELECT * FROM cars WHERE name LIKE '%' || ? || '%'";

    private SQLiteDatabase database = null;

    public AllCarsAccess(Context context) {
        database = new CarsOpenHelper(context).getReadableDatabase();
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

    private class CarsOpenHelper extends SQLiteOpenHelper {

        public CarsOpenHelper(Context context) {
            super(context, "cars.sqlite", null, 3);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }


    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        this.close();
    }
}
