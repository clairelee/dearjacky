package com.dearjacky;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class TimelineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        android.support.v7.app.ActionBar a = getSupportActionBar();
        a.setTitle("January");
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#009688"));
        a.setBackgroundDrawable(colorDrawable);

        Button button, button2, button3;
        button = (Button)findViewById(R.id.button);
        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), RespondActivity.class);
                startActivity(i);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), EditResponseActivity.class);
                startActivity(i);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent(getBaseContext(), PhoneToWatchService.class);
//        sendIntent.putExtra("zip", repArray.toString());
//        Log.d("T", "message length: " + repArray.toString().length());
//        Log.d("T", "message "+ repArray.toString());

                startService(sendIntent);
            }
        });

    }
}
