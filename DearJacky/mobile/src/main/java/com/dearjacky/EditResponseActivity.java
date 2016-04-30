package com.dearjacky;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class EditResponseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_response);
        String title;
        // If there are extras, edit, if not new...
        Intent intent = getIntent();
        if (intent.hasExtra("event_id")) {
            title = "Edit Response";
            // Do stuff to get the event infos
        } else {
            title = "New Response";
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
        android.support.v7.app.ActionBar a = getSupportActionBar();
        a.setTitle(title);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        //return true;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (getParentActivityIntent() == null) {
                    Log.i("T", "You have forgotten to specify the parentActivityName in the AndroidManifest!");
                    onBackPressed();
                } else {
                    NavUtils.navigateUpFromSameTask(this);
                }
                return true;
            case R.id.action_confirm:
                // Either update the entry, or save the new entry
                Toast.makeText(EditResponseActivity.this, "Response Saved!", Toast.LENGTH_SHORT).show();
                onBackPressed();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
