package com.dearjacky;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.widget.TextView;

public class AddTags extends Activity {

    private TextView mTextView;
    private String mMood;
    private String mIntensity;
    private String mNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tags);
        Intent oldIntent = getIntent();
        mMood = oldIntent.getExtras().getString("mood");
        mIntensity = oldIntent.getExtras().getString("intensity");
        mNote = oldIntent.getExtras().getString("note");

        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                //mTextView = (TextView) stub.findViewById(R.id.text);
//                Button button;
//                button = (Button)findViewById(R.id.button4);
//
//                button.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent i = new Intent(getBaseContext(), Confirmation.class);
//                        startActivity(i);
//                    }
//                });

            }
        });


    }
}
