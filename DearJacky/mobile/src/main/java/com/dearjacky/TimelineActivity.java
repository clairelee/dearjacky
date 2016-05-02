package com.dearjacky;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class TimelineActivity extends AppCompatActivity {
    private SensorTagDBHelper dbHelper;
    ListView list;
    Date date1;
    Date date2;
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        android.support.v7.app.ActionBar a = getSupportActionBar();
        dbHelper = new SensorTagDBHelper(getBaseContext());
        Intent oldIntent = getIntent();
        long millis = oldIntent.getExtras().getLong("date");
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(millis);
        int yearNum = cal.get(Calendar.YEAR);
        int monthNum = cal.get(Calendar.MONTH) + 1;
        String monthString = "";
        String monthArr[] = {"January", "February", "March", "April", "May", "June", "July",
                "August", "September", "October", "November", "December"};
        monthString = monthArr[monthNum - 1];

        a.setTitle(monthString + " " + Integer.toString(yearNum));
        a.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.colorPrimary)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Typewriter jackyText = (Typewriter) findViewById(R.id.event_name);
        jackyText.setCharacterDelay(50);
        jackyText.animateText("Looks like you're feeling a bit down today. Tap me or this speech icon for some suggestions :)");
        jackyText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), RespondActivity.class);
                startActivity(intent);
            }
        });
        list = (ListView) findViewById(R.id.list);

        Calendar cal1 = Calendar.getInstance(); // cal1 is the first day of this month
        cal1.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1);
        cal1.set(Calendar.HOUR, 0);
        cal1.set(Calendar.MINUTE, 0);
        cal1.set(Calendar.SECOND, 0);
        cal1.set(Calendar.MILLISECOND, 0);
        cal1.set(Calendar.AM_PM,Calendar.AM);
        Calendar cal2 = Calendar.getInstance(); // cal2 is the first day of the next month
        cal2.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, 1);
        cal2.set(Calendar.HOUR, 0);
        cal2.set(Calendar.MINUTE, 0);
        cal2.set(Calendar.SECOND, 0);
        cal2.set(Calendar.MILLISECOND, 0);
        cal2.set(Calendar.AM_PM,Calendar.AM);

        date1 = cal1.getTime();
        date2 = cal2.getTime();

        List<DataPointJacky> items = dbHelper.getTableOneDataDuringPeriod(date1, date2);
        System.out.println(date1);
        System.out.println(date2);
        //System.out.println(items.size());
        list.setAdapter(new DataPointAdapter(this, items));
        int position = 0;
        for (int i = 0; i < items.size(); i++) {
            DataPointJacky current = items.get(i);
            if (current.timestamp < millis) {
                position += 1;
            }
        }
        final int pos = position;
        list.postDelayed(new Runnable() {
            @Override
            public void run() {
                list.setSelection(pos);
            }
        },100L);

//        need to replace the following...
//        ArrayList<Events> items = new ArrayList<>();
//        items.add(new Events("First Event", TimelineView.TYPE_START, (int)(Math.random()*4)));
//        for (int i = 0; i < 20; i++) {
//            items.add(new Events(String.format("Middle Event", i + 1),
//                TimelineView.TYPE_MIDDLE, (int)(Math.random()*4)));
//        }
//        items.add(new Events("Last Event", TimelineView.TYPE_END, (int)(Math.random()*4)));
//        list.setAdapter(new EventsAdapter(this, items));

//        RoundTimelineView timelineView = (RoundTimelineView) findViewById(R.id.timeline1);
//        Glide.with(this).load(R.drawable.avatar).into(timelineView);
//
//        RoundTimelineView timeline3_align_top =
//            (RoundTimelineView) findViewById(R.id.timeline3_align_top);
//        timeline3_align_top.setIndicatorSize(
//            getResources().getDimensionPixelOffset(R.dimen.large_timeline_1));
//        timeline3_align_top.setTimelineType(TimelineView.TYPE_HIDDEN);
//
////        Glide.with(this).load(R.drawable.avatar).into(timeline3_align_top);
//
//        RoundTimelineView timeline3_align_bottom =
//            (RoundTimelineView) findViewById(R.id.timeline3_align_bottom);
//        timeline3_align_bottom.setIndicatorSize(
//            getResources().getDimensionPixelOffset(R.dimen.large_timeline_2));
//        timeline3_align_bottom.setTimelineStyle(TimelineView.STYLE_LINEAR);

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
        List<DataPointJacky> items = dbHelper.getTableOneDataDuringPeriod(date1, date2);
        list.setAdapter(new DataPointAdapter(this, items));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (getParentActivityIntent() == null) {
                    Log.i("T", "You have forgotten to specify the parentActivityName in the AndroidManifest!");
                    onBackPressed();
                } else {
                    NavUtils.navigateUpFromSameTask(this);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
