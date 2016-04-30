package com.dearjacky;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by yanrongli on 4/30/16.
 */
public class FinishActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        System.out.println("meow1234567");
        finish();
//        if (getIntent().getBooleanExtra("EXIT", false)) {
//            System.out.println("meow123456789");
//            finish();
//        }
    }
}
