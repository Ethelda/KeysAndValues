package tuziks.kav.kav.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import tuziks.kav.kav.DatabaseHelper;
import tuziks.kav.kav.model.KeyValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Date: 12.23.12
 * Time: 00:15
 */
public class KeyValueRepository {
    public static final String TABLE_NAME = "KeyValue";
    public static final String COLUMN_ID = "Id";
    public static final String COLUMN_KEY = "Key";
    public static final String COLUMN_VALUE = "Value";
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public KeyValueRepository(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public KeyValueRepository open() throws SQLException {
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public KeyValue get(int id) {
        List<KeyValue> res = new ArrayList<KeyValue>();

        Cursor cursor = database.query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_KEY, COLUMN_VALUE}, COLUMN_ID + " = ? ", new String[]{Integer.toString(id)}, null, null, null);
        if (cursor.getCount() < 1)
            return null;
        cursor.moveToFirst();
        return new KeyValue(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
    }

    public List<KeyValue> all() {
        List<KeyValue> res = new ArrayList<KeyValue>();

        Cursor cursor = database.query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_KEY, COLUMN_VALUE}, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            res.add(new KeyValue(cursor.getInt(0), cursor.getString(1), cursor.getString(2)));
            cursor.moveToNext();
        }
        cursor.close();
        return res;
    }

    public KeyValue create(String key, String value) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_KEY, key);
        values.put(COLUMN_VALUE, value);
        long insertId = database.insert(TABLE_NAME, null, values);
        Cursor cursor = database.query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_KEY, COLUMN_VALUE},
                COLUMN_ID + " = " + insertId,
                null, null, null, null
        );
        cursor.moveToFirst();
        KeyValue kav = new KeyValue(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
        cursor.close();
        return kav;
    }

    public void update(KeyValue kv) {
        ContentValues a = new ContentValues();
        a.put(COLUMN_KEY, kv.getKey());
        a.put(COLUMN_VALUE, kv.getValue());
        database.update(TABLE_NAME, a, COLUMN_ID + "= ?", new String[]{Integer.toString(kv.getId())});
    }

    public void delete(KeyValue kv) {
        database.delete(TABLE_NAME, COLUMN_ID + "=" + kv.getId(), null);
    }
}
