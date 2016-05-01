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

import com.alorma.timeline.TimelineView;

import java.util.ArrayList;


public class TimelineActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        android.support.v7.app.ActionBar a = getSupportActionBar();
        a.setTitle("January");
        a.setBackgroundDrawable(new ColorDrawable( ContextCompat.getColor(this, R.color.colorPrimary)));
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
        ListView list = (ListView) findViewById(R.id.list);

        ArrayList<Events> items = new ArrayList<>();
        items.add(new Events("First Event", TimelineView.TYPE_START, (int)(Math.random()*4)));
        for (int i = 0; i < 20; i++) {
            items.add(new Events(String.format("Middle Event", i + 1),
                TimelineView.TYPE_MIDDLE, (int)(Math.random()*4)));
        }
        items.add(new Events("Last Event", TimelineView.TYPE_END, (int)(Math.random()*4)));
        list.setAdapter(new EventsAdapter(this, items));

//        RoundTimelineView timelineView = (RoundTimelineView) findViewById(R.id.timeline1);
//        Glide.with(this).load(R.drawable.avatar).into(timelineView);

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
