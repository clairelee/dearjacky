package com.example.yanrongli.database_and_nlp;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by yanrongli on 4/19/16.
 */
public class NLPService extends Service {
    private SensorTagDBHelper dbHelper;
    private String[] filteredWords = {"today", "tomorrow", "yesterday", "morning", "afternoon", "noon",
            "evening", "night", "month", "year"};

    @Override
    public void onCreate() {
        super.onCreate();
        dbHelper = new SensorTagDBHelper(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        analyzeNewData();
        return START_STICKY;
    }

    public boolean analyzeNewData()
    {
        Cursor res = dbHelper.getUnprocessedData();
        if(res.getCount() == 0) return true;
        String text = "";
        String mood = "";
        String timestamp = "";
        int value = 0;
        String tag = "";

        //res.moveToFirst();
        while(res.moveToNext())
        {
            //process the entry
            text = res.getString(5);
            mood = res.getString(2);
            posTagOneSentence(text + ". " + mood + ".");
            //change the entry into "processed"
            timestamp = res.getString(1);
            value = res.getInt(3);
            tag = res.getString(4);
            boolean ifUpdated = dbHelper.updateTableOneData(timestamp,mood,value,tag,text,1);
            System.out.println("Updated to processed?: " + ifUpdated);
        }
        return true;
    }

    //function: store keywords to table 3 (add an attribute: number of appearance!)
    public void storeKeywords(String mood, String keywords)
    {
        dbHelper.insertTableThreeData(mood, keywords);
    }

    //function: return keywords based on input



    public void posTagOneSentence(String text)
    {
        System.out.println(text);
        new NLPAsyncTask(this).execute(text);

    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
