package com.example.codehenge;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.R;

import java.net.URL;
import java.util.List;

public class TweetItemAdapter extends ArrayAdapter<Tweet> {
    private List<Tweet> tweets;

    public TweetItemAdapter(Context context, int textViewResourceId, List<Tweet> tweets) {
        super(context, textViewResourceId,tweets);
        this.tweets = tweets;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v == null) {
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.hlistitem,null);
        }

        Tweet tweet = tweets.get(position);
        if(tweet != null) {
            TextView username = (TextView) v.findViewById(R.id.username);
            TextView message = (TextView) v.findViewById(R.id.message);
            ImageView image = (ImageView) v.findViewById(R.id.avatar);

            if(username != null) {
                username.setText(tweet.username);
            }

            if(message != null) {
                message.setText(tweet.message);
            }

            if(image != null) {
                image.setImageBitmap(getBitmap(tweet.image_url));
            }
        }

        return v;
    }

    public Bitmap getBitmap(String bitmapUrl) {
        try {
            URL url = new URL(bitmapUrl);
            return BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch(Exception e) {
            Log.v("TEST", "Exception: " + e.getMessage());
            return null;
        }
    }

}
