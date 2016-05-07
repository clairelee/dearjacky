package com.dearjacky;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RespondActivity extends AppCompatActivity {
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private SensorTagDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    1);
        } else {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_respond);
            dbHelper = new SensorTagDBHelper(getBaseContext());
            android.support.v7.app.ActionBar a = getSupportActionBar();
            a.setTitle("Ask Jacky");
            a.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.colorPrimary)));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            Typewriter jackyText = (Typewriter) findViewById(R.id.event_name);
            jackyText.setCharacterDelay(50);
            String[] messages = JackyPhrases.suggestionPhrases;
            jackyText.animateText(messages[(int)(Math.random()*messages.length)]);

            //Fetch keywords from database and populate them into the view
            List<String> keywordStrings = dbHelper.getTableThreeDataTopTen();
            TextView keyword_1 = (TextView) findViewById(R.id.keyword_1);
            TextView keyword_2 = (TextView) findViewById(R.id.keyword_2);
            TextView keyword_3 = (TextView) findViewById(R.id.keyword_3);
            TextView keyword_4 = (TextView) findViewById(R.id.keyword_4);
            TextView keyword_5 = (TextView) findViewById(R.id.keyword_5);
            TextView[] tViews = {keyword_1, keyword_2, keyword_3, keyword_4, keyword_5};

            for(TextView tv: tViews){
                if(keywordStrings.isEmpty()){
                    ((View)tv.getParent()).setVisibility(View.GONE);
                    continue;
                }
                String keyword = keywordStrings.remove((int)(Math.random()*keywordStrings.size()));
                tv.setText(keyword);
            }

            Typewriter jackyText2 = (Typewriter) findViewById(R.id.jacky_text2);
            jackyText2.setInitialDelay(1500);
            jackyText2.setCharacterDelay(50);
            String[] messages2 = JackyPhrases.contactsPhrases;
            jackyText2.animateText(messages2[(int)(Math.random()*messages2.length)]);
            RoundedQuickContactBadge badge1 = (RoundedQuickContactBadge) findViewById(R.id.quickContactBadge);

            RoundedQuickContactBadge badge2 = (RoundedQuickContactBadge) findViewById(R.id.quickContactBadge2);
            RoundedQuickContactBadge badge3 = (RoundedQuickContactBadge) findViewById(R.id.quickContactBadge3);
            RoundedQuickContactBadge badge4 = (RoundedQuickContactBadge) findViewById(R.id.quickContactBadge4);

            TextView name1 = (TextView) findViewById(R.id.textView2);
            TextView name2 = (TextView) findViewById(R.id.textView3);
            TextView name3 = (TextView) findViewById(R.id.textView4);
            TextView name4 = (TextView) findViewById(R.id.textView5);
            RoundedQuickContactBadge[] badges = {badge1, badge2, badge3, badge4};
            TextView[] names = {name1, name2, name3, name4};
            Map h = getFavoriteContacts();
            List keys = Arrays.asList(h.keySet().toArray());
            ArrayList<Integer> intList = new ArrayList<Integer>();
            for (int i=1; i<keys.size(); i++) {
                intList.add(new Integer(i));
            }
            Collections.shuffle(intList);
            if(intList.size() == 0){
                ((TextView)findViewById(R.id.contacts)).setText("Tap here to go to your contacts app and star some contacts to see people here!");
                ((TextView)findViewById(R.id.contacts)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent();
                        i.setComponent(new ComponentName("com.android.contacts", "com.android.contacts.DialtactsContactsEntryActivity"));
                        i.setAction("android.intent.action.MAIN");
                        i.addCategory("android.intent.category.LAUNCHER");
                        i.addCategory("android.intent.category.DEFAULT");
                        startActivity(i);

                    }
                });
            }
            for(int i = 0; i < 4; i++){
                names[i].setText("");
            }

            for(int i = 0; i < Math.min(4,intList.size()); i++){
                int index = intList.get(i);
                String name = ((String)keys.get(index));
                String contactUri = ((String[])h.get(name))[0];
                String thumbUri = ((String[])h.get(name))[1];
                Uri contact = Uri.parse(contactUri);
                badges[i].assignContactUri(contact);
                if(thumbUri != null && !thumbUri.equals("")){
                    Bitmap mThumbnail =
                            loadContactPhotoThumbnail(thumbUri);
                    badges[i].setImageBitmap(mThumbnail);
                }else{
                    badges[i].setImageDrawable(getResources().getDrawable(R.drawable.profile_default));
                }


                names[i].setText(name.split(" ")[0]);

            }

    //        ListView list2 = (ListView) findViewById(R.id.list3);
    //
    //        ArrayList<Events> items = new ArrayList<>();
    //        items.add(new Events("First Event", TimelineView.TYPE_START, (int) (Math.random() * 4)));
    //        for (int i = 0; i < 20; i++) {
    //            items.add(new Events(String.format("Middle Event", i + 1),
    //                    TimelineView.TYPE_MIDDLE, (int) (Math.random() * 4)));
    //        }
    //        items.add(new Events("Last Event", TimelineView.TYPE_END, (int) (Math.random() * 4)));
    //        list.setAdapter(new EventsAdapter(this, items));
    //
    //        list2.setAdapter(new EventsAdapter(this, items));
            // ATTENTION: This was auto-generated to implement the App Indexing API.
            // See https://g.co/AppIndexing/AndroidStudio for more information.
            client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        }
    }

    Map<String, String[]> getFavoriteContacts() {

        Map<String, String[]> contactMap = new HashMap<String, String[]>();

        Uri queryUri = ContactsContract.Contacts.CONTENT_URI;

        String[] projection = new String[]{
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.STARRED, ContactsContract.Contacts.PHOTO_THUMBNAIL_URI};

        String selection = ContactsContract.Contacts.STARRED + "='1'";

        Cursor cursor = managedQuery(queryUri, projection, selection, null, null);

        while (cursor.moveToNext()) {
            String contactID = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.Contacts._ID));

            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.withAppendedPath(
                    ContactsContract.Contacts.CONTENT_URI, String.valueOf(contactID));
            intent.setData(uri);
            String intentUriString = intent.toUri(0);

            String title = (cursor.getString(
                    cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
            String thumb = (cursor.getString(
                    cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI)));
            String[] result = {intentUriString, thumb};
            contactMap.put(title, result);
        }

        cursor.close();
        return contactMap;
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Respond Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.dearjacky/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Respond Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.dearjacky/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    /**
     * Load a contact photo thumbnail and return it as a Bitmap,
     * resizing the image to the provided image dimensions as needed.
     * @param photoData photo ID Prior to Honeycomb, the contact's _ID value.
     * For Honeycomb and later, the value of PHOTO_THUMBNAIL_URI.
     * @return A thumbnail Bitmap, sized to the provided width and height.
     * Returns null if the thumbnail is not found.
     */
    private Bitmap loadContactPhotoThumbnail(String photoData) {
        // Creates an asset file descriptor for the thumbnail file.
        AssetFileDescriptor afd = null;
        // try-catch block for file not found
        try {
            // Creates a holder for the URI.
            Uri thumbUri;
            // If Android 3.0 or later
            if (Build.VERSION.SDK_INT
                    >=
                    Build.VERSION_CODES.HONEYCOMB) {
                // Sets the URI from the incoming PHOTO_THUMBNAIL_URI
                thumbUri = Uri.parse(photoData);
            } else {
                // Prior to Android 3.0, constructs a photo Uri using _ID
                /*
                 * Creates a contact URI from the Contacts content URI
                 * incoming photoData (_ID)
                 */
                final Uri contactUri = Uri.withAppendedPath(
                        ContactsContract.Contacts.CONTENT_URI, photoData);
                /*
                 * Creates a photo URI by appending the content URI of
                 * Contacts.Photo.
                 */
                thumbUri =
                        Uri.withAppendedPath(
                                contactUri, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
            }

        /*
         * Retrieves an AssetFileDescriptor object for the thumbnail
         * URI
         * using ContentResolver.openAssetFileDescriptor
         */
            afd = this.getContentResolver().
                    openAssetFileDescriptor(thumbUri, "r");
        /*
         * Gets a file descriptor from the asset file descriptor.
         * This object can be used across processes.
         */
            FileDescriptor fileDescriptor = afd.getFileDescriptor();
            // Decode the photo file and return the result as a Bitmap
            // If the file descriptor is valid
            if (fileDescriptor != null) {
                // Decodes the bitmap
                return BitmapFactory.decodeFileDescriptor(
                        fileDescriptor, null, null);
            }
            // If the file isn't found
        } catch (FileNotFoundException e) {
            /*
             * Handle file not found errors
             */
            // In all cases, close the asset file descriptor
        } finally {
            if (afd != null) {
                try {
                    afd.close();
                } catch (IOException e) {}
            }
        }
        return null;
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
