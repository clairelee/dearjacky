package com.dearjacky;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import java.nio.charset.StandardCharsets;

//import com.android.volley.Request;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonObjectRequest;

/**
 * Created by joleary and noon on 2/19/16 at very late in the night. (early in the morning?)
 */
public class PhoneListenerService extends WearableListenerService {

    //   WearableListenerServices don't need an iBinder or an onStartCommand: they just need an onMessageReceieved.
    //private static final String TOAST = "/send_toast";
    private static String mMood;
    private static String mIntensity;
    private static String mNote;
    private static SensorTagDBHelper mDBHelper;
    //Google API
    //String site = "https://maps.googleapis.com/maps/api/geocode/json?";
    //String apikey = "&key=AIzaSyC4ejrsUZXmtYLCMmgEFBjkduOolOavt90";
    //String apikey = "&key=AIzaSyC4ejrsUZXmtYLCMmgEFBjkduOolOavt90";

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d("T", "in PhoneListenerService, got: " + messageEvent.getPath());
        //Modify this whole things to start DetailedActivity using data from ProfileActivity

        if( messageEvent.getPath().equalsIgnoreCase("/message_mood") ) {
            mMood = new String(messageEvent.getData(), StandardCharsets.UTF_8);
        }
        else if(messageEvent.getPath().equalsIgnoreCase("/message_intensity")) {
            mIntensity = new String(messageEvent.getData(), StandardCharsets.UTF_8);
        }
        else if(messageEvent.getPath().equalsIgnoreCase("/message_note")) {
            mNote = new String(messageEvent.getData(), StandardCharsets.UTF_8);
            //write them into database.
            mDBHelper = new SensorTagDBHelper(this);
            //get current system time
            //Timestamp stamp = new Timestamp(System.currentTimeMillis());
            //String stamp_string = stamp.toString();
            //Date date = new Date(stamp.getTime());
            //DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            //String dateFormatted = formatter.format(date);
            String stamp_string = String.valueOf(System.currentTimeMillis());
            mDBHelper.insertTableOneData(stamp_string, mMood, Integer.parseInt(mIntensity), "", mNote, 0);

            Intent NLPintent = new Intent(this, NLPService.class);
            startService(NLPintent);
        }
        else {
            super.onMessageReceived(messageEvent);
        }

    }
}
