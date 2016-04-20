package com.dearjacky;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class EditResponseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_response);

        android.support.v7.app.ActionBar a = getSupportActionBar();
        a.setTitle("Edit Response");
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#009688"));
        a.setBackgroundDrawable(colorDrawable);

    }
}
