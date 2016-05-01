package com.dearjacky;

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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditResponseActivity extends AppCompatActivity {
    String emotion;
    SensorTagDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new SensorTagDBHelper(getBaseContext());
        setContentView(R.layout.activity_edit_response);
        String title;
        emotion = "";
        EditText notes = (EditText) findViewById(R.id.notes);
        final Button angry = (Button)findViewById(R.id.mood_angry);
        final Button excited = (Button)findViewById(R.id.mood_excited);
        final Button happy = (Button)findViewById(R.id.mood_happy);
        final Button depressed = (Button)findViewById(R.id.mood_depressed);
        Button addButton = (Button) findViewById(R.id.add_value);
        Button subButton = (Button) findViewById(R.id.sub_value);
        final TextView valueText = (TextView) findViewById(R.id.value_text);
        final TextView date = (TextView) findViewById(R.id.date);
        final TextView time = (TextView) findViewById(R.id.time);

        // If there are extras, edit, if not new...
        Intent intent = getIntent();
        if (intent.hasExtra("timestamp")) {
            title = "Edit Response";
            String timestamp = intent.getStringExtra("timestamp");
            long timevalue = Long.parseLong(timestamp);
            DataPointJacky event = dbHelper.getSelectedTableOneData(timestamp);
            String mood = event.mood;
            if (mood.equals("angry")) {
                angry.setBackgroundResource(R.drawable.angry_selected);
            } else if (mood.equals("excited")) {
                excited.setBackgroundResource(R.drawable.excited_selected);
            } else if (mood.equals("depressed")) {
                depressed.setBackgroundResource(R.drawable.depressed_selected);
            } else {
                happy.setBackgroundResource(R.drawable.happy_selected);
            }
            notes.setText(event.note, TextView.BufferType.EDITABLE);
            valueText.setText(String.valueOf(event.intensity));
            SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy");
            String dateString = sdf.format(timevalue);
            date.setText(dateString);
            SimpleDateFormat sdf2 = new SimpleDateFormat("h:mm a");
            String timeString = sdf2.format(timevalue);
            time.setText(timeString);
        } else {
            title = "New Response";
            long now = System.currentTimeMillis();
            SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy");
            String dateString = sdf.format(now);
            date.setText(dateString);
            SimpleDateFormat sdf2 = new SimpleDateFormat("h:mm a");
            String timeString = sdf2.format(now);
            time.setText(timeString);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
        android.support.v7.app.ActionBar a = getSupportActionBar();
        a.setTitle(title);
        a.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.colorPrimary)));

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
                emotion = "excited";
                angry.setBackgroundResource(R.drawable.angry);
                excited.setBackgroundResource(R.drawable.excited_selected);
                happy.setBackgroundResource(R.drawable.happy);
                depressed.setBackgroundResource(R.drawable.depressed);
            }
        });
        happy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emotion = "calm";
                angry.setBackgroundResource(R.drawable.angry);
                excited.setBackgroundResource(R.drawable.excited);
                happy.setBackgroundResource(R.drawable.happy_selected);
                depressed.setBackgroundResource(R.drawable.depressed);
            }
        });
        depressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emotion = "depressed";
                angry.setBackgroundResource(R.drawable.angry);
                excited.setBackgroundResource(R.drawable.excited);
                happy.setBackgroundResource(R.drawable.happy);
                depressed.setBackgroundResource(R.drawable.depressed_selected);
            }
        });

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

        final Calendar myCalendar = Calendar.getInstance();

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

        date.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(EditResponseActivity.this, datepicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        time.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(EditResponseActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String string;
                        if (selectedHour > 12) {
                            string = selectedHour - 12 + ":" + selectedMinute + " PM";
                        } else {
                            string = selectedHour + ":" + selectedMinute + " AM";
                        }
                        time.setText(string);
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

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
                    Toast.makeText(EditResponseActivity.this, "Response Saved! " + user_notes + " " + emotion + " " + user_intensity, Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
