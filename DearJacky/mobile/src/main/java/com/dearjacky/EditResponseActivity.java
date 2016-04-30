package com.dearjacky;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class EditResponseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_response);

        android.support.v7.app.ActionBar a = getSupportActionBar();
        a.setTitle("Edit Response");
        a.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.colorPrimary)));


        ImageButton emoji1 = (ImageButton)findViewById(R.id.emoji1);
        emoji1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), TimelineActivity.class);
                startActivity(i);
            }
        });

//        a.setTitle("Edit Response");
//        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#009688"));
//        a.setBackgroundDrawable(colorDrawable);

    }
}
