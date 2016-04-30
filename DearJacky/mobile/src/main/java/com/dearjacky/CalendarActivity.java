package com.dearjacky;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CalendarView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CalendarActivity extends AppCompatActivity {

    CalendarView calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_calendar);
        android.support.v7.app.ActionBar a = getSupportActionBar();
        a.setTitle("Jacky");
        a.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.colorPrimary)));

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
            int j = (int) Math.floor(Math.random() * 4);
            int colorId;
            switch(j){
                case 0: colorId = R.color.colorAngry;
                    break;
                case 1: colorId = R.color.colorCalm;
                    break;
                case 2: colorId = R.color.colorDepressed;
                    break;
                case 3: colorId = R.color.colorExcited;
                    break;
                default:colorId = R.color.colorBlack;
                    break;
            }
            Calendar myCalendar = new GregorianCalendar(2016, 3, i);
            Date myDate = myCalendar.getTime();
            ColorDrawable cd = new ColorDrawable(ContextCompat.getColor(getBaseContext(), colorId));
            caldroidFragment.setBackgroundDrawableForDate(cd, myDate);
        }
        caldroidFragment.refreshView();

        final CaldroidListener listener = new CaldroidListener() {
            @Override
            public void onSelectDate(Date date, View view) {
                Intent intent = new Intent(getBaseContext(), EditResponseActivity.class);
                intent.putExtra("date", date);
                startActivity(intent);
            }
        };
        caldroidFragment.setCaldroidListener(listener);


        //AlarmManager
        AlarmManager alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        System.out.println("meow1");
        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        int requestCode = 0;
        PendingIntent pendIntent = PendingIntent.getBroadcast(getApplicationContext(), requestCode, intent, 0);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 10);
        long interval = 60 * 1000;
        System.out.println("meow2");
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), interval, pendIntent);
        System.out.println("meow3");

    }


}
