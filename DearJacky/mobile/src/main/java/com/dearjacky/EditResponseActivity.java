package com.dearjacky;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditResponseActivity extends AppCompatActivity {
    String emotion;
    String note;
    int intensity;
    long timestamp;
    SensorTagDBHelper dbHelper;
    Calendar myCalendar;
    boolean newResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_response);
        String title;
        emotion = "";
        note = "";
        // If there are extras, edit, if not new...
        Intent intent = getIntent();
        if (intent.hasExtra("millis")) {
            title = "Edit Response";
            emotion = intent.getExtras().getString("mood");
            note = intent.getExtras().getString("note");
            intensity = Integer.parseInt(intent.getExtras().getString("intensity"));
            timestamp = Long.parseLong(intent.getExtras().getString("millis"));
            newResponse= false;
        } else {
            title = "New Response";
            newResponse = true;
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
        android.support.v7.app.ActionBar a = getSupportActionBar();
        a.setTitle(title);
        a.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.colorPrimary)));

        final Button angry = (Button)findViewById(R.id.mood_angry);
        final Button excited = (Button)findViewById(R.id.mood_excited);
        final Button happy = (Button)findViewById(R.id.mood_happy);
        final Button depressed = (Button)findViewById(R.id.mood_depressed);

        if(emotion.equals("angry")) angry.setBackgroundResource(R.drawable.angry_selected);
        if(emotion.equals("happy")) excited.setBackgroundResource(R.drawable.excited_selected);
        if(emotion.equals("sad")) depressed.setBackgroundResource(R.drawable.depressed_selected);
        if(emotion.equals("ok")) happy.setBackgroundResource(R.drawable.happy_selected);

        EditText notes = (EditText) findViewById(R.id.notes);
        if(title.equals("Edit Response")) notes.setText(note);

        angry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emotion = "angry";
                angry.setBackgroundResource(R.drawable.angry_selected);
                excited.setBackgroundResource(R.drawable.excited);
                happy.setBackgroundResource(R.drawable.happy);
                depressed.setBackgroundResource(R.drawable.depressed);
            }
        });
        excited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emotion = "happy";
                angry.setBackgroundResource(R.drawable.angry);
                excited.setBackgroundResource(R.drawable.excited_selected);
                happy.setBackgroundResource(R.drawable.happy);
                depressed.setBackgroundResource(R.drawable.depressed);
            }
        });
        happy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emotion = "ok";
                angry.setBackgroundResource(R.drawable.angry);
                excited.setBackgroundResource(R.drawable.excited);
                happy.setBackgroundResource(R.drawable.happy_selected);
                depressed.setBackgroundResource(R.drawable.depressed);
            }
        });
        depressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emotion = "sad";
                angry.setBackgroundResource(R.drawable.angry);
                excited.setBackgroundResource(R.drawable.excited);
                happy.setBackgroundResource(R.drawable.happy);
                depressed.setBackgroundResource(R.drawable.depressed_selected);
            }
        });

        Button addButton = (Button) findViewById(R.id.add_value);
        Button subButton = (Button) findViewById(R.id.sub_value);
        final TextView valueText = (TextView) findViewById(R.id.value_text);
        if(title.equals("Edit Response"))
            valueText.setText(String.valueOf(intensity));

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

        final TextView date = (TextView) findViewById(R.id.date);
        final TextView time = (TextView) findViewById(R.id.time);
        long now = System.currentTimeMillis();
        if(title.equals("Edit Response")) now = timestamp;
        else timestamp = now;

        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy");
        String dateString = sdf.format(now);
        date.setText(dateString);

        SimpleDateFormat sdf2 = new SimpleDateFormat("h:mm a");
        String timeString = sdf2.format(now);
        time.setText(timeString);

        myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener datepicker = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MMMM dd, yyyy"; //In which you need put here
                SimpleDateFormat sdf3 = new SimpleDateFormat(myFormat, Locale.US);
                date.setText(sdf3.format(myCalendar.getTime()));
            }

        };

        if(title.equals("New Response"))
            date.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    new DatePickerDialog(EditResponseActivity.this, datepicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            });
        if(title.equals("New Response"))
            time.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    int hour = myCalendar.get(Calendar.HOUR_OF_DAY);
                    final int minute = myCalendar.get(Calendar.MINUTE);
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(EditResponseActivity.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            myCalendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                            myCalendar.set(Calendar.MINUTE, selectedMinute);
                            String string;
                            String minuteString;
                            if (selectedMinute < 10)
                                minuteString = "0" + selectedMinute;
                            else
                                minuteString = "" + selectedMinute;
                            if (selectedHour > 12) {
                                string = selectedHour - 12 + ":" + minuteString + " PM";
                            } else {
                                if (selectedHour == 0)
                                    selectedHour = 12;

                                string = selectedHour + ":" + minuteString + " AM";
                            }
                            time.setText(string);
                        }
                    }, hour, minute, false);//Yes 24 hour time
                    mTimePicker.setTitle("Select Time");
                    mTimePicker.show();
                }
            });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Toast.makeText(EditResponseActivity.this, String.valueOf(timestamp), Toast.LENGTH_SHORT).show();
        if (newResponse){
            getMenuInflater().inflate(R.menu.menu, menu);
        }
        else{
            getMenuInflater().inflate(R.menu.menu_with_delete, menu);
        }
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
                EditText notes = (EditText) findViewById(R.id.notes);
                TextView intensity = (TextView) findViewById(R.id.value_text);
                TextView date = (TextView) findViewById(R.id.date);
                TextView time = (TextView) findViewById(R.id.time);

                String user_notes = notes.getText().toString();
                String user_intensity = intensity.getText().toString();
                String user_date = date.getText().toString();
                String user_time = time.getText().toString();
                if (emotion.equals("")) {
                    Toast.makeText(EditResponseActivity.this, "Please choose an emotion", Toast.LENGTH_SHORT).show();
                } else {
                    // SAVE ENTRY!!
                    dbHelper = new SensorTagDBHelper(getBaseContext());
                    if (getSupportActionBar().getTitle().equals("New Response"))
                        timestamp = myCalendar.getTimeInMillis();
                    System.out.println(timestamp);
                    if (getSupportActionBar().getTitle().equals("New Response")) {
                        dbHelper.insertTableOneData(String.valueOf(timestamp), emotion, Integer.parseInt(user_intensity), "", user_notes, 0);
                        Intent NLPintent = new Intent(this, NLPService.class);
                        startService(NLPintent);
                    }
                    else {
                        dbHelper.updateTableOneData(String.valueOf(timestamp), emotion, Integer.parseInt(user_intensity), "", user_notes, 0);
                        Intent NLPintent = new Intent(this, NLPService.class);
                        startService(NLPintent);
                    }
                    Toast.makeText(EditResponseActivity.this, "Response Saved! " + user_notes + " " + emotion + " " + user_intensity, Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
                return true;
            case R.id.action_delete:
                new AlertDialog.Builder(this)
                        .setTitle("Delete Entry?")
                        .setMessage("Are you sure you want to permanantly delete this entry?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dbHelper = new SensorTagDBHelper(getBaseContext());
                                dbHelper.deleteTableOneData(String.valueOf(timestamp));
                                Toast.makeText(EditResponseActivity.this, "Response Deleted! "+ timestamp, Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            }

                        })
                        .setNegativeButton("No", null)
                        .show();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
