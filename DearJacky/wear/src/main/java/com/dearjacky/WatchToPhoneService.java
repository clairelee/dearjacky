package com.dearjacky;

/**
 * Created by yanrongli on 2/28/16.
 */
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joleary and noon on 2/19/16 at very late in the night. (early in the morning?)
 */
public class WatchToPhoneService extends Service {

    private GoogleApiClient mWatchApiClient;
    private List<Node> nodes = new ArrayList<>();
    private String mMood = "";
    private String mIntensity;
    private String mNote = "";

    @Override
    public void onCreate() {
        super.onCreate();
        //initialize the googleAPIClient for message passing
        mWatchApiClient = new GoogleApiClient.Builder( this )
                .addApi( Wearable.API )
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle connectionHint) {
                    }
                    @Override
                    public void onConnectionSuspended(int cause){
                    }
                })
                .build();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mWatchApiClient.disconnect();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Bundle extras = intent.getExtras();
        mMood = extras.getString("mood");
        mIntensity = extras.getString("intensity");
        mNote = extras.getString("note");
        mWatchApiClient.connect();
        sendMessage("/message_mood", mMood);
        System.out.println(mMood);
        sendMessage("/message_intensity", mIntensity);
        System.out.println(mIntensity);
        sendMessage("/message_note", mNote);
        System.out.println(mNote);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void sendMessage(final String path, final String text ) {
        Wearable.NodeApi.getConnectedNodes(mWatchApiClient).setResultCallback(new ResultCallback<NodeApi.GetConnectedNodesResult>() {
            @Override
            public void onResult(NodeApi.GetConnectedNodesResult getConnectedNodesResult) {
                nodes = getConnectedNodesResult.getNodes();
                for (Node node : nodes)
                    Wearable.MessageApi.sendMessage(mWatchApiClient, node.getId(), path, text.getBytes());
            }
        });
    }
}
