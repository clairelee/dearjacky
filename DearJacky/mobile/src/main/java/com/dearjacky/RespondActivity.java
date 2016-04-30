package com.dearjacky;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import com.alorma.timeline.TimelineView;

import java.util.ArrayList;

public class RespondActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_respond);
        android.support.v7.app.ActionBar a = getSupportActionBar();
        a.setTitle("Ask Jacky");
        a.setBackgroundDrawable(new ColorDrawable( ContextCompat.getColor(this, R.color.colorPrimary)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Typewriter jackyText = (Typewriter) findViewById(R.id.event_name);
        jackyText.setCharacterDelay(50);
        jackyText.animateText("Here are some things that have made you happy before!");

        Typewriter jackyText2 = (Typewriter) findViewById(R.id.jacky_text2);
        jackyText2.setInitialDelay(1500);
        jackyText2.setCharacterDelay(50);
        jackyText2.animateText("Maybe talking to somebody about it will help?");
        ListView list2 = (ListView) findViewById(R.id.list3);

        ArrayList<Events> items = new ArrayList<>();
        items.add(new Events("First Event", TimelineView.TYPE_START, (int)(Math.random()*4)));
        for (int i = 0; i < 20; i++) {
            items.add(new Events(String.format("Middle Event", i + 1),
                    TimelineView.TYPE_MIDDLE, (int)(Math.random()*4)));
        }
        items.add(new Events("Last Event", TimelineView.TYPE_END, (int)(Math.random()*4)));

        list2.setAdapter(new EventsAdapter(this, items));
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
