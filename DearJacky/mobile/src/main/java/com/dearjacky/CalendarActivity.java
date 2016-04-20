package com.dearjacky;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

public class CalendarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.activity_calendar);

        ImageButton calendarView = (ImageButton)findViewById(R.id.calendarview);

        calendarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), TimelineActivity.class);
                startActivity(i);
            }
        });

        android.support.v7.app.ActionBar a = getSupportActionBar();
        a.setTitle("Jacky");
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#009688"));
        a.setBackgroundDrawable(colorDrawable);

    }
}
