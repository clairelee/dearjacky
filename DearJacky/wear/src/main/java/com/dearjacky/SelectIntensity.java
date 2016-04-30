package com.dearjacky;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SelectIntensity extends Activity {

    private TextView mTextView;
    private String mMood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_intensity);
        Intent oldIntent = getIntent();
        mMood = oldIntent.getExtras().getString("mood");
        //Here we should set the background color.
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                //mTextView = (TextView) stub.findViewById(R.id.text);
                //Button button;
                //button = (Button)findViewById(R.id.button2);

//                button.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent i = new Intent(getBaseContext(), AddNotes.class);
//                        startActivity(i);
//                    }
//                });
                final TextView valueText = (TextView) stub.findViewById(R.id.value_text);
                Button addButton = (Button) stub.findViewById(R.id.add_value);
                Button subButton = (Button) stub.findViewById(R.id.sub_value);
                Button cancelButton = (Button) stub.findViewById(R.id.cancel_button);
                Button confirmButton = (Button) stub.findViewById(R.id.confirm_button);

                addButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int curVal = Integer.parseInt(valueText.getText().toString());
                        if(curVal < 5)
                        {
                            valueText.setText(String.valueOf(curVal+1));
                        }
                    }
                });

                subButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int curVal = Integer.parseInt(valueText.getText().toString());
                        if(curVal > 0)
                        {
                            valueText.setText(String.valueOf(curVal-1));
                        }
                    }
                });

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

                confirmButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getBaseContext(), AddNotes.class);
                        i.putExtra("mood", mMood);
                        i.putExtra("intensity", valueText.getText().toString());
                        startActivity(i);
                    }
                });

            }
        });


    }
}
