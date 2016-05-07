package com.dearjacky;

/**
 * Created by yanrongli on 3/12/16.
 */

/*
All my database interaction code is implemented in SensorTagDBHelper.
To use it, you first need to have an object in your onCreate():
    SensorTagDBHelper myDBHelper = new SensorTagDBHelper();

Then you can call all the methods with myDBHelper.method1(), myDBHelper.method2()...

In all, I have three tables:

The first table stores all the data points in the format <timestamp, mood("excited", "calm, "depressed",
 "angry"), intensity(value), tag(not useful for now), detail(note of your record), processed(whether
  this entry has been parsed by NLP toolkit)>

The second table stores all the tags. For now we don't need it.

The third table stores all the keywords in the format <mood, keywords(may be a phrase), num(the number
of times that this {mood, keywords} pair appeard in the record history)>.

Besides, each table has a column "ID" as the first column. You can just ignore it.

Here is a summary of the methods provided:

insertTableOneData() ~ inserTableThreeData() allows you to insert data in table 1-3, respectively.
updateTableOneData() ~ updateTableThreeData() allows you to update data in table 1-3, respectively.
deleteTableOneData() ~ deleteTableThreeData() allows you to delete data in table 1-3, respectively.
getTableOneDataDuringPeriod() returns you a list of datapoints between two dates.
getAllTableOneData() ~ getAllTableThreeData() returns you a cursor pointing to the head of the query
results including all data in table 1-3, respectively.
getSelectedTableOneData() ~ getSelectedTableThreeData() returns you a cursor pointing to the head of
 the query results of user-specified query in table 1-3, respectively.
*/


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yanrongli on 3/2/16.
 */
public class SensorTagDBHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "my260ProjectDB.db";
    public static String TABLE1_NAME = "details";
    public static String TABLE2_NAME = "tags";
    public static String TABLE3_NAME = "keywords";
    public static String TABLE1_COL1 = "ID";
    public static String TABLE1_COL2 = "MYTIMESTAMP";
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
        db.execSQL("CREATE TABLE " + TABLE1_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,MYTIMESTAMP TEXT,MOOD TEXT,VALUE INTEGER,TAG TEXT,DETAIL TEXT,PROCESSED TINYINT)");
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

    /*
    This method allows you to insert a data point to Table One.
     */
    public boolean insertTableOneData(String timestamp, String mood, Integer value, String tag, String detail, Integer processed) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE1_COL2, timestamp);
        contentValues.put(TABLE1_COL3, mood);
        contentValues.put(TABLE1_COL4, value);
        contentValues.put(TABLE1_COL5, tag);
        contentValues.put(TABLE1_COL6, detail);
        contentValues.put(TABLE1_COL7, processed);

        Cursor cur = db.rawQuery("SELECT COUNT(*) FROM "+TABLE1_NAME+" WHERE MYTIMESTAMP == '"+timestamp+"'", null);
        cur.moveToFirst();
        int count = cur.getInt(0);
        cur.close();
        if(count == 0) {
            long result = db.insert(TABLE1_NAME, null, contentValues);
            if (result == -1)
                return false;
            else {
                System.out.println("stored successfully!");
                return true;
            }
        }
        else
        {
            Cursor cur2 = db.rawQuery("DELETE FROM "+TABLE1_NAME+" WHERE MYTIMESTAMP == '"+timestamp+"'", null);
            long result = db.insert(TABLE1_NAME, null, contentValues);
            if (result == -1)
                return false;
            else {
                System.out.println("updated successfully!");
                return true;
            }
        }
    }

    /*
    This method allows you to insert a data point to Table Two.
    */
    public boolean insertTableTwoData(String tag) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE2_COL2, tag);
        long result = db.insert(TABLE2_NAME, null, contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    /*
    This method allows you to insert a data point to Table Three.
    */
    public boolean insertTableThreeData(String mood, String keywords) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery("SELECT COUNT(*) FROM "+TABLE3_NAME+" WHERE MOOD == '"+mood+"' AND KEYWORDS == '"+keywords+"'", null);
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
            Cursor cur2 = db.rawQuery("SELECT * FROM "+TABLE3_NAME+" WHERE MOOD == '"+mood+"' AND KEYWORDS == '"+keywords+"'", null);
            cur2.moveToFirst();
            int count2 = cur2.getInt(3);
            System.out.println("Updated Succesfully!");
            return updateTableThreeData(mood, keywords, count2 + 1);
        }
    }

    /*
    This method allows you to get a list of datapoints between two dates. If you want to query for the
    datapoints on Apr 1 2016, for example, you just need to call the method with date1 = Apr 1 2016 00:00:00.000
    and date2 = Apr 1 2016 23:59:59.999
     */
    public List<DataPointJacky> getTableOneDataDuringPeriod(Date date1, Date date2) {
        List<DataPointJacky> resList = new ArrayList<DataPointJacky>();
        long stamp1 = date1.getTime();
        long stamp2 = date2.getTime();
        Cursor res = getAllTableOneData();
        res.moveToFirst();
        while(!res.isAfterLast())
        {
            DataPointJacky tmpPoint = new DataPointJacky();
            tmpPoint.timestamp = res.getLong(res.getColumnIndex(TABLE1_COL2));
            tmpPoint.mood = res.getString(res.getColumnIndex(TABLE1_COL3));
            tmpPoint.intensity = res.getInt(res.getColumnIndex(TABLE1_COL4));
            tmpPoint.note = res.getString(res.getColumnIndex(TABLE1_COL6));

            if(tmpPoint.timestamp >= stamp1 && tmpPoint.timestamp <= stamp2)
                resList.add(tmpPoint);

            res.moveToNext();
            //System.out.println("tmpPoint: " + tmpPoint.mood);
        }
        //System.out.println("list finishes");
        res.close();
//        try {
//            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
//            Date parseDate1 = dateFormat.parse(date1);
//            Date parseDate2 = dateFormat.parse(date2);
//            stamp1 = new Timestamp(parseDate1.getTime());
//            stamp2 = new Timestamp(parseDate2.getTime());
//        } catch(Exception e){
//
//        }
        return resList;
    }

    public List<String> getTableThreeDataTopFive() {
        List<String> resList = new ArrayList<String>();
        //System.out.println("aaaaaaaa");
        Cursor res = getTableThreePositiveSortedData();
        //System.out.println("bbbbbbbbb");
        res.moveToFirst();
        int i = 0;
        while(i < 5)
        {
            resList.add(res.getString(res.getColumnIndex(TABLE3_COL3)));
            i++;
        //    System.out.println("keywordsssssss: "+ res.getString(res.getColumnIndex(TABLE3_COL3)));
            res.moveToNext();
        }

        return resList;
    }

    public List<String> getTableThreeDataTopTen() {
        List<String> resList = new ArrayList<String>();
        //System.out.println("aaaaaaaa");
        Cursor res = getTableThreePositiveSortedData();
        //System.out.println("bbbbbbbbb");
        res.moveToFirst();
        int i = 0;
        try {
            while (i < 10) {
                resList.add(res.getString(res.getColumnIndex(TABLE3_COL3)));
                i++;
                //    System.out.println("keywordsssssss: "+ res.getString(res.getColumnIndex(TABLE3_COL3)));
                res.moveToNext();
            }
        }catch(Exception e){

        }
        if(resList.size() == 0){
            resList.add("Make some happy \nmemories to see them here!");
        }
        return resList;
    }

    public Cursor getAllTableOneData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE1_NAME+" ORDER BY "+TABLE1_COL2+" ASC", null);
        return res;
    }

    public Cursor getAllTableTwoData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE2_NAME, null);
        return res;
    }

    public Cursor getTableThreePositiveSortedData() {
        SQLiteDatabase db = this.getWritableDatabase();
        System.out.println("get table three data!");
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE3_NAME+" WHERE "+TABLE3_COL2+"=='happy' OR "+TABLE3_COL2+"=='ok' ORDER BY "+TABLE3_COL4 + " DESC", null);
        return res;
    }

    public Cursor getUnprocessedData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE1_NAME+" WHERE PROCESSED == 0", null);
        return res;
    }

    public DataPointJacky getSelectedTableOneData(String timestamp) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE1_NAME + " WHERE MYTIMESTAMP == '"+timestamp+"'", null);
        DataPointJacky tmpPoint = new DataPointJacky();
        tmpPoint.timestamp = res.getLong(res.getColumnIndex(TABLE1_COL2));
        tmpPoint.mood = res.getString(res.getColumnIndex(TABLE1_COL3));
        tmpPoint.intensity = res.getInt(res.getColumnIndex(TABLE1_COL4));
        tmpPoint.note = res.getString(res.getColumnIndex(TABLE1_COL6));
        return tmpPoint;
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
        db.update(TABLE1_NAME, contentValues, "MYTIMESTAMP = ?", new String[] {timestamp});
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
        return db.delete(TABLE1_NAME, "MYTIMESTAMP = ?", new String[] {timestamp});
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

