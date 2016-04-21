package com.dearjacky;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class EditResponseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_response);

        android.support.v7.app.ActionBar a = getSupportActionBar();
        a.hide();

        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), SettingsActivity.class);
                startActivity(i);
            }
        });
//        a.setTitle("Edit Response");
//        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#009688"));
//        a.setBackgroundDrawable(colorDrawable);

    }
}
