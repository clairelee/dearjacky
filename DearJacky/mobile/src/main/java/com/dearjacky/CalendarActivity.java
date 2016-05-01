package com.dearjacky;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class CalendarActivity extends AppCompatActivity {
    private SensorTagDBHelper dbHelper;

    CalendarView calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_calendar);
        android.support.v7.app.ActionBar a = getSupportActionBar();
        a.setTitle("Jacky");
        a.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.colorPrimary)));

        String dbname = "260ProjectDB.db";
        File dbpath = getBaseContext().getDatabasePath(dbname);
        Log.d("T", dbpath.toString());
//        Log.d("T", "DB Check");
        System.out.println("DB Check");

        if(dbpath.exists()){
            Log.d("T", "DB EXISTSASDFASDFASDFASDF");
        }
        else{
            Log.d("T", "DB DOESN'T EXISTSASDFASDFASDFASDF");
//            if db doesn't exist, copy default from resources
            try {
                InputStream in = getResources().openRawResource(R.raw.db_dump);
                OutputStream out = new FileOutputStream(dbpath);

                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;

                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                in.close();
                out.close();
            }catch(Exception e){

            }
        }

        dbHelper = new SensorTagDBHelper(getBaseContext());

        //

        //

        Typewriter jackyText = (Typewriter) findViewById(R.id.jacky_text);
        jackyText.setCharacterDelay(50);
        jackyText.animateText("Seems like this week was rough. If you tap here, I have some suggestions that might cheer you up!");
        jackyText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), RespondActivity.class);
                startActivity(intent);
            }
        });

        // Caldroid Calendar stuff:
        final CaldroidFragment caldroidFragment = new CaldroidFragment();
        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        caldroidFragment.setArguments(args);
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calendar, caldroidFragment);
        t.commit();

        Calendar myCalendar = new GregorianCalendar(2016, 3, 1);
        Calendar myCalendar1 = new GregorianCalendar(2016, 3, 1);
        Calendar myCalendar2 = new GregorianCalendar(2016, 3, 2);
        for(int i=0; i<60; i++){
            //Calendar myCalendar = new GregorianCalendar(2016, 3, i);
            myCalendar1.add(Calendar.DATE, 1);
            Date myDate1 = myCalendar1.getTime();
            myCalendar2.add(Calendar.DATE, 1);
            Date myDate2 = myCalendar2.getTime();
            int colorId = 0;
            List<DataPointJacky> myEventList= dbHelper.getTableOneDataDuringPeriod(myDate1, myDate2);
            int numHappy = 0;
            int numOk = 0;
            int numSad = 0;
            int numAngry = 0;
            for(DataPointJacky ele : myEventList)
            {
                if(ele.mood.equals("happy")) numHappy++;
                if(ele.mood.equals("ok")) numOk++;
                if(ele.mood.equals("sad")) numSad++;
                if(ele.mood.equals("angry")) numAngry++;
            }
            if(numHappy == 0 && numOk == 0 && numSad == 0 && numAngry == 0) colorId = R.color.text_white;
            else if(numHappy == Math.max(Math.max(numHappy, numOk), Math.max(numSad, numAngry))) colorId = R.color.colorExcited;
            else if(numOk == Math.max(Math.max(numHappy, numOk), Math.max(numSad, numAngry))) colorId = R.color.colorCalm;
            else if(numSad == Math.max(Math.max(numHappy, numOk), Math.max(numSad, numAngry))) colorId = R.color.colorDepressed;
            else if(numAngry == Math.max(Math.max(numHappy, numOk), Math.max(numSad, numAngry))) colorId = R.color.colorAngry;
            ColorDrawable cd = new ColorDrawable(ContextCompat.getColor(getBaseContext(), colorId));
            caldroidFragment.setBackgroundDrawableForDate(cd, myDate1);
            //System.out.println("myCalendar1: " + myCalendar1.getTime());
            //System.out.println("myCalendar2: " + myCalendar2.getTime());
        }
        caldroidFragment.refreshView();

        final CaldroidListener listener = new CaldroidListener() {
            @Override
            public void onSelectDate(Date date, View view) {
                Intent intent = new Intent(getBaseContext(), TimelineActivity.class);
                long date_long = date.getTime();
                intent.putExtra("date", date_long);
                startActivity(intent);
            }
        };
        caldroidFragment.setCaldroidListener(listener);

        verifyStoragePermissions(this);


//        FileInputStream fis=null;
//        FileOutputStream fos=null;
//
//        try
//        {
//            fis=new FileInputStream(f);
//            fos=new FileOutputStream("/mnt/sdcard/db_dump.db");
//            while(true)
//            {
//                int i=fis.read();
//                if(i!=-1)
//                {fos.write(i);}
//                else
//                {break;}
//            }
//            fos.flush();
//            Toast.makeText(this, "DB dump OK", Toast.LENGTH_LONG).show();
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//            Toast.makeText(this, "DB dump ERROR", Toast.LENGTH_LONG).show();
//        }
//        finally
//        {
//            try
//            {
//                fos.close();
//                fis.close();
//            }
//            catch(IOException ioe)
//            {}
//        }

        //AlarmManager
        AlarmManager alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        //System.out.println("meow1");
        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        int requestCode = 0;
        PendingIntent pendIntent = PendingIntent.getBroadcast(getApplicationContext(), requestCode, intent, 0);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 10);
        long interval = 600 * 1000;
        //System.out.println("meow2");
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), interval, pendIntent);
        //System.out.println("meow3");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), EditResponseActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {  // After a pause OR at startup
        super.onResume();
        final CaldroidFragment caldroidFragment = new CaldroidFragment();
        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        caldroidFragment.setArguments(args);
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calendar, caldroidFragment);
        t.commit();

        Calendar myCalendar = new GregorianCalendar(2016, 3, 1);
        Calendar myCalendar1 = new GregorianCalendar(2016, 3, 1);
        Calendar myCalendar2 = new GregorianCalendar(2016, 3, 2);
        for(int i=0; i<60; i++){
            //Calendar myCalendar = new GregorianCalendar(2016, 3, i);
            myCalendar1.add(Calendar.DATE, 1);
            Date myDate1 = myCalendar1.getTime();
            myCalendar2.add(Calendar.DATE, 1);
            Date myDate2 = myCalendar2.getTime();
            int colorId = 0;
            List<DataPointJacky> myEventList= dbHelper.getTableOneDataDuringPeriod(myDate1, myDate2);
            int numHappy = 0;
            int numOk = 0;
            int numSad = 0;
            int numAngry = 0;
            for(DataPointJacky ele : myEventList)
            {
                if(ele.mood.equals("happy")) numHappy++;
                if(ele.mood.equals("ok")) numOk++;
                if(ele.mood.equals("sad")) numSad++;
                if(ele.mood.equals("angry")) numAngry++;
            }
            if(numHappy == 0 && numOk == 0 && numSad == 0 && numAngry == 0) colorId = R.color.text_white;
            else if(numHappy == Math.max(Math.max(numHappy, numOk), Math.max(numSad, numAngry))) colorId = R.color.colorExcited;
            else if(numOk == Math.max(Math.max(numHappy, numOk), Math.max(numSad, numAngry))) colorId = R.color.colorCalm;
            else if(numSad == Math.max(Math.max(numHappy, numOk), Math.max(numSad, numAngry))) colorId = R.color.colorDepressed;
            else if(numAngry == Math.max(Math.max(numHappy, numOk), Math.max(numSad, numAngry))) colorId = R.color.colorAngry;
            ColorDrawable cd = new ColorDrawable(ContextCompat.getColor(getBaseContext(), colorId));
            caldroidFragment.setBackgroundDrawableForDate(cd, myDate1);
            //System.out.println("myCalendar1: " + myCalendar1.getTime());
            //System.out.println("myCalendar2: " + myCalendar2.getTime());
        }
        caldroidFragment.refreshView();

        final CaldroidListener listener = new CaldroidListener() {
            @Override
            public void onSelectDate(Date date, View view) {
                Intent intent = new Intent(getBaseContext(), TimelineActivity.class);
                long date_long = date.getTime();
                intent.putExtra("date", date_long);
                startActivity(intent);
            }
        };
        caldroidFragment.setCaldroidListener(listener);
    }

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

}
