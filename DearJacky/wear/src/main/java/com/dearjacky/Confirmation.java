package com.dearjacky;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

public class Confirmation extends Activity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);

        Intent sendIntent = new Intent(this, WatchToPhoneService.class);
        sendIntent.putExtra("cmd", "Recieved Data Entry!");
//        sendIntent.putExtra("json", jo.toString());
        this.startService(sendIntent);

        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);
            }
        });


    }


}
