package com.example.cpwslogs.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.cpwslogs.models.ShedLog;

import java.util.ArrayList;

/**
 * Created by shaafi on 21-Aug-18.
 * Email: a15shaafi.209@gmail.com
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "cpwslogs.db";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "shed_logs";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_SHED_ID = "shed_id";
    private static final String COLUMN_TREATMENT = "treatment";
    private static final String COLUMN_TEMP = "temperature";
    private static final String COLUMN_HUMIDITY = "humidity";
    private static final String COLUMN_AMMONIA = "ammonia";
    private static final String COLUMN_DATE = "date";

    private Context mContext;

    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( "
            + COLUMN_ID + " INTEGER PRIMARY KEY, "
            + COLUMN_SHED_ID + " INTEGER, "
            + COLUMN_TREATMENT + " TEXT, "
            + COLUMN_TEMP + " TEXT, "
            + COLUMN_HUMIDITY + " TEXT, "
            + COLUMN_AMMONIA + " TEXT, "
            + COLUMN_DATE + " TEXT"
            + ");";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public boolean insertIntoDb(ShedLog shedLog) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_SHED_ID, shedLog.getShed());
        contentValues.put(COLUMN_TEMP, shedLog.getTemp());
        contentValues.put(COLUMN_TREATMENT, shedLog.getTreatment());
        contentValues.put(COLUMN_HUMIDITY, shedLog.getHumidity());
        contentValues.put(COLUMN_AMMONIA, shedLog.getAmmonia());
        contentValues.put(COLUMN_DATE, shedLog.getDate());
        db.insert(TABLE_NAME, null, contentValues);
        db.close();
        return true;
    }

    public ArrayList<ShedLog> getAllLogs() {
        ArrayList<ShedLog> logArrayList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("select * from " + TABLE_NAME, null);

        while (cur.moveToNext()) {
            ShedLog log = new ShedLog(
                    cur.getInt(cur.getColumnIndex(COLUMN_SHED_ID)),
                    cur.getString(cur.getColumnIndex(COLUMN_TEMP)),
                    cur.getString(cur.getColumnIndex(COLUMN_HUMIDITY)),
                    cur.getString(cur.getColumnIndex(COLUMN_AMMONIA)),
                    cur.getString(cur.getColumnIndex(COLUMN_TREATMENT)),
                    cur.getString(cur.getColumnIndex(COLUMN_DATE))
            );
            logArrayList.add(log);
            cur.moveToNext();
        }
        db.close();
        cur.close();
        return logArrayList;
    }

    public boolean deleteAllLogs() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
        return true;
    }
}
