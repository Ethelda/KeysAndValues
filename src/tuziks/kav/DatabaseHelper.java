package tuziks.kav;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import tuziks.kav.repository.KeyValueRepository;

/**
 * Created with IntelliJ IDEA.
 * Date: 12.22.12
 * Time: 23:52
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "keys_and_values.sl3";
    private static final int DATABASE_VERSION = 4;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i(Constants.LOG_KEY, "creating database");
        sqLiteDatabase.execSQL(
                "CREATE TABLE " + KeyValueRepository.TABLE_NAME + " ( " +
                        KeyValueRepository.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        KeyValueRepository.COLUMN_KEY + " TEXT NOT NULL, " +
                        KeyValueRepository.COLUMN_VALUE + " TEXT NOT NULL);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int from, int to) {
        Log.i(Constants.LOG_KEY, "Upgrading  database " + DATABASE_NAME + " from " + from + " to " + to);
    }
}
