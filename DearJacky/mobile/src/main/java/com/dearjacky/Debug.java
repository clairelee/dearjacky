package com.dearjacky;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class Debug extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);
        Button reset_db = (Button) findViewById(R.id.reset_db_button);

        reset_db.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    String dbname = "my260ProjectDB.db";
                    File dbpath = getBaseContext().getDatabasePath(dbname);
                    Log.d("T", dbpath.toString());
                    InputStream in = getResources().openRawResource(R.raw.db_dump);
                    OutputStream out = new FileOutputStream(dbpath);

                    // Transfer bytes from in to out
                    byte[] buf = new byte[1024];
                    int len;

                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                    in.close();
                    out.close();
                }catch(Exception e){

                }
                Toast.makeText(getApplicationContext(), "Successfully Reset DB", Toast.LENGTH_LONG).show();

            }
        });

        Button send_notification = (Button) findViewById(R.id.send_notification_button);

        send_notification.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent watchIntent = new Intent(v.getContext(), PhoneToWatchService.class);
                v.getContext().startService(watchIntent);
                Toast.makeText(getApplicationContext(), "Successfully sent notification", Toast.LENGTH_LONG).show();

            }
        });


    }
}
