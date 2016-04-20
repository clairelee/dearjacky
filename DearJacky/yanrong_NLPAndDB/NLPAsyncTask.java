package com.example.yanrongli.database_and_nlp;

import android.content.Context;
import android.os.AsyncTask;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.Future;

/**
 * Created by yanrongli on 4/19/16.
 */
public class NLPAsyncTask extends AsyncTask<String, Void, String> {
    private Context mContext;
    private Exception exception;
    private SensorTagDBHelper dbHelper;
    private String[] filteredWords = {"today", "tomorrow", "yesterday", "morning", "afternoon", "noon",
            "evening", "night", "month", "year"};

    public NLPAsyncTask(Context context){
        mContext = context;
        dbHelper = new SensorTagDBHelper(context);
    }

    @Override
    protected String doInBackground(String... text) {
        System.out.println(text[0]);

        Future<HttpResponse<JsonNode>> future = Unirest.post("https://japerk-text-processing.p.mashape.com/tag/")
                .header("X-Mashape-Key", "fKKyoov5pemshucNYihbQrnKBmQ3p1WNzMOjsnuWZsR3U7Lee0")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Accept", "application/json")
                .field("language", "english")
                .field("output", "tagged")
                .field("text", text[0])
                .asJsonAsync(new Callback<JsonNode>() {
                    @Override
                    public void completed(HttpResponse<JsonNode> httpResponse) {

                        JSONObject result = httpResponse.getBody().getObject();
                        System.out.println("The request has succeeded");
                        String resultString = "";
                        try {
                            resultString = result.getString("text");
                            //System.out.println(resultString);
                        }
                        catch(JSONException e){
                            System.out.println(e);
                        }
                        if(!resultString.equals("")) {
                            String[] tokens = resultString.split(" ");
                            for(int i = 0; i < tokens.length; i++) {
                                boolean filtered = false;
                                String keywords = "";
                                String[] word_and_tag = tokens[i].split("/");
                                //System.out.println("token: " + word_and_tag[0]);
                                //System.out.println("tag: " + word_and_tag[1]);
                                for (int j = 0; j < filteredWords.length; j++) {
                                    if (word_and_tag[0].equals(filteredWords[j]))
                                        filtered = true;
                                }
                                if (filtered == true)
                                    continue;
                                else if (i > 0 && i < tokens.length - 3) //when the word is not the last token
                                {
                                    if (word_and_tag[1].equals("NN") || word_and_tag[1].equals("NNS")) {
                                        String[] last_word_and_tag = tokens[i - 1].split("/");
                                        String[] next_word_and_tag = tokens[i + 1].split("/");
                                        //See if the previous word should be included
                                        if (last_word_and_tag[1].equals("JJ")) {
                                            keywords = last_word_and_tag[0] + " " + word_and_tag[0];
                                        } else {
                                            keywords = word_and_tag[0];
                                        }
                                        //Until here keywords is not "" anymore
                                        //check if the next word should be included
                                        if (next_word_and_tag[1].equals("NN") || next_word_and_tag[1].equals("NNS")) {
                                            boolean nextFiltered = false;
                                            for(int j = 0; j < filteredWords.length; j++)
                                            {
                                                if(next_word_and_tag[0].equals(filteredWords[j]))
                                                    nextFiltered = true;
                                            }
                                            if(!nextFiltered)
                                                keywords = keywords + " " + next_word_and_tag[0];
                                            i++;
                                        }
                                    }
                                } else if (i < tokens.length - 2) //when the word is the last token
                                {
                                    if (word_and_tag[1].equals("NN") || word_and_tag[1].equals("NNS")) {
                                        String[] last_word_and_tag = tokens[i - 1].split("/");
                                        //See if the previous word should be included
                                        if (last_word_and_tag[1].equals("JJ")) {
                                            keywords = last_word_and_tag[0] + " " + word_and_tag[0];
                                        } else {
                                            keywords = word_and_tag[0];
                                        }
                                    }
                                }
                                //Extract mood
                                String mood = tokens[tokens.length - 2].split("/")[0];
                                //System.out.println("mood = " + mood);
                                System.out.println("keywords = " + keywords);

                                //Store keywords. One sentence, although only has one mood, may include
                                //many sets of keywords.
                                if (!keywords.equals("")) {
                                    boolean isInserted = dbHelper.insertTableThreeData(mood, keywords);
                                    System.out.println(isInserted);
                                }
                            }
                        }
                    }

                    @Override
                    public void failed(UnirestException e) {
                        System.out.println("The request has failed");
                        System.out.println(e);
                    }

                    @Override
                    public void cancelled() {
                        System.out.println("The request has been cancelled");
                    }
                });

        return "Success";
    }

    @Override
    protected void onPostExecute(String response) {
        if(response == null)
            response = "There was an err";
        super.onPostExecute(response);
    }
}
