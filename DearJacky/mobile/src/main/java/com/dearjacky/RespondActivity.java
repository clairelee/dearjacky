package com.dearjacky;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class RespondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_respond);

        // Set Action Bar color
        android.support.v7.app.ActionBar a = getSupportActionBar();
        a.hide();

        //a.setTitle("Jacky Says");
        //ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#009688"));
        //a.setBackgroundDrawable(colorDrawable);
    }
}
