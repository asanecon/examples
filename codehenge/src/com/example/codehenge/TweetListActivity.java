package com.example.codehenge;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import com.example.R;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;
import java.util.List;


public class TweetListActivity extends Activity {
    List<Tweet> tweets;
    TweetItemAdapter adapter;
    volatile boolean done = false;
    TweetListActivity me = this;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.hlist);


        new Thread(new Runnable() {
            public void run() {
                tweets = getTweets("chow", 1);
            }
        }).start();

        while(tweets == null) {}


        new Thread(new Runnable() {
            public void run() {
                adapter = new TweetItemAdapter(me, R.layout.hlistitem, tweets);
                ListView listView = (ListView) findViewById(R.id.ListViewId);
                listView.setAdapter(adapter);
                done = true;
            }
        }).start();

        while(!done){}
    }

    public List<Tweet> getTweets(String searchTerm, int page) {
        String searchUrl = "http://search.twitter.com/search.json?q=@"
                + searchTerm + "&rpp=100&page=" + page;
        HttpGet httpGet = new HttpGet(searchUrl);

        HttpClient client = new DefaultHttpClient();

        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String responseBody = null;
        try {
            responseBody = client.execute(httpGet, responseHandler);
        } catch(Exception e) {
            e.printStackTrace();
        }

        JSONParser parser = new JSONParser();
        JSONObject jsonObject = null;
        try{
            Object obj = parser.parse(responseBody);
            jsonObject = (JSONObject)obj;
        } catch(Exception e) {
            Log.v("TEST", "Exception: " + e.getMessage());
        }

        JSONArray array = null;
        try {
            Object obj = jsonObject.get("results");
            array = (JSONArray)obj;
        } catch(Exception e) {
            Log.v("TEST", "Exception: " + e.getMessage());
        }

        List<Tweet> tweets = new ArrayList<Tweet>();
        try {
            for(Object t : array) {
                Tweet tweet = new Tweet(
                    ((JSONObject)t).get("from_user").toString(),
                    ((JSONObject)t).get("text").toString(),
                    ((JSONObject)t).get("profile_image_url").toString());
                tweets.add(tweet);
            }
        } catch(Exception e) {
            Log.v("TEST", "Exception: " + e.getMessage());
        }

        return tweets;
    }


}
