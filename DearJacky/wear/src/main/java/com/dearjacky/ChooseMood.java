package com.dearjacky;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ChooseMood extends Activity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                //mTextView = (TextView) stub.findViewById(R.id.text);
                //Button button;
                //button = (Button)findViewById(R.id.button1);
                Button buttonHappy = (Button)findViewById(R.id.mood_happy);
                Button buttonOk = (Button)findViewById(R.id.mood_ok);
                Button buttonSad = (Button)findViewById(R.id.mood_sad);
                Button buttonAngry = (Button)findViewById(R.id.mood_angry);

                buttonHappy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getBaseContext(), SelectIntensity.class);
                        i.putExtra("mood", "happy");
                        startActivity(i);
                    }
                });

                buttonOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getBaseContext(), SelectIntensity.class);
                        i.putExtra("mood", "ok");
                        startActivity(i);
                    }
                });

                buttonSad.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getBaseContext(), SelectIntensity.class);
                        i.putExtra("mood", "sad");
                        startActivity(i);
                    }
                });

                buttonAngry.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getBaseContext(), SelectIntensity.class);
                        i.putExtra("mood", "angry");
                        startActivity(i);
                    }
                });



//                button.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent i = new Intent(getBaseContext(), SelectIntensity.class);
//                        startActivity(i);
//                    }
//                });
            }
        });



    }
}
