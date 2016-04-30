package com.dearjacky;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CalendarView;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

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

        dbHelper = new SensorTagDBHelper(getBaseContext());
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

        for(int i=1; i<=30; i++){
//            int j = (int) Math.floor(Math.random() * 4);
//            int colorId;
//            switch(j){
//                case 0: colorId = R.color.colorAngry;
//                    break;
//                case 1: colorId = R.color.colorCalm;
//                    break;
//                case 2: colorId = R.color.colorDepressed;
//                    break;
//                case 3: colorId = R.color.colorExcited;
//                    break;
//                default:colorId = R.color.colorBlack;
//                    break;
//            }

            Calendar myCalendar = new GregorianCalendar(2016, 3, i);
            Date myDate = myCalendar.getTime();
            myCalendar.add(Calendar.DATE, 1);
            Date myNextDate = myCalendar.getTime();
            int colorId = 0;
            List<DataPointJacky> myEventList= dbHelper.getTableOneDataDuringPeriod(myDate, myNextDate);
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
            if(numHappy == 0 && numOk == 0 && numSad == 0 && numAngry == 0) colorId = R.color.colorBlack;
            else if(numHappy == Math.max(Math.max(numHappy, numOk), Math.max(numSad, numAngry))) colorId = R.color.colorExcited;
            else if(numOk == Math.max(Math.max(numHappy, numOk), Math.max(numSad, numAngry))) colorId = R.color.colorCalm;
            else if(numSad == Math.max(Math.max(numHappy, numOk), Math.max(numSad, numAngry))) colorId = R.color.colorDepressed;
            else if(numAngry == Math.max(Math.max(numHappy, numOk), Math.max(numSad, numAngry))) colorId = R.color.colorAngry;
            ColorDrawable cd = new ColorDrawable(ContextCompat.getColor(getBaseContext(), colorId));
            caldroidFragment.setBackgroundDrawableForDate(cd, myDate);
            System.out.println("meowmeowmeowmeowmeow");
        }
        caldroidFragment.refreshView();

        final CaldroidListener listener = new CaldroidListener() {
            @Override
            public void onSelectDate(Date date, View view) {
                Intent intent = new Intent(getBaseContext(), TimelineActivity.class);
                intent.putExtra("date", date);
                startActivity(intent);
            }
        };
        caldroidFragment.setCaldroidListener(listener);


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


}
