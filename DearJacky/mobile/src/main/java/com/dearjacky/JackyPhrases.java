package com.dearjacky;

import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Jasper on 5/3/2016.
 */
public class JackyPhrases {
    static String[] neutralPhrases = {
            "Hey, how are you! Tell me what you've been up to recently!",
            "Hi! How has your day been so far?",
            "Anything new?",
            "Hey, how are you today?",
            "What have you been up to?",
            "Tell me about your day!"
    };

    static String[] happyPhrases ={
            "Looks like today is going pretty well, I'm glad!",
            "Seems like a great day so far, tell me more about what you've done today :)",
            "Hey! Thanks for checking in with me, its nice to hear about your day!",
            "I love hearing your good news!",
            "Thanks for checking in with me :)"
    };

    static String[] sadPhrases ={
            "I'm sorry that things have been rough :( Tap here if you want some suggestions to cheer up.",
            "Tap here and we can forget about your worries and chat about some better things :)",
            "I'm always here for you, and your other friends are too. Tap here and we can talk to some of them or do fun things!",
            "I hope that things get better soon :( If you want some help figuring out what to do next, tap here.",
            "I'm sorry today was not the best. Tap me and we can talk some more!"
    };

    static String[] suggestionPhrases = {
            "Hey, you're usually in a good mood after doing these things, give them a shot!",
            "Let's do something fun, like these!",
            "These are always fun things to do :)"
    };

    static String[] contactsPhrases = {
            "I'm always glad to hear from you, and your friends would be glad to hear from you too!",
            "Let somebody know how you're doing :)",
            "It always helps to talk with someone"
    };

    static String[] getList(SensorTagDBHelper dbHelper){
        Calendar myCalendar1 = new GregorianCalendar();
        Calendar myCalendar2 = new GregorianCalendar();
        myCalendar1.add(Calendar.DATE, -1);
        List<DataPointJacky> myEventList= dbHelper.getTableOneDataDuringPeriod(myCalendar1.getTime(), myCalendar2.getTime());
        int numHappy = 0;
        int numOk = 0;
        int numSad = 0;
        int numAngry = 0;
        for(DataPointJacky ele : myEventList)
        {
            if(ele.mood.equals("happy")) numHappy++;
            if(ele.mood.equals("ok")) numOk++;
            if(ele.mood.equals("sad")) numSad++;
            if(ele.mood.equals("angry")) numAngry++;
        }
        if(numHappy == 0 && numOk == 0 && numSad == 0 && numAngry == 0) return JackyPhrases.neutralPhrases;
        else if(numHappy == Math.max(Math.max(numHappy, numOk), Math.max(numSad, numAngry))) return JackyPhrases.happyPhrases;
        else if(numOk == Math.max(Math.max(numHappy, numOk), Math.max(numSad, numAngry))) return JackyPhrases.happyPhrases;
        else if(numSad == Math.max(Math.max(numHappy, numOk), Math.max(numSad, numAngry))) return JackyPhrases.sadPhrases;
        else return JackyPhrases.sadPhrases;
    }
}
