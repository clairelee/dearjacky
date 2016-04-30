package com.dearjacky;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.widget.TextView;

public class Confirmation extends Activity {

    private TextView mTextView;
    private String mMood;
    private String mIntensity;
    private String mNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        Intent oldIntent = getIntent();
        mMood = oldIntent.getExtras().getString("mood");
        mIntensity = oldIntent.getExtras().getString("intensity");
        mNote = oldIntent.getExtras().getString("note");

        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);

        Intent sendIntent = new Intent(this, WatchToPhoneService.class);
        sendIntent.putExtra("cmd", "Received Data Entry!");
        sendIntent.putExtra("mood", mMood);
        sendIntent.putExtra("intensity", mIntensity);
        sendIntent.putExtra("note", mNote);
//        sendIntent.putExtra("json", jo.toString());
        this.startService(sendIntent);

        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
               // mTextView = (TextView) stub.findViewById(R.id.text);
            }
        });


    }


}
