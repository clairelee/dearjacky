package com.example.yanrongli.database_and_nlp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private SensorTagDBHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        myDB = new SensorTagDBHelper(this);
        myDB.clearAll();
        myDB.insertTableOneData("105", "happy", 4, "", "I visited the palace today.", 0);
        myDB.insertTableOneData("111", "sad", 4, "", "I got bad grades yesterday.", 0);
        //myDB.insertTableOneData("123", "meow", 4, "", "This shouldn't appear here.", 1);
        myDB.insertTableOneData("555", "happy", 4, "", "I played soccer with my friends yesterday.", 0);
        //myDB.insertTableOneData("567", "happy", 4, "", "I played soccer.", 0);
        Intent NLPintent = new Intent(MainActivity.this, NLPService.class);
        startService(NLPintent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
