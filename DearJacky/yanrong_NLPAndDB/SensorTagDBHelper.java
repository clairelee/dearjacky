package com.example.yanrongli.database_and_nlp;

/**
 * Created by yanrongli on 3/12/16.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yanrongli on 3/2/16.
 */
public class SensorTagDBHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "260ProjectDB.db";
    public static String TABLE1_NAME = "details";
    public static String TABLE2_NAME = "tags";
    public static String TABLE3_NAME = "keywords";
    public static String TABLE1_COL1 = "ID";
    public static String TABLE1_COL2 = "TIMESTAMP";
    public static String TABLE1_COL3 = "MOOD";
    public static String TABLE1_COL4 = "VALUE";
    public static String TABLE1_COL5 = "TAG";
    public static String TABLE1_COL6 = "DETAIL";
    public static String TABLE1_COL7 = "PROCESSED";
    public static String TABLE2_COL1 = "ID";
    public static String TABLE2_COL2 = "TAG";
    public static String TABLE3_COL1 = "ID";
    public static String TABLE3_COL2 = "MOOD";
    public static String TABLE3_COL3 = "KEYWORDS";
    public static String TABLE3_COL4 = "NUM";
    //.....

    public SensorTagDBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE1_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,TIMESTAMP TEXT,MOOD TEXT,VALUE INTEGER,TAG TEXT,DETAIL TEXT,PROCESSED TINYINT)");
        db.execSQL("CREATE TABLE " + TABLE2_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,TAG TEXT)");
        db.execSQL("CREATE TABLE " + TABLE3_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,MOOD TEXT,KEYWORDS TEXT,NUM INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE1_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE2_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE3_NAME);
        onCreate(db);
    }

    public void clearAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE1_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE2_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE3_NAME);
        onCreate(db);
    }

    boolean insertTableOneData(String timestamp, String mood, Integer value, String tag, String detail, Integer processed) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE1_COL2, timestamp);
        contentValues.put(TABLE1_COL3, mood);
        contentValues.put(TABLE1_COL4, value);
        contentValues.put(TABLE1_COL5, tag);
        contentValues.put(TABLE1_COL6, detail);
        contentValues.put(TABLE1_COL7, processed);
        long result = db.insert(TABLE1_NAME, null, contentValues);
        if(result == -1) {
            return false;
        }
        else{
            //System.out.println("inserted new text successfully " + timestamp);
            return true;
        }
    }

    boolean insertTableTwoData(String tag) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE2_COL2, tag);
        long result = db.insert(TABLE2_NAME, null, contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    boolean insertTableThreeData(String mood, String keywords) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery("SELECT COUNT(*) FROM "+TABLE3_NAME+" WHERE MOOD = '"+mood+"' AND KEYWORDS = '"+keywords+"'", null);
        cur.moveToFirst();
        int count = cur.getInt(0);
        cur.close();
        if(count == 0) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(TABLE3_COL2, mood);
            contentValues.put(TABLE3_COL3, keywords);
            long result = db.insert(TABLE3_NAME, null, contentValues);
            if (result == -1)
                return false;
            else {
                System.out.println("stored successfully!");
                return true;
            }
        }
        else
        {
            Cursor cur2 = db.rawQuery("SELECT * FROM "+TABLE3_NAME+" WHERE MOOD = '"+mood+"' AND KEYWORDS = '"+keywords+"'", null);
            cur2.moveToFirst();
            int count2 = cur2.getInt(3);
            System.out.println("Updated Succesfully!");
            return updateTableThreeData(mood, keywords, count2 + 1);
        }
    }

    public Cursor getAllTableOneData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE1_NAME, null);
        return res;
    }

    public Cursor getAllTableTwoData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE2_NAME, null);
        return res;
    }

    public Cursor getAllTableThreeData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE3_NAME, null);
        return res;
    }

    public Cursor getUnprocessedData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE1_NAME+" WHERE PROCESSED = 0", null);
        return res;
    }

    public Cursor getSelectedTableOneData(String query) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery(query, null);
        return res;
    }

    public Cursor getSelectedTableTwoData(String query) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery(query, null);
        return res;
    }

    public Cursor getSelectedTableThreeData(String query) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery(query, null);
        return res;
    }

//    public JSONObject getJsonFromAllData() {
//        Cursor cursor = getAllData();
//        JSONObject results = new JSONObject();
//        JSONArray resultsArray = new JSONArray();
//
//        cursor.moveToFirst();
//        int i = 0;
//        while(!cursor.isAfterLast())
//        {
//            JSONObject entry = new JSONObject();
//            try{
//                //entry.put("id", cursor.getString(cursor.getColumnIndex(COL_1)));
//                entry.put("sensortag_id", cursor.getString(cursor.getColumnIndex(COL_2)));
//                entry.put("sensortag_type", cursor.getString(cursor.getColumnIndex(COL_3)));
//
//                cursor.moveToNext();
//
//                resultsArray.put(i, entry);
//                i++;
//            } catch(JSONException e){
//                e.printStackTrace();
//            }
//        }
//
//        try {
//            results.put("all_data", resultsArray);
//        } catch(JSONException e){
//            e.printStackTrace();
//        }
//
//        return results;
//
//    }

    public boolean updateTableOneData(String timestamp, String mood, Integer value, String tag, String detail, Integer processed) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE1_COL2, timestamp);
        contentValues.put(TABLE1_COL3, mood);
        contentValues.put(TABLE1_COL4, value);
        contentValues.put(TABLE1_COL5, tag);
        contentValues.put(TABLE1_COL6, detail);
        contentValues.put(TABLE1_COL7, processed);
        db.update(TABLE1_NAME, contentValues, "TIMESTAMP = ?", new String[] {timestamp});
        return true;
    }

    public boolean updateTableThreeData(String mood, String keywords, Integer num)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE3_COL2, mood);
        contentValues.put(TABLE3_COL3, keywords);
        contentValues.put(TABLE3_COL4, num);
        db.update(TABLE3_NAME, contentValues, "MOOD = ? AND KEYWORDS = ?", new String[] {mood, keywords});
        return true;
    }

    public Integer deleteTableOneData(String timestamp) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE1_NAME, "TIMESTAMP = ?", new String[] {timestamp});
    }

    public Integer deleteTableTwoData(String tag) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE2_NAME, "TAG = ?", new String[] {tag});
    }

    public Integer deleteTableThreeData(String mood, String keyword) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE3_NAME, "MOOD = ? AND KEYWORDS = ?", new String[] {mood, keyword});
    }

}

