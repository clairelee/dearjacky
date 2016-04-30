package com.dearjacky;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

/**
 * Created by yanrongli on 2/28/16.
 */

/**
 * Created by joleary and noon on 2/19/16 at very late in the night. (early in the morning?)
 */
public class WatchListenerService extends WearableListenerService {
    // In PhoneToWatchService, we passed in a path, either "/FRED" or "/LEXY"
    // These paths serve to differentiate different phone-to-watch messages
//    private static final String NAME_LIST = "/name_list";
//    private static final String PARTY_LIST = "/party_list";
//    private static final String MY_2012_VOTE = "/2012vote";
//    private static final String START_INTENT = "/start_intent";
//    private static String name_list = "No_name_received";
//    private static String party_list = "No_party_received";
//    private static String my2012vote = "No_location_received";
    private static String START_INTENT = "/start";

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d("T", "in WatchListenerService, got: " + messageEvent.getPath());
        //use the 'path' field in sendmessage to differentiate use cases
        //(here, fred vs lexy)

        if (messageEvent.getPath().equalsIgnoreCase( START_INTENT )){
            Intent intent = new Intent(this, ChooseMood.class );
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //you need to add this flag since you're starting a new activity from a service
            //System.out.println(name_list);
            //System.out.println(party_list);
            //System.out.println(my2012vote);
            Log.d("T", "about to start watch MainActivity with new profile list");
            startActivity(intent);
        } else {
            super.onMessageReceived( messageEvent );
        }

    }
}