package com.dearjacky;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class TimelineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        ImageButton timelineView = (ImageButton)findViewById(R.id.timelineview);

        timelineView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), EditResponseActivity.class);
                startActivity(i);
            }
        });

        android.support.v7.app.ActionBar a = getSupportActionBar();
        a.setTitle("January");
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#009688"));
        a.setBackgroundDrawable(colorDrawable);

    }
}
